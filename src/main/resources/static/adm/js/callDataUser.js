

let typeUsers = document.querySelectorAll(".type-user");
let activeBtn = document.querySelector(".active-type");

let slide =document.querySelector(".slide");
slide.style.left = activeBtn.offsetLeft + "px";
slide.style.width = activeBtn.offsetWidth + "px";
window.addEventListener('resize', function() {
    slide.style.left = activeBtn.offsetLeft + "px";
    slide.style.width = activeBtn.offsetWidth + "px";
});

console.log(slide)
typeUsers.forEach(e=>{
   e.onclick =()=>{
        let userTable = document.getElementById("user-table");
        userTable.innerHTML ="";
        let activeBtn = document.querySelector(".active-type");
        activeBtn.classList.toggle("active-type");

       var domain = window.location.host;
       console.log(domain);


        slide.style.left = e.offsetLeft + "px";
        slide.style.width = e.offsetWidth + "px";

        e.classList.toggle("active-type");

        let link =e.getAttribute("data-link");

        getData(link);

    }
})

function getData(link){
    $.get(`http://localhost:8080/api/v1/users/get-user?type=${link}`,)
        .done(function(data) {
           let content =data.content;
           let page = data.pageable.pageNumber;
           render(content,page);
           console.log(content);
        })
        .fail(function(jqXHR, textStatus, errorThrown) {
            // Xử lý lỗi
            var errorMessage = jqXHR.responseText;

            // Nếu dữ liệu trả về là JSON, bạn có thể sử dụng:
            var errorMessage = jqXHR.responseJSON.message;
            console.error(textStatus, errorThrown);
        });
}
function  render(content,page){
    if(!content || content.length <1 ) return false;
    var domain ="http://" + window.location.host;
    content.forEach(user=>{

        let newHtml = `
            <tr>
                <td>
                    <a > ${user.id}</a>
                </td>
                <td>
                    <a> ${user.name}</a>
                </td>
                <td>
                    <a>${user.email}</a>
                </td>
                <td>
                    <a>${user.phone}</a>
                </td>
                <td>
                   <div class="role_${user.id}">
                   </div>
                </td>
                <td>
                    <span>
                        <a href="${domain}/admin/users/${user.id}/detail">
                        <button type="button"
                                class="btn btn-info px-4"> Detail  </button>
                        </a>
                        <span data-btn ="${user.status}" class="btn-check">
                                 <button type="button"
                                        class="btn btn-danger user-delete-btn"
                                        onclick="deleteUsers(this)"
                                        data-id="${user.id}"> Delete 
                                </button>
                        </span>
                       
                        </span>
                </td>
            </tr>
        `
        let userTable = document.getElementById("user-table");
        userTable.innerHTML += newHtml;
        let role = userTable.querySelector(`.role_${user.id}`);
        user.roles.forEach(e=>{
            let span = `<span style="display: inline-block; padding: 0 4px" >${e.name}</span>`
            role.innerHTML +=span;
        })
        renderBtn(user.id);
    })
}

function  renderBtn (id){
    let spans = document.querySelectorAll(".btn-check");

    let btnActive =`<button type="button"
                                    class="btn btn-primary btn-active-user"
                                    data-id="${id}"
                                    onclick="activityUsers(this)"
                                    > Active
                            </button>`

    let btnDelete = `<button type="button"
                                    class="btn btn-danger user-delete-btn"
                                    onclick="deleteUsers(this)"
                                    data-id="${id}"
                                    > Delete
                            </button>`

    spans.forEach(e=>{
        if(e.getAttribute("data-btn") === "true"){
            e.innerHTML = btnDelete;
        }else e.innerHTML = btnActive;
    })
}