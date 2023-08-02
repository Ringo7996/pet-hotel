//get element





let hotelId = document.getElementById("id").innerText;

const api1Url =`http://localhost:8080/api/v1/room-type/get-room-type-not-part-of-hotel/${hotelId}`;
const api2Url =`http://localhost:8080/api/v1/admin/get-admin-not-part-of-hotel/${hotelId}`;


const fetchGetRoomType = fetch(api1Url).then(response => response.json());
const fetchGetAdmin = fetch(api2Url).then(response => response.json());
console.log(fetchGetRoomType,fetchGetAdmin)

let listRoomType;
let listAdmin;
let deleteTypeRoom;
let deleteAdmin;


Promise.all([fetchGetRoomType, fetchGetAdmin])
    .then(([result1, result2]) => {
        listRoomType = result1;
        listRoomType.forEach(e=>{
            e.roomNumber = 1;
        })
        listAdmin = result2;
        letGo(listRoomType,listAdmin);
    })
    .catch(error => {
        console.error(error);
    });
function letGo(listRoomType,listAdmin){
    let newData =[...listRoomType];
    let inputfile = document.getElementById("file-img");
    let btnUpload = document.getElementById("btn-upload");
    let img = document.getElementById("img-review");
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

//form info
    let formInfoHotel = document.querySelector(".form-info-hotel");

    let numbersRoom = document.querySelectorAll(".numbersRoom");
    numbersRoom.forEach(e=>{
        e.onchange = ()=>{
            if(e.value.trim() ==="") e.value =1;
        }
    })

// Room Type
    function RoomType(id,name,price,roomNumber){
        this.name = name;
        this.id =id;
        this.price = price;
        this.roomNumber = roomNumber;
    }
    let close = document.getElementById("close");
    let addListRoom = document.getElementById("addListRoom");
    let tbodyTypeRoom = document.getElementById("table-room-type");
    let btnAddInTypeRoom = document.querySelector(".btn-add-in-room-type");
    let tbodyAddTypeRoom = document.getElementById("tbody-add-type-room");

    btnAddInTypeRoom.setAttribute("data-toggle","modal");
    btnAddInTypeRoom.setAttribute("data-target","#add-type-room");
    btnAddInTypeRoom.classList.remove("btn-secondary");
    btnAddInTypeRoom.classList.add("btn-info")

    btnAddInTypeRoom.onclick =()=>{
        tbodyAddTypeRoom.innerHTML="";
        newData.forEach(e=>{
            let newElement = `
                  <tr>
                       <td class="id">${e.id}</td>
                       <td >${e.name}</td>
                       <td >${e.price}</td>
                       <td >
                            <input class="numbersRoom" value="${e.roomNumber}" min="1" style="max-width: 80px; outline: #0c84ff" type="number">
                        </td>
                        <td>
                            <input class="isCheck" style="width: 20px; height: 20px;" type="checkbox">
                        </td>
                   </tr>  
            `
            tbodyAddTypeRoom.innerHTML += newElement;
        })
    }


    addListRoom.onclick =()=>{
        let isChecks =document.querySelectorAll(".isCheck");
        let listRoomTypes =[];
        isChecks.forEach((e)=>{
            let father = e.parentElement.parentElement;
            if(e.checked){
                let id = father.querySelector(".id").innerText;
                id = Number(id);
                let roomNumber = father.querySelector(".numbersRoom").value;
                let roomType = findByid(id);
                roomType.roomNumber = Number(roomNumber);
                roomType.roomNumber = roomNumber;
                listRoomTypes.push(roomType);
                deleteByid(id,newData);
            }
        })
        close.click();
        if(listRoomTypes.length>0){
             listRoomTypes.forEach(e=>{
                let newElement = `
                  <tr>
                       <td class="id" >${e.id}</td>
                       <td class="name" >${e.name}</td>
                       <td class="price">${e.price}</td>
                       <td  class="roomNumber">${e.roomNumber}</td>
                        <td>
                        <a href="/admin/room-types/${e.id}/detail">
                             <button type="button" class="btn btn-info px-4">Detail </button>
                        </a>
                             <button data-id="${e.id}" onclick="deleteTypeRoom(this)" type="button" class="btn btn-danger px-4 btn-dl-type-room">Delete </button>
                        </td>
                   </tr>  
                 `
                tbodyTypeRoom.innerHTML+= newElement;
            })
        }
    }

    // staff
    let newData2 = [...listAdmin];


    let tbodyAddAdmin = document.getElementById("tbody-add-admin");
    let btnInAddStaff = document.querySelector(".btn-in-add-staff");
    let btnAddStaff = document.getElementById("addAdminList");
    let closePopUpAdmin = document.querySelector("#close-admin");
    let tbodyAdmin = document.getElementById("tbodyAdmin");

    btnInAddStaff.setAttribute("data-toggle","modal");
    btnInAddStaff.setAttribute("data-target","#add-admin");
    btnInAddStaff.classList.remove("btn-secondary");
    btnInAddStaff.classList.add("btn-info")

    btnInAddStaff.onclick =()=>{
        tbodyAddAdmin.innerHTML="";
        newData2.forEach(e=>{
            let newElement = `
                  <tr>
                       <td class="id">${e.id}</td>
                       <td class="name" >${e.name}</td>
                       <td class="email" >${e.email}</td>
                       <td class="phone" >${e.phone}</td>
                        <td>
                            <input class="isCheck_add-Admin" style="width: 20px; height: 20px;" type="checkbox">
                        </td>
                   </tr>  
            `
            tbodyAddAdmin.innerHTML += newElement;
        })
    }
    function Admin (id,name,email,phone){
        this.name = name;
        this.id= id;
        this.phone = phone;
        this.email = email;
    }

    btnAddStaff.onclick=()=>{
        let isChecks =document.querySelectorAll(".isCheck_add-Admin");
        let listAdminSystem =[];

        isChecks.forEach((e)=>{
            let father = e.parentElement.parentElement;
            if(e.checked){
                let id = father.querySelector(".id").innerText;
                let name =father.querySelector(".name").innerText;
                let email = father.querySelector(".email").innerText;
                let phone = father.querySelector(".phone").innerText;

                let admin = new Admin(id,name,email,phone);

                listAdminSystem.push(admin);
                id= Number(id);
                deleteByid(id,newData2);
            }
        })
        closePopUpAdmin.click();
        if(listAdminSystem.length>0){
            listAdminSystem.forEach(e=>{
                let newElement = `
                  <tr>
                       <td class="id" >${e.id}</td>
                       <td class="name" >${e.name}</td>
                       <td class="email">${e.email}</td>
                       <td  class="phone">${e.phone}</td>
                        <td>
                        <a href="/admin/users/${e.id}/detail">
                             <button type="button" class="btn btn-info px-4">Detail </button>
                        </a>
                             <button data-id="${e.id}" onclick="deleteAdmin(this)" type="button" class="btn btn-danger px-4 btn-dl-type-room">Delete </button>
                        </td>
                   </tr>  
                 `
                tbodyAdmin.innerHTML+= newElement;
            })
        }
    }




// function
       deleteTypeRoom = (e)=>{

        let id = e.getAttribute("data-id");
        id=Number(id);
        let father = e.parentElement.parentElement;
        let roomType;
        if(!findByid(id)){
            let name = father.querySelector(".name").innerText;
            let price = father.querySelector(".price").innerText;
            let roomNumber = father.querySelector(".roomNumber").innerText;

            roomType  = new RoomType(id,name,price,roomNumber);

        }else {
            roomType= findByid(id);
        }
        newData.push(roomType);

        tbodyTypeRoom.removeChild(father);
    }
    deleteAdmin = (e)=>{
        let father = e.parentElement.parentElement;

        let id = father.querySelector(".id").innerText;
        let name =father.querySelector(".name").innerText;
        let email = father.querySelector(".email").innerText;
        let phone = father.querySelector(".phone").innerText;

        let admin = new Admin(id,name,email,phone);
        newData2.push(admin);

        tbodyAdmin.removeChild(father);
    }


    function findByid(id){
        return newData.find(e=>e.id === id);
    }
    function deleteByid(id,data){
        for( let i=0; i<data.length; i++){
            let e = data[i];
            if(e.id === id ){
                data.splice(i,1);
                return true;
            }
        }
        return false;
    }

}


