
const form = document.getElementById("form-update-password");

//validation
let validation = async ()=>{
    const oldPw = document.getElementById("old-password").value;
    const newPw = document.getElementById("password").value;
    const confirmPw = document.getElementById("confirm-password").value;
    if (newPw.trim() === "" || oldPw.trim() == ""){
        mess.innerText ="Vui lòng nhập đủ thông tin";
        return;
    };
    if (newPw !== confirmPw){
        mess.innerText ="Xác nhận mật khẩu không khớp!";
        return;
    }

    if (newPw.length < 8 || oldPw.length < 8) {
        mess.innerText="Mật khẩu phải chứa ít nhất 8 ký tự!";
        return false;
    }



    try {
        const dataform ={
            oldPassword: oldPw,
            newPassword:newPw,
        }
        const data = await fetch(`http://localhost:8080/api/v1/auth/update-password`, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(dataform)
        })

        if (data.status === 200) {
            alert("Thay đổi thành công!");
            window.location.href = "/"
        }
        if(data.status === 400){
            mess.innerText="Không đúng mật khẩu cũ";
        }
    } catch (e) {
        console.log(e)
    }
}
form.onsubmit =(e)=>{
    e.preventDefault()
    validation()
}