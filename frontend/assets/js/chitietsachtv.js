const user = JSON.parse(localStorage.getItem("nguoiDung"));
const maNguoiDung = user ? user.maNguoiDung : null;
const hoTenNguoiDung = user ? user.hoTen : "Ẩn danh";

const usernameSpan = document.getElementById("username");
if (user && user.hoTen) {
  usernameSpan.textContent = `👤 ${user.hoTen}`;
} else {
  window.location.href = "login-register.html";
}

const urlParams = new URLSearchParams(window.location.search);
const maSach = urlParams.get("maSach");

if (maSach) {
  if (maNguoiDung) {
    document.getElementById("commentFormContainer").style.display = "block";
  } else {
    document.getElementById("loginPrompt").style.display = "block";
  }

  fetch(`http://localhost:8080/api/sach/${maSach}`)
    .then((response) => response.json())
    .then((book) => {
      document.getElementById(
        "bookImage"
      ).src = `http://localhost:8080/${book.hinhAnh}`;
      document.getElementById("bookImage").alt = book.tenSach;
      document.getElementById("bookTitle").textContent = book.tenSach;
      document.getElementById("bookPrice").textContent =
        book.mienPhi == 0 ? "Miễn phí" : `${book.gia.toLocaleString()} VNĐ`;
      document.getElementById("bookReleaseDate").textContent = new Date(
        book.ngayPhatHanh
      ).toLocaleDateString("vi-VN");
      document.getElementById("bookStock").textContent = book.soLuongTon;
      document.getElementById("bookDescription").textContent =
        book.moTa || "Không có mô tả";

      const priceBadge = document.getElementById("priceBadge");
      priceBadge.textContent =
        book.mienPhi === 0 ? "Miễn phí" : `${book.gia.toLocaleString()} VNĐ`;
      priceBadge.className = `price-badge ${
        book.mienPhi === 0 ? "free" : "paid"
      }`;

      const actionDiv = document.getElementById("bookAction");
      if (book.soLuongTon <= 0) {
        actionDiv.innerHTML = `<span class="out-of-stock">Hết hàng</span>`;
      } else if (book.mienPhi == 0) {
        actionDiv.innerHTML = `<a href="http://localhost:8080/${book.tenFilePdf}" target="_blank" class="action-btn">Đọc sách</a>`;
      } else {
        actionDiv.innerHTML = `<button id="buyButton" class="action-btn">🛒 Mua sách</button>`;

        document.getElementById("buyButton").addEventListener("click", () => {
          fetch(`http://localhost:8080/api/gio-hang/${maNguoiDung}`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
              id: {
                maSach: maSach,
              },
              soLuong: 1,
            }),
          })
            .then((res) => {
              if (res.ok) {
                window.location.href = "giohang.html";
              } else {
                return res.text().then((msg) => {
                  throw new Error(msg);
                });
              }
            })
            .catch((err) => {
              console.error("Lỗi khi thêm vào giỏ hàng:", err);
              alert("Không thể thêm vào giỏ hàng.");
            });
        });
      }

      if (maNguoiDung) {
        document.getElementById("reviewFormContainer").style.display = "block";
      }

      if (book.maTacGia) {
        fetch(`http://localhost:8080/api/tac-gia/${book.maTacGia}`)
          .then((res) => res.json())
          .then((tacGia) => {
            document.getElementById("bookAuthor").textContent =
              tacGia.tenTacGia;
          });
      } else {
        document.getElementById("bookAuthor").textContent = "Không xác định";
      }

      fetch("http://localhost:8080/api/sach-the-loai")
        .then((res) => res.json())
        .then((dsSTL) => {
          const dsMaTheLoai = dsSTL
            .filter((stl) => stl.id.maSach == maSach)
            .map((stl) => stl.id.maTheLoai);
          fetch("http://localhost:8080/api/categories")
            .then((res) => res.json())
            .then((dsTL) => {
              const theLoaiText = dsTL
                .filter((tl) => dsMaTheLoai.includes(tl.id))
                .map((tl) => tl.name)
                .join(", ");
              document.getElementById("bookCategories").textContent =
                theLoaiText || "Không xác định";
            });
        });

      fetch(`http://localhost:8080/api/danh-gia?maSach=${maSach}`)
        .then((res) => res.json())
        .then((filtered) => {
          console.log("Danh sách đánh giá:", filtered);
          const danhGiaList = document.getElementById("danhGiaList");
          danhGiaList.innerHTML = "";

          let sum = 0;
          filtered.forEach((dg) => {
            if (!dg.soSao) dg.soSao = 0;
            sum += dg.soSao;

            const div = document.createElement("div");
            div.className = "review-item";
            div.innerHTML = `
        <p><strong>${"⭐".repeat(dg.soSao)}</strong></p>
        <p>${dg.noiDung || "(Không có nội dung)"}</p>
      `;
            danhGiaList.appendChild(div);
          });

          if (filtered.length > 0) {
            const avg = (sum / filtered.length).toFixed(1);
            const avgDiv = document.createElement("div");
            avgDiv.className = "average-rating";
            avgDiv.innerHTML = `<strong>⭐ Trung bình:</strong> ${avg} sao`;
            danhGiaList.prepend(avgDiv);
          }
        });

      fetch(`http://localhost:8080/api/binh-luan?maSach=${maSach}`)
        .then((res) => res.json())
        .then((comments) => {
          const container = document.getElementById("binhLuanList");
          container.innerHTML = "";

          comments.forEach((c) => {
            const div = document.createElement("div");
            div.className = "comment-item";
            div.innerHTML = `
        <p><strong>${c.tenNguoiDung || "Ẩn danh"}</strong> - ${new Date(
              c.ngayBinhLuan
            ).toLocaleString("vi-VN")}</p>
        <p>${c.noiDung}</p>`;
            container.appendChild(div);
          });
        });
    });

  document
    .getElementById("reviewForm")
    .addEventListener("submit", function (e) {
      e.preventDefault();
      const rating = document.getElementById("rating").value;
      const comment = document.getElementById("reviewComment").value.trim();
      const errorDiv = document.getElementById("reviewError");

      if (!comment) {
        errorDiv.textContent = "Vui lòng nhập nội dung đánh giá.";
        errorDiv.style.display = "block";
        return;
      }

      fetch(
        `http://localhost:8080/api/don-hang?maSach=${maSach}&maNguoiDung=${maNguoiDung}`
      )
        .then((res) => res.json())
        .then((orders) => {
          if (!orders || orders.length === 0)
            return alert("Bạn cần mua sách để đánh giá");
          fetch("http://localhost:8080/api/danh-gia", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
              maDonHang: orders[0].maDonHang,
              maNguoiDung,
              maSach,
              soSao: parseInt(rating),
              noiDung: comment || null,
            }),
          }).then(() => location.reload());
        });
    });

  document
    .getElementById("commentForm")
    .addEventListener("submit", function (e) {
      e.preventDefault();
      const content = document.getElementById("comment").value.trim();
      if (!content) return;
      fetch("http://localhost:8080/api/binh-luan", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ maSach, maNguoiDung, noiDung: content }),
      }).then(() => location.reload());
    });
}

function logout() {
  localStorage.clear();
  window.location.href = "trangchu.html";
}
document.getElementById("commentForm").addEventListener("submit", function (e) {
  e.preventDefault();
  const content = document.getElementById("comment").value.trim();
  if (!content) return;

  fetch("http://localhost:8080/api/binh-luan", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ maSach, maNguoiDung, noiDung: content }),
  })
    .then((res) => res.json())
    .then((newComment) => {
      const container = document.getElementById("binhLuanList");
      const div = document.createElement("div");
      div.className = "comment-item";
      div.innerHTML = `
        <p><strong>${hoTenNguoiDung}</strong> - ${new Date().toLocaleString(
        "vi-VN"
      )}</p>
        <p>${content}</p>`;
      container.appendChild(div);
      document.getElementById("comment").value = "";
    });
});
let page = 1;
const limit = 5;
function loadComments() {
  fetch(
    `http://localhost:8080/api/binh-luan?maSach=${maSach}&page=${page}&limit=${limit}`
  )
    .then((res) => res.json())
    .then((comments) => {
      const container = document.getElementById("binhLuanList");
      comments.forEach((c) => {
        const div = document.createElement("div");
        div.className = "comment-item";
        div.innerHTML = `
          <p><strong>${c.tenNguoiDung || "Ẩn danh"}</strong> - ${new Date(
          c.ngayBinhLuan
        ).toLocaleString("vi-VN")}</p>
          <p>${c.noiDung}</p>`;
        container.appendChild(div);
      });
    });
}
const loadMoreBtn = document.createElement("button");
loadMoreBtn.textContent = "Tải thêm bình luận";
loadMoreBtn.className = "action-btn";
loadMoreBtn.addEventListener("click", () => {
  page++;
  loadComments();
});
document.getElementById("binhLuanList").after(loadMoreBtn);
document.getElementById("addToLibraryBtn").addEventListener("click", () => {
  const user = JSON.parse(localStorage.getItem("nguoiDung"));
  if (!user || !user.maNguoiDung) {
    alert("Vui lòng đăng nhập trước khi thêm vào thư viện.");
    return;
  }

  fetch("http://localhost:8080/api/thu-vien", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      maNguoiDung: user.maNguoiDung,
      maSach: maSach,
    }),
  })
    .then((res) => {
      if (res.ok) {
        alert("Đã thêm vào thư viện!");
      } else {
        return res.text().then((msg) => {
          throw new Error(msg);
        });
      }
    })
    .catch((err) => {
      console.error("Lỗi khi thêm vào thư viện:", err);
      alert("Thêm thất bại hoặc đã tồn tại!");
    });
});
