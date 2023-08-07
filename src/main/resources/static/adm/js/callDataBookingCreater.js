
let sLCity = document.getElementById("city");
getCity();
function getCity(){
    $.get(`/api/v1/hotels/city`,)
        .done(function(data) {
            console.log(data);
            renderOp("city",data);
        })
        .fail(function(jqXHR, textStatus, errorThrown) {
            console.error(textStatus, errorThrown);
        });
}
function getDistrict(city){
    $.get(`/api/v1/hotels/district?city=${city}`,)
        .done(function(data) {
            console.log(data);
            renderOp("district",data);
        })
        .fail(function(jqXHR, textStatus, errorThrown) {
            console.error(textStatus, errorThrown);
        });
}

function renderOp(id,data){
    let element = document.getElementById(id);
    console.log(element)
    let arrData = [];
     data.forEach(e=>{
         let op  = {
             value: e,
             text: e,
         }
         arrData.push(op);
     })
    element.innerHTML="";
    if(id === "city"){
        let optionElementDefault = document.createElement("option");
        optionElementDefault.value = "1";
        optionElementDefault.text = "Chọn";
        element.appendChild(optionElementDefault);
    }
    arrData.forEach(function(option) {
        let optionElement = document.createElement("option");
        optionElement.value = option.value;
        optionElement.text = option.text;
        element.appendChild(optionElement);
    });
}


sLCity.onchange =()=>{
    let city = sLCity.value;
    getDistrict(city);
}

