let btnCancel = document.querySelector(".reset");
let btnBack = document.querySelector(".back");
btnBack.onclick =()=>{
    window.history.back();
}

btnCancel.onclick=()=>{
    reset();
}