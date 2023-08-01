let btnCancel = document.querySelector(".cancel");
let btnBack = document.querySelector(".back");
btnBack.onclick =()=>{
    window.history.back();
}

btnCancel.onclick=()=>{
    reset();
}