
const form = document.getElementById("form-login");
const mess = document.getElementById("mess");


form.addEventListener("submit", async (e) => {
    e.preventDefault();
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    if (password.trim() === "" || email.trim() === "") {
        console.log(email)
        console.log(password)
        mess.innerText = "Vui lòng nhập đầu đủ dữ liệu"
        return false;
    }else {
        mess.innerText ="";
    }

    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        mess.innerText ="Vui lòng nhập địa chỉ email hợp lệ!";
        return false;
    }
    // Kiểm tra mật khẩu
    // if (password.length < 8) {
    //     mess.innerText="Mật khẩu phải chứa ít nhất 8 ký tự!";
    //     return false;
    // }

    try {
        const request = {
            email,
            password,
        }
        const data = await fetch("/api/v1/auth/login-handle", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(request)
        })
        if (data.status === 200) {
            window.location.href = "/"
        }
        if (data.status === 400) {
            mess.innerText="Tài khoản hoặc mật khấu không đúng";
        }
    } catch (e) {
        console.log(e);
    }
})