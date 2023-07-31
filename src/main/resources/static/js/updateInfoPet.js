let form = document.getElementById("updateInfo");

//validate
let validate = () => {
    let name = document.getElementById("name").value;
    let type = document.getElementById("type").value;
    let color = document.getElementById("color").value;
    let breed = document.getElementById("breed").value;
    let sex = document.querySelector('input[name="sex"]:checked').value;

    let id = document.getElementById("id").innerText;

    let mess = document.getElementById(".mess");
    
    if (name.trim() === "" || type.trim() === "" || color.trim() === "" || breed.trim() === "")  {
        mess.innerText = "Điền đủ thông tin"
        return false;
    }
    
    let data = {
        name,
        type,
        color,
        breed,
        sex,
    };
    $.ajax({
        url: ` http://localhost:8080/api/v1/pets/update-info-pet/${id}`,
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (res) {
            alert("Chỉnh sủa thành công");
            location.reload();
        },
        error: function (xhr, status, error) {
            if (xhr.status === 400) {
                mess.innerText = "Chỉnh sửa thất bại"
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

let bthUploads = document.querySelectorAll(".btn-upload");



bthUploads.forEach(e=>{
    e.onclick = () => {
        let formUpload = e.nextElementSibling.querySelector(".file");
        let img = e.previousElementSibling;

        let id = e.getAttribute("data-id");

        formUpload.click();
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
                url: `http://localhost:8080/api/v1/file-pet/${id}`,
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

    }
})


// btn edit
var btnEdits = document.querySelectorAll(".btn-edit");


// handle click
btnEdits.forEach((element)=>{
    element.onclick =()=>{

        let sbl = element.previousElementSibling;
        let name = sbl.querySelector(".name");
        let type = sbl.querySelector(".type");
        let color = sbl.querySelector(".color");
        let sex =sbl.querySelector(".sex");
        let breed =sbl.querySelector(".breed");


        let inputname = document.getElementById("name");
        let inputbreed = document.getElementById("breed");
        let inputype = document.getElementById("type");
        let inputcolor = document.getElementById("color");
        let inputssex = document.querySelectorAll('input[name="sex"]');

        inputname.value = name.innerText;
        inputbreed.value = breed.innerText;
        inputype.value = type.innerText;
        inputcolor.value = color.innerText;
        inputssex.forEach(e=>{
            console.log(e.value === sex.innerText);
            if(e.value === sex.innerText) e.checked = true;
        })

        let eid =document.getElementById("id");
        let id= element.getAttribute("data-id");
        eid.innerText = id;
        console.log(id);
    }
})

