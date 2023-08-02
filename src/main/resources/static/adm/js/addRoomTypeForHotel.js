//get element
let hotelId = document.getElementById("id").innerText;
let listRoomType;
let deleteTypeRoom;
fetch(`http://localhost:8080/api/v1/room-type/get-room-type-not-part-of-hotel/${hotelId}`)
    .then(response => response.json())
    .then(data => {

        listRoomType = data;
        listRoomType.forEach(e=>{
            e.roomNumber = 1;
        })
        letGo(listRoomType);
    })
    .catch(error => console.error("lỗi"));


function letGo(listRoomType){
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
    let btnAddInTypeRoom = document.querySelector(".btn-add-ỉn-room-type");
    let tbodyAddTypeRoom = document.getElementById("tbody-add-type-room");

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
                deleteByid(id);
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
// delete
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

    function findByid(id){
        return newData.find(e=>e.id === id);
    }
    function deleteByid(id){
        for( let i=0; i<newData.length; i++){
            let e = newData[i];
            if(e.id === id ){
                newData.splice(i,1);
                return true;
            }
        }
        return false;
    }
}


