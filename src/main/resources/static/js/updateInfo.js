let form = document.getElementById("updateInfo");

//validate
let validate = () => {
    let name = document.getElementById("name").value;
    let email = document.getElementById("email").value;
    let phone = document.getElementById("phone").value;
    let mess = document.getElementById("mess");

    if (name.trim() === "" || phone.trim() === "" || email.trim() === "") {
        mess.innerText = "Điền đủ thông tin"
        return false;
    }
    // Kiểm tra số điện thoại
    var phoneRegex = /^[0-9]{10}$/;
    if (!phoneRegex.test(phone)) {
        mess.innerText = "Vui lòng nhập số điện thoại hợp lệ (gồm 10 chữ số)!";
        return false;
    }

    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        mess.innerText = "Vui lòng nhập địa chỉ email hợp lệ!";
        return false;
    }

    let data = {
        name,
        email,
        phone,
    };
    $.ajax({
        url: '/api/v1/auth/update-info',
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (res) {
            alert("Chỉnh sủa thành công");
            location.reload();
        },
        error: function (xhr, status, error) {
            if (xhr.status === 400) {
                mess.innerText = "Email đã tồn tại"
            }
        },
    });

    return true;
}
form.onsubmit = (e) => {
    e.preventDefault();
    validate();
}


// upload ảnh

let bthUpload = document.querySelector(".btn-upload");
let formUpload = document.querySelector(".file");
let img = document.querySelector(".img-responsive");

console.log(bthUpload)

bthUpload.onclick = () => {
    console.log(formUpload)
    formUpload.click();
}
formUpload.addEventListener('change', function () {
    const file = formUpload.files[0];
    const reader = new FileReader();
    reader.addEventListener('load', function () {
        img.src = reader.result;
    }, false);

    if (file) {
        reader.readAsDataURL(file);
    }
    var data = new FormData();
    data.append('file', file);
    $.ajax({
        url: 'http://localhost:8080/api/v1/files',
        type: 'POST',
        data: data,
        contentType: false,
        enctype: 'multipart/form-data',
        processData: false,
        success: function (response) {
            console.log("ok");
        },
        error: function (jqXHR, textStatus, errorThrown, response) {
            window.alert(JSON.parse(jqXHR.responseText).message)
            console.log("fail");
        }
    })
});