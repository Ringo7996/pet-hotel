

let btnSubmit = document.getElementById("btnUpdate");
let inputfile = document.getElementById("file-img");
let img = document.getElementById("img-review");
let listRoles = document.querySelectorAll(".role");

btnSubmit.onclick=()=>{

    let name = document.getElementById("name").value;
    let email = document.getElementById("email").value;
    let phone = document.getElementById("phone").value;
    let id = document.getElementById("id").innerText;
    let fileImg = inputfile.files[0];

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


    let roles =[];
    listRoles.forEach(e=>{
        if(e.checked) roles.push(e.value);
    })

    let data = new FormData();
    data.append("name",name);
    data.append("phone",phone);
    data.append("email",email);
    data.append("roles",roles);
    if(fileImg){
        data.append("file",fileImg);
    }

    $.ajax({
        url: `http://localhost:8080/api/v1/admin/update-user/${id}`,
        type: 'POST',
        data: data,
        contentType: false,
        enctype: 'multipart/form-data',
        processData: false,
        success: function (response) {
            mess.innerText="";
            alert("Update thành công!")
        },
        error: function (jqXHR, textStatus, errorThrown, response) {
            if(jqXHR.status === 400) {
                mess.innerText="Email đã tồn tại"
            }
            console.log("fail");
        }
    })

}

let btnUpload = document.getElementById("btn-upload");
inputfile.onchange=()=>{
    let fileImg = inputfile.files[0];
    let render = new FileReader();

    render.onload =()=>{
        img.src = render.result;
    }
    if(fileImg){
        render.readAsDataURL(fileImg);
    }
}
btnUpload.onclick =()=>{
    inputfile.click();
}

var reset =()=>{

    //input
    let inputs = document.querySelectorAll(".form-control");
    inputs.forEach(e=>{
        e.value ="";
    })

    // check
    let listRoles = document.querySelectorAll(".role");
    listRoles.forEach(e=>{
        if(e.value !== "1") e.checked =false;
    })

    //img
    img.src="/images/user-profile.jpg";

}

