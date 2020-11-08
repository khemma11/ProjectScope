

function sendGetRequest(method, url) {
    const headers = {
        'Content-Type': 'application/json'
    };
    return fetch(url, {
        method: method,
        headers: headers
    }).then(response => {
        if (!response.ok) {
            return response.status;
        }
        return response.json();
    });
}

function sendPostRequest(method, url, body) {

    const headers = {
        'Content-Type': 'application/json'
    };
    return fetch(url, {
        method: method,
        headers: headers,
        body: JSON.stringify(body)
    }).then(response => {
        return response.json();
    });

}

$(document).on("click",".txt",function (){

    let form = $("#avatar")[0];
    let data = new FormData(form);
    console.log(data);
    let profilePicture = "";
    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/user/profilePicture",
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 1000,
        success: function (data) {
            console.log(data);

            profilePicture = data.profilePicture;

            let name = $("#firstName").val();
            let surname = $("#lasttName").val();
            let email = $("#email").val();
            let password = $("#password").val();
            // let userType = $("#userTypeSelect").val();



            let body = {
                name: name,
                surname: surname,
                email: email,
                password: password,
                profilePicture:profilePicture,
                // userType:userType,

            };

            sendPostRequest("POST", "/user/saveUser", body).then(
                data => {
                    console.log(data);
                    if(data == "Created"){
                        window.location.href = "/index";
                    }else{
                        $(".clearfix").prepend("<p style=' color: pink'>Օնլայն հարթակի գրանցման սխալ, խնդրում ենք կրկին և ճիշտ լրացնել դաշտերը՝ գրանցման համար</p>");
                    }
                }
            ).catch();


        },
        error: function (data) {
            window.location = "/error";
        }
    });


});

if(error!=null){
    $(".clearfix").html("<p style='color: white'>" + error + "</p>");
}


