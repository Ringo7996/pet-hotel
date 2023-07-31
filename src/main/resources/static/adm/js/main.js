//Active Menu
function activeMenu(){
    const links = document.querySelectorAll("#menu a")
    const currentPath = window.location.pathname;
        console.log(currentPath)

    links.forEach((link) => {
        console.log(link.getAttribute("href"));
        const path = link.getAttribute("href");
        if (currentPath === path){
        link.querySelector("span").classList.add("active")
        }
    })
}
 activeMenu();


//Toggle Theme
const btnThemeToggle = document.getElementById("theme-toggle");
    btnThemeToggle.addEventListener("click", () => {
    if(document.body.classList.contains("dark")){
        localStorage.setItem("theme","light")
    } else{
        localStorage.setItem("theme","dark")
    }
    document.body.classList.toggle("dark");
})

//Active Theme
function activeTheme(){
    const themeValue = localStorage.getItem("theme");
    if (themeValue){
        if (themeValue ==="dark"){
            document.body.classList.add("dark");
        } else{
            document.body.classList.remove("dark");
        }
    } else{
        document.body.classList.remove("dark");
    }
}
activeTheme()


