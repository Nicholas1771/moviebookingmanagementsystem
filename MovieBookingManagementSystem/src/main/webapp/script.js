var modal = document.getElementById("loginModal");

function login() {
	console.log("logging in");
	var emailText = document.getElementById("emailText").value;
	var passwordText = document.getElementById("passwordText").value;
	console.log(emailText + " " + passwordText);
	var request = new XMLHttpRequest();
	
	request.onreadystatechange = function() {
		if (this.status == 400) {
			loginStatus.innerHTML = this.responseText;
			document.getElementById("passwordText").value = "";
		} else if (this.status == 200) {
			location.reload();
		}
	}
	
	request.open("POST", "Login", true);
	request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	request.send("email=" + emailText + "&password=" + passwordText);
}

function showModal(){
	modal.style.display = "block";
}

function closeModal(){
	modal.style.display = "none";
}

window.onclick = function(event) {
  if (event.target == modal) {
    closeModal();
  }
}