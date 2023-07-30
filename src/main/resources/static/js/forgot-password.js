
let form = document.getElementById("form-forgot-pasword");

//validate
let validate = async ()=>{
    let email = document.getElementById("email").value

    if(email.trim() ==="") {
        mess.innerText ="Vui lòng nhập email"
        return false;
    }

    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        mess.innerText ="Vui lòng nhập địa chỉ email hợp lệ!";
        return false;
    }

    try {
        const dataform ={
            email,
        }
        const data = await fetch(`http://localhost:8080/api/v1/auth/forgot-password`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(dataform)
        })

        if (data.status === 200) {
            alert("Vui lòng mở email để xác thực");
        }
    } catch (e) {
        console.log(e)
    }
}
form.onsubmit =(e)=>{
    e.preventDefault()
    validate()
}