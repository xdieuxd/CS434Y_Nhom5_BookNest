function shuffleArray(array) {
  for (let i = array.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [array[i], array[j]] = [array[j], array[i]];
  }
  return array;
}

function debounce(func, delay) {
  let timer;
  return function (...args) {
    clearTimeout(timer);
    timer = setTimeout(() => func.apply(this, args), delay);
  };
}

function luuLichSuTimKiem(tuKhoa, maNguoiDung) {
  fetch("http://localhost:8080/api/lich-su-tim-kiem", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ maNguoiDung, tuKhoa }),
  }).catch((err) => console.error("Lỗi lưu lịch sử tìm kiếm:", err));
}

// fetch thể loại
fetch("http://localhost:8080/api/categories")
  .then((response) => response.json())
  .then((categories) => {
    const dropdown = document.getElementById("filterDropdown");
    categories.forEach((cat) => {
      const option = document.createElement("option");
      option.value = cat.id;
      option.textContent = cat.name;
      dropdown.appendChild(option);
    });
  })
  .catch((error) => console.error("Lỗi khi lấy danh sách thể loại:", error));

let luuLichSuTimKiemDebounce;

document.addEventListener("DOMContentLoaded", function () {
  const user = JSON.parse(localStorage.getItem("nguoiDung"));
  const usernameSpan = document.getElementById("username");

  // Kiểm tra trạng thái đăng nhập
  if (user && user.hoTen) {
    usernameSpan.textContent = `👤 ${user.hoTen}`;
  } else {
    window.location.href = "login-register.html";
  }

  // Khởi tạo debounce lưu lịch sử tìm kiếm
  if (user && user.maNguoiDung) {
    luuLichSuTimKiemDebounce = debounce((term) => {
      luuLichSuTimKiem(term, user.maNguoiDung);
    }, 600);
  }

  // Load danh sách sách
  fetchBooks();

  // Thiết lập ô tìm kiếm
  setupSearchBox();
});

// Thiết lập ô tìm kiếm
function setupSearchBox() {
  const searchInput = document.getElementById("searchInput");
  const suggestionsList = document.createElement("ul");
  suggestionsList.id = "suggestionsList";
  suggestionsList.style.display = "none";
  searchInput.parentNode.appendChild(suggestionsList);

  // Khi người dùng nhập từ khóa
  searchInput.addEventListener("input", function () {
    const searchTerm = this.value.trim().toLowerCase();
    if (searchTerm) {
      fetch("http://localhost:8080/api/sach")
        .then((response) => response.json())
        .then((allBooks) => {
          const suggestions = [
            ...new Set(
              allBooks
                .filter((book) =>
                  book.tenSach.toLowerCase().includes(searchTerm)
                )
                .map((book) => book.tenSach)
                .slice(0, 5)
            ),
          ];
          renderSuggestions(suggestions);
          fetchBooks(null, searchTerm);

          // Lưu lịch sử tìm kiếm
          if (luuLichSuTimKiemDebounce) {
            luuLichSuTimKiemDebounce(searchTerm);
          }
        })
        .catch((error) => console.error("Lỗi khi lấy sách cho gợi ý:", error));
    } else {
      suggestionsList.style.display = "none";
      fetchBooks();
    }
  });

  document.addEventListener("click", function (event) {
    if (
      !searchInput.contains(event.target) &&
      !suggestionsList.contains(event.target)
    ) {
      suggestionsList.style.display = "none";
    }
  });

  searchInput.addEventListener("keydown", function (event) {
    if (event.key === "Enter") {
      const firstSuggestion = suggestionsList.querySelector("li");
      if (firstSuggestion) {
        searchInput.value = firstSuggestion.textContent;
        suggestionsList.style.display = "none";
        fetchBooks(null, firstSuggestion.textContent.toLowerCase());
      }
    }
  });
}

function renderSuggestions(suggestions) {
  const suggestionsList = document.getElementById("suggestionsList");
  suggestionsList.innerHTML = "";
  if (suggestions.length > 0) {
    suggestions.forEach((suggestion) => {
      const li = document.createElement("li");
      li.textContent = suggestion;
      li.className = "suggestion-item";
      li.addEventListener("click", () => {
        document.getElementById("searchInput").value = suggestion;
        suggestionsList.style.display = "none";
        fetchBooks(null, suggestion.toLowerCase());
      });
      suggestionsList.appendChild(li);
    });
    suggestionsList.style.display = "block";
  } else {
    suggestionsList.style.display = "none";
  }
}

// lọc theo thể loại
function fetchBooks(categoryId = null, searchTerm = "") {
  fetch("http://localhost:8080/api/sach")
    .then((response) => {
      if (!response.ok)
        throw new Error(`Lỗi HTTP! trạng thái: ${response.status}`);
      return response.json();
    })
    .then((allBooks) => {
      let booksForGrid = allBooks;

      if (searchTerm) {
        // Lọc sách theo từ khóa
        booksForGrid = allBooks.filter((book) =>
          book.tenSach.toLowerCase().includes(searchTerm)
        );
        renderBooks(booksForGrid, "bookGrid");
      } else if (categoryId && categoryId !== "all") {
        // Lọc theo thể loại
        fetch(
          `http://localhost:8080/api/sach-the-loai/theloai?maTheLoai=${categoryId}`
        )
          .then((response) => {
            if (!response.ok)
              throw new Error(`Lỗi HTTP! trạng thái: ${response.status}`);
            return response.json();
          })
          .then((sachTheLoaiList) => {
            const matchingBooks = sachTheLoaiList
              .filter((stl) => stl.id.maTheLoai == categoryId)
              .map((stl) => stl.id.maSach);
            booksForGrid = allBooks.filter((book) =>
              matchingBooks.includes(book.maSach)
            );
            renderBooks(booksForGrid, "bookGrid");
          })
          .catch((error) =>
            console.error("Lỗi khi lấy danh sách sách-thể loại:", error)
          );
      } else {
        // Hiển thị ngẫu nhiên
        booksForGrid = shuffleArray([...allBooks]);
        renderBooks(booksForGrid, "bookGrid");
      }

      // Hiển thị sách miễn phí
      const freeBooks = allBooks.filter((book) => book.mienPhi == 0);
      renderBooks(freeBooks, "freeBooks");

      // Hiển thị sách mới nhất
      const startDate = new Date("2025-04-20");
      const endDate = new Date("2025-07-20");
      const newestBooks = allBooks.filter((book) => {
        const releaseDate = new Date(book.ngayPhatHanh);
        return releaseDate >= startDate && releaseDate <= endDate;
      });
      renderBooks(newestBooks, "newestBooks");
    })
    .catch((error) => console.error("Lỗi khi lấy danh sách sách:", error));
}

// render sách
function renderBooks(books, gridId, showAll = false) {
  const bookGrid = document.getElementById(gridId);
  bookGrid.innerHTML = "";

  if (books.length === 0) {
    bookGrid.innerHTML = `<p>Không tìm thấy sách phù hợp cho ${
      gridId === "freeBooks"
        ? "sách miễn phí"
        : gridId === "newestBooks"
        ? "sách mới nhất"
        : "tìm kiếm hoặc thể loại đã chọn"
    }.</p>`;
    return;
  }

  const gridStyle = window.getComputedStyle(bookGrid);
  const gridColumns = gridStyle
    .getPropertyValue("grid-template-columns")
    .split(" ").length;
  const maxBooksToShow = gridColumns * 2;

  const booksToShow = showAll ? books : books.slice(0, maxBooksToShow);
  booksToShow.forEach((book) => {
    const bookDiv = document.createElement("div");
    bookDiv.className = "book-item";
    bookDiv.style.cursor = "pointer";
    bookDiv.innerHTML = `
      <div class="book-img-container">
        <img src="http://localhost:8080/${book.hinhAnh}" alt="${
      book.tenSach
    }" class="book-img"/>
        <div class="price-badge ${book.mienPhi === 0 ? "free" : "paid"}">
          ${
            book.mienPhi === 0 ? "Miễn phí" : `${book.gia.toLocaleString()} VNĐ`
          }
        </div>
      </div>
      <h3 class="book_title">${book.tenSach}</h3>
    `;
    bookDiv.addEventListener("click", () => {
      window.location.href = `chitietsachtv.html?maSach=${book.maSach}`;
    });
    bookGrid.appendChild(bookDiv);
  });

  if (books.length > maxBooksToShow) {
    const viewMoreDiv = document.createElement("div");
    viewMoreDiv.className = "view-more";
    viewMoreDiv.innerHTML = showAll
      ? `<button class="view-less-btn">Ẩn bớt</button>`
      : `<button class="view-more-btn">Xem thêm</button>`;
    bookGrid.appendChild(viewMoreDiv);

    const button = viewMoreDiv.querySelector("button");
    button.addEventListener("click", () => {
      renderBooks(books, gridId, !showAll);
    });
  }
}

// sự kiện thay đổi thể loại
document
  .getElementById("filterDropdown")
  .addEventListener("change", function () {
    const selectedCategory = this.value;
    fetchBooks(
      selectedCategory,
      document.getElementById("searchInput").value.trim().toLowerCase()
    );
  });

function logout() {
  localStorage.clear();
  window.location.href = "trangchu.html";
}
