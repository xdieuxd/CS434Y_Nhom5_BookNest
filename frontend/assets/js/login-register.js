document.addEventListener("DOMContentLoaded", () => {
  const loginForm = document.getElementById("login-form");
  const registerForm = document.getElementById("register-form");
  const wrapper = document.getElementById("form-wrapper");
  const message = document.getElementById("message");

  loginForm.style.opacity = "1";
  loginForm.style.pointerEvents = "all";
  registerForm.style.opacity = "0";
  registerForm.style.pointerEvents = "none";

  window.toggleForm = function () {
    wrapper.classList.toggle("slide-register");
    if (wrapper.classList.contains("slide-register")) {
      loginForm.style.opacity = "0";
      loginForm.style.pointerEvents = "none";
      registerForm.style.opacity = "1";
      registerForm.style.pointerEvents = "all";
    } else {
      loginForm.style.opacity = "1";
      loginForm.style.pointerEvents = "all";
      registerForm.style.opacity = "0";
      registerForm.style.pointerEvents = "none";
    }
  };

  registerForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const hoTen = document.getElementById("register-fullname").value;
    const email = document.getElementById("register-email").value;
    const matKhau = document.getElementById("register-password").value;
    const sdt = document.getElementById("register-phone").value;
    const diaChi = document.getElementById("register-address").value;

    const res = await fetch("http://localhost:8080/api/nguoi-dung", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        hoTen: hoTen,
        email: email,
        matKhau: matKhau,
        sdt: sdt,
        diaChi: diaChi,
        maVaiTro: 1,
      }),
    });

    if (res.ok) {
      message.textContent = "Đăng ký thành công!";
      message.className = "message";
      toggleForm();
    } else {
      message.textContent = "Đăng ký thất bại!";
      message.className = "error";
    }
  });

  loginForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const email = document.getElementById("login-email").value;
    const matKhau = document.getElementById("login-password").value;

    const res = await fetch("http://localhost:8080/api/nguoi-dung/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        email: email,
        matKhau: matKhau,
      }),
      credentials: "include",
    });

    if (res.ok) {
      const data = await res.json();
      console.log("Dữ liệu từ API login:", data);
      localStorage.setItem("nguoiDung", JSON.stringify(data));

      localStorage.setItem("token", data.token || "");
      localStorage.setItem("maNguoiDung", data.maNguoiDung);
      localStorage.setItem("email", data.email);
      localStorage.setItem("maVaiTro", data.maVaiTro);

      if (data.maVaiTro === 3) {
        window.location.href = "admin.html";
      } else if (data.maVaiTro === 2) {
        window.location.href = "nhanvien.html";
      } else {
        window.location.href = "thanhvien.html";
      }
    } else {
      message.textContent = "Đăng nhập thất bại!";
      message.className = "error";
    }
  });
});
