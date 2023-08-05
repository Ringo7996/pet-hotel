
var deleteUsers =(e=>{
        let id = e.getAttribute("data-id");
        let father =e.parentElement.parentElement.parentElement.parentElement;
        let root = father.parentElement;
        let activeType = document.querySelector(".active-type");
        let type = activeType.getAttribute("data-link");

        let btnActive =`<button type="button"
                                        class="btn btn-primary btn-active-user"
                                        data-id="${id}"
                                        onclick="activityUsers(this)"
                                        > Active
                                </button>`

        $.post(`http://localhost:8080/api/v1/users/delete-user/${id}`,)
            .done(function(data) {
                if(type ==="all"){
                    e.parentElement.innerHTML = btnActive
                }else{
                    root.removeChild(father);
                }
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
})

