
let btnSubmit = document.getElementById("btn-submit");
let inputfile = document.getElementById("file-img");
let img = document.getElementById("img-review");

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


btnSubmit.onclick=()=>{

    let name = document.getElementById("name").value;
    let city = document.getElementById("city").value;
    let district = document.getElementById("district").value;
    let address = document.getElementById("address").value;
    let description = document.getElementById("description").value;
    console.log(name,city,district,description,address)
    let fileImg = inputfile.files[0];

    let mess = document.getElementById("mess");

    if (name.trim() === "" || city.trim() === "" || district.trim() === "" || address.trim() === ""|| description.trim() === ""){
        mess.innerText = "Điền đủ thông tin"
        return false;
    }


    let submitData = new FormData();

    submitData.append("name",name);
    submitData.append("city",city);
    submitData.append("district",district);
    submitData.append("address",address);
    submitData.append("description",description);

    if(fileImg){
        submitData.append("fileImage",fileImg);
    }

    $.ajax({
        url: 'http://localhost:8080/api/v1/admin/create-hotel',
        type: 'POST',
        data: submitData,
        contentType: false,
        enctype: 'multipart/form-data',
        processData: false,
        success: function (response) {
            mess.innerText="";
            alert("Tạo thành công")
            reset();
        },
        error: function (jqXHR, textStatus, errorThrown, response) {
            if(jqXHR.status === 400) {
                mess.innerText="Tên đã tồn tại"
            }
            console.log("fail");
        }
    })

}