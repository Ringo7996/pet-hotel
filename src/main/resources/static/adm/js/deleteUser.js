
let btnDeleteUsers = document.querySelectorAll(".user-delete-btn");

btnDeleteUsers.forEach(e=>{
    e.onclick =()=>{
        let id = e.getAttribute("data-id");
        let father =e.parentElement.parentElement.parentElement;
        let root = father.parentElement;

        $.post(`http://localhost:8080/api/v1/users/delete-user/${id}`,)
            .done(function(data) {
                root.removeChild(father);
                console.log(data);
                if(data === "/logout"){

                    location.href =data;
                }
            })
            .fail(function(jqXHR, textStatus, errorThrown) {
                // Xử lý lỗi
                var errorMessage = jqXHR.responseText;

                // Nếu dữ liệu trả về là JSON, bạn có thể sử dụng:
                var errorMessage = jqXHR.responseJSON.message;
                alert(errorMessage)
                console.error(textStatus, errorThrown);
            });
    }
})

