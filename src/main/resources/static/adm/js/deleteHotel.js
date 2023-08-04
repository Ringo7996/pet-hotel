
let btnDeleteHotels = document.querySelectorAll(".hotel-delete-btn");

btnDeleteHotels.forEach(e=>{
    e.onclick =()=>{
        let id = e.getAttribute("data-id");
        let father =e.parentElement.parentElement.parentElement;
        let root = father.parentElement;

        $.post(`http://localhost:8080/api/v1/hotels/delete-hotel/${id}`,)
            .done(function(data) {
                root.removeChild(father);
                console.log(data);
            })
            .fail(function(jqXHR, textStatus, errorThrown) {
                // Xử lý lỗi
                alert("Lỗi hệ thống")
                console.error(textStatus, errorThrown);
            });
    }
})

