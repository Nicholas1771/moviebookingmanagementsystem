<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<title>Profile</title>
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=heebo">
	<link rel="stylesheet" href="style.css">
</head>
<body>

	<%@ page import="java.util.List" %>
	<%@ page import="java.util.ArrayList" %>
	<%@ page import="com.fdmgroup.model.Customer" %>
	
	<%
		Customer customer = null;
		if (request.getSession().getAttribute("customer") != null) {
			customer = (Customer) request.getSession().getAttribute("customer");
		}
	%>

	<div id="navbar">
		<a class="navlink navlink_left text" href="Home">Home</a>
		<a class="navlink navlink_left text" href="Showings">Showings</a>
		<% if (customer != null) { %>
		<div id="profile" class="navlink navlink_right text"><%= customer.getFirstName() %>
		<% } else {%>
		<div id="profile" class="navlink navlink_right text">Profile
		<% } %>
			<div id="dropdown-content">
				<% if (customer != null) { %>
				<a class="navlink" href="profile.jsp">My Profile</a>
				<a class="navlink" href="Tickets">My Tickets</a>
				<a class="navlink" id="login" href="Logout">Logout</a>
				<% } else { %>
				<div id="login" class="navlink" onclick="showModal()">Login</div>
				<% } %>
			</div>
		</div>
		<a class="navlink navlink_right text" href="signup.jsp">Sign Up</a>
	</div>
	
	<div id="loginModal" class="modal">
		<div class="modal-content">
			<button id="close" onclick="closeModal()">&times;</button>
			<br>
			<h1 class="page_title text">Login</h1>
			<div class="login_div">
				<div class="form_row">
					<label class="form_item_left text" for="email">Email</label>
					<input id="emailText" class="form_item_right text" type="email" placeholder="Enter Email..." name="email" required>
				</div>
				<br>
				<div class="form_row">
					<label class="form_item_left text" for="password">Password</label>
					<input id="passwordText" class="form_item_right text" type="password" placeholder="Enter Password..." name="password" required>
				</div>
			</div>
			<br>
			<p id="loginStatus" class="text"></p>	
			<div class="login_row">
				<button class="login button blue text" onclick="login()">Login</button>
			</div>	
		</div>
	</div>
	
	<h1 class="page_title text">Your Info</h1>
	<div class="profile_div">
		<div class="profile_item">
		<label class="profile text" for="email">Email</label>
		<input id="profileEmail" class="profile text" type="email" value="<%= customer.getEmail() %>"  name="email" readonly>
		</div>
		<div class="profile_item">
		<label class="profile text" for="firstName">First Name</label>
		<input id="profileFirstName" class="profile text" type="text" value="<%= customer.getFirstName() %>"  name="firstName">
		<button class="profile button change blue text" onclick="change('firstName')">Change</button>
		</div>
		<div class="profile_item">
		<label class="profile text" for="lastName">Last Name</label>
		<input id="profileLastName" class="profile text" type="text" value="<%= customer.getLastName() %>"  name="lastName">
		<button class="profile button change blue text" onclick="change('lastName')">Change</button>
		</div>
		<div class="profile_item">
		<label class="profile text" for="password">Password</label>
		<input id="profilePassword" class="profile text" type="password" value="<%= customer.getPassword() %>" name="password">
		<button class="profile button change blue text" onclick="change('password')">Change</button>
		</div>
		<button class="deactivate button red text" onclick="deactivate()">Deactivate Account</button>
		<p id="profileStatus" class="text"></p>
	</div>
	<script src="script.js"></script>
	
	<script>
	function change(type) {
		console.log("changing profile info");
		var firstNameText = document.getElementById("profileFirstName").value;
		var lastNameText = document.getElementById("profileLastName").value;
		var passwordText = document.getElementById("profilePassword").value;
		
		var request = new XMLHttpRequest();
		
		request.onreadystatechange = function() {
			if (this.status == 400) {
				document.getElementById("profileStatus").style.color = "red";
				profileStatus.innerHTML = this.responseText;
			} else if (this.status == 200) {
				document.getElementById("profileStatus").style.color = "green";
				profileStatus.innerHTML = this.responseText;
			}
		}
		
		request.open("POST", "Change", true);
		request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		
		if (type == 'password') {
			request.send("password=" + passwordText);
		} else if (type == 'firstName') {
			console.log("fName" + firstNameText);
			request.send("firstName=" + firstNameText);
		} else if (type == 'lastName') {
			request.send("lastName=" + lastNameText);
		}
	}
	
	function deactivate() {
		var email = prompt("WARNING - You are about to permenently delete your account including any tickets you have purchased. Enter you email address to confirm.", "");
		
		var request = new XMLHttpRequest();
		
		request.onreadystatechange = function() {
			if (this.status == 400) {
				document.getElementById("profileStatus").style.color = "red";
				profileStatus.innerHTML = this.responseText;
			} else if (this.status == 200) {
				var homeRequest = new XMLHttpRequest();
				window.location.href = "signup.jsp";
			}
		}
		
		request.open("POST", "Change", true);
		request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		
		if (email != null) {
			console.log("deactivating");
			request.send("email=" + email);
		}
	}
	</script>
</body>
</html>