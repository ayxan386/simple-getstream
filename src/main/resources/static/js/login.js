let token;

function login(e) {
    e.preventDefault();
    let username = document.getElementsByName("username")[0].value;
    fetch("http://localhost:8080/login", {
        method: "POST",
        body: username
    })
        .then(response => {
            if (response.ok)
                return response.json;
            throw new Error("Wrong status");
        }).then(json => {
        token = json;
        console.log(json)
    });
}