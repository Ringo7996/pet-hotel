
let form = document.querySelector("#sign_up");
let inputs = form.querySelectorAll(".form-control");

// validate

function validateForm() {
    // lấy element
    let mess = document.getElementById("mess");
    var name = document.getElementById("name").value;
    var phone = document.getElementById("phone").value;
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;


    if (name === ""){
        console.log(name)
        mess.innerText ="Tên không thể để trống!";
        return false;
    }

    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        console.log(email)
        mess.innerText ="Vui lòng nhập địa chỉ email hợp lệ!";
        return false;
    }

    // Kiểm tra số điện thoại
    var phoneRegex = /^[0-9]{10}$/;
    if (!phoneRegex.test(phone)) {
        mess.innerText="Vui lòng nhập số điện thoại hợp lệ (gồm 10 chữ số)!";
        return false;
    }

    // Kiểm tra mật khẩu
    if (password.length < 8) {
        mess.innerText="Mật khẩu phải chứa ít nhất 8 ký tự!";
        return false;
    }

    // Kiểm tra xác nhận mật khẩu
    if (password !== confirmPassword) {
        mess.innerText ="Xác nhận mật khẩu không khớp!";
        return false;
    }
    // Nếu tất cả điều kiện đều hợp lệ, cho phép submit form

    let data = {
        name,
        email,
        password,
        phone,
    }
    $.ajax({
        url: '/api/v1/auth/create-user',
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function(res) {
            alert("Đăng ký thành công");
            location.href="/login";
        },
        error: function(xhr, status, error) {
            if(xhr.status === 400){
                mess.innerText="Email đã tồn tại"
            }
        },
    });
    return true;
}
form.onsubmit = (e)=>{
    e.preventDefault()
    validateForm()

}

