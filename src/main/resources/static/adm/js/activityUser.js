activityUsers = (e=>{
        let id = e.getAttribute("data-id");
        let father =e.parentElement.parentElement.parentElement.parentElement;
        let root = father.parentElement;
        let activeType = document.querySelector(".active-type");
        let type = activeType.getAttribute("data-link");

        let btnDelete =  `<button type="button"
                                    class="btn btn-danger user-delete-btn"
                                    onclick="deleteUsers(this)"
                                    data-id="${id}"
                                    > Delete
                                </button>`

        $.post(`http://localhost:8080/api/v1/users/activity-user/${id}`,)
            .done(function(data) {
                if(type ==="all"){
                    e.parentElement.innerHTML = btnDelete
                }else{
                    root.removeChild(father);
                }
                console.log(data);
            })
            .fail(function(jqXHR, textStatus, errorThrown) {
                // Xử lý lỗi
                var errorMessage = jqXHR.responseText;

                // Nếu dữ liệu trả về là JSON, bạn có thể sử dụng:
                var errorMessage = jqXHR.responseJSON.message;
                alert(errorMessage)
                console.error(textStatus, errorThrown);
            });
})

