
const form = document.getElementById("form-reset-password");

//validation
let validation = async ()=>{
    const email = document.getElementById("email").innerText;
    const newPw = document.getElementById("password").value;
    const confirmPw = document.getElementById("confirm-password").value;
    if (newPw.trim() === ""){
        mess.innerText ="Vui lòng nhập đủ thông tin";
        return;
    };
    if (newPw !== confirmPw){
        mess.innerText ="Xác nhận mật khẩu không khớp!";
        return;
    }

    if (newPw.length < 8 || confirmPw.length < 8) {
        mess.innerText="Mật khẩu phải chứa ít nhất 8 ký tự!";
        return false;
    }
    try {
        const formData ={
            email,
            password : newPw,
        }
        const data = await fetch(`http://localhost:8080/api/v1/auth/reset-password`, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(formData)
        })

        if (data.status === 200) {
            alert("Thay đổi thành công!");
            window.location.href = "/"
        }
    } catch (e) {
        console.log(e)
    }
}
form.onsubmit =(e)=>{
    e.preventDefault()
    validation()
}