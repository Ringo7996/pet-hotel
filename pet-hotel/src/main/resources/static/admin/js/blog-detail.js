const btnModalImage = document.getElementById("btn-modal-image");
const btnChoseImage = document.getElementById("btn-chose-image");
const inputThumbnail = document.getElementById("avatar");
const btnDeleteImage = document.getElementById("btn-delete-image");
const imageContainer = document.querySelector(".image-container");
const imageThumbnailEl = document.getElementById("thumbnail")

let imageList = [];
btnModalImage.addEventListener("click", async () => {
    try {
        //Gọi API
        const data = await fetch("/api/v1/users/1/files");
        const dataJson = await data.json();
        imageList = dataJson;

        //Hien thi hinh anh
        renderPagination(imageList);

    } catch (e) {
        console.log(e);
    }
})


function renderImage(imageList) {
    imageContainer.innerHTML = "";

    //tạo nội dung
    let html = "";
    imageList.forEach(i => {
        html += `
                <div class="image-item" onclick="choseImage(this)" data-id="${i.id}">
                     <img src="/api/v1/files/${i.id}"  alt="">
                </div>
			`
    })
    imageContainer.innerHTML = html;
}


function renderPagination(imageList) {
    $('.pagination-container').pagination({
        dataSource: imageList,
        pageSize: 12,
        callback: function (data, pagination) {
            renderImage(data);
        }
    })
}

function choseImage(element) {
    // xoá hết selected ở mọi element
    const imageSelected = document.querySelector(".image-item.selected")
    if (imageSelected) {
        imageSelected.classList.remove("selected")
    }

    //thêm selected vào
    element.classList.add("selected")

    //Active 2 chuc nang
    btnChoseImage.disabled = false;
    btnDeleteImage.disabled = false;

    btnDeleteImage.addEventListener("click", async () => {
        try {
            const imageSelected = document.querySelector(".image-item.selected")
            const imageId = +imageSelected.dataset.id;


            await fetch(`/api/v1/files/${imageId}`, {
                method: "DELETE"
            })


            imageList = imageList.filter(i => i.id !== imageId)
            renderPagination(imageList);

            btnChoseImage.disabled = true;
            btnDeleteImage.disabled = true;
        } catch (e) {
            console.log(e);
        }
    })

}


btnChoseImage.addEventListener("click", () => {
    const imageSelected = document.querySelector(".image-item.selected")
    const url = imageSelected.querySelector("img").getAttribute("src");
    thumbnailEl.src = url;

    // đóng modal
    $("#modal-xl").modal("hide");
    btnChoseImage.disabled = true;
    btnDeleteImage.disabled = true;

})


//Upload file
inputThumbnail.addEventListener("change", (event) => {
    // Lấy ra file
    const file = event.target.files[0]
    console.log(file)

    // Tạo đối tượng formData
    const formData = new FormData();
    formData.append("file", file)

    // Gọi API
    fetch(`/api/v1/files`, {
        method: "POST",
        body: formData
    })
        .then((res) => {
            return res.json()
        })
        .then(res => {
            imageList.unshift(res);
            renderPagination(imageList);
        })
        .catch(err => {
            console.log(err)
        })
})


