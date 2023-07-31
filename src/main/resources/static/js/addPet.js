let formAddPet = document.getElementById("form-add-pet");

// validate
let validateAddForm =()=>{

    //
    let name = document.getElementById("name-pet").value;
    let breed = document.getElementById("breed-pet").value;
    let type = document.getElementById("type-pet").value;
    let color = document.getElementById("color-pet").value;
    let sex = document.querySelector('input[name="sex"]:checked');
    let mess = document.getElementById("mess-create");
    if(!sex){
        mess.innerText = "Điền đủ thông tin"
        return  false;
    }
    sex =sex.value;


    if (name.trim() === "" || type.trim() === "" || color.trim() === "" || breed.trim() === "" || sex.trim() ==="")  {
        mess.innerText = "Điền đủ thông tin"
        return false;
    }
    let data = new FormData()

    data.append("file",file.files[0]);
    data.append("sex",sex);
    data.append("name",name);
    data.append("breed",breed);
    data.append("color",color);
    data.append("type",type);

    $.ajax({
        url: ` http://localhost:8080/api/v1/pets/add-new-pet`,
        type: 'POST',
        data:data,
        enctype: 'multipart/form-data',
        contentType: false,
        processData: false,
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
}

formAddPet.onsubmit=(e)=>{
    e.preventDefault();
    validateAddForm();
}

let btnAdd = document.querySelector(".btn-add");
console.log(btnAdd);
let file = document.getElementById("add-file-pet");
btnAdd.onclick=()=>{
    let img = btnAdd.previousElementSibling;
    file.click();
    file.addEventListener('change', function () {
        let datafile = file.files[0];
        let reader = new FileReader();
        reader.addEventListener('load', function () {
            img.src = reader.result;
        }, false);

        if (datafile) {
            reader.readAsDataURL(datafile);
        }
    })

}