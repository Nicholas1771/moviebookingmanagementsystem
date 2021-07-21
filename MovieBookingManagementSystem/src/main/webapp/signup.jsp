<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<title>Sign Up</title>
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
	
	<h1 class="page_title text">Sign Up</h1>
		
	<div class="signup_div">
		<div class="form_row">
			<label class="form_item_left text" for="firstName">First Name</label>
			<input id="signupFirstName" class="form_item_right text" placeholder="Enter First Name..." name="firstName" required>
		</div>
		<br>
		<div class="form_row">
			<label class="form_item_left text" for="lastName">Last Name</label>
			<input id="signupLastName" class="form_item_right text" type="text" placeholder="Enter Last Name..." name="lastName" required>
		</div>
		<br>
		<div class="form_row">
			<label class="form_item_left text" for="email">Email</label>
			<input id="signupEmail" class="form_item_right text" type="email" placeholder="Enter Email..." name="email" required>
		</div>
		<br>
		<div class="form_row">
			<label class="form_item_left text" for="password">Password</label>
			<input id="signupPassword" class="form_item_right text" type="password" placeholder="Enter Password..." name="password" required>
		</div>
		<br>
		<div class="form_row">
			<label class="form_item_left text" for="confirmPassword">Confirm Password</label>
			<input id="signupConfirmPassword" class="form_item_right text" type="password" placeholder="Confirm Password..." name="confirmPassword" required>
		</div>
		<br>
	</div>
	<br>
	<p id="signupStatus" class="text"></p>
	<div class="signup_row">
			<button class="signup button blue text" onclick="signup()">Sign Up</button>
	</div>
		
	<script src="script.js"></script>
	
	<script>
		function signup() {
			console.log("signing up");
			var firstNameText = document.getElementById("signupFirstName").value;
			var lastNameText = document.getElementById("signupLastName").value;
			var emailText = document.getElementById("signupEmail").value;
			var passwordText = document.getElementById("signupPassword").value;
			var confirmPasswordText = document.getElementById("signupConfirmPassword").value;
			
			var request = new XMLHttpRequest();
			
			request.onreadystatechange = function() {
				if (this.status == 400) {
					document.getElementById("signupStatus").style.color = "red";
					signupStatus.innerHTML = this.responseText;
				} else if (this.status == 200) {
					document.getElementById("signupStatus").style.color = "green";
					signupStatus.innerHTML = this.responseText;
				}
			}
			
			request.open("POST", "SignUp", true);
			request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			request.send("email=" + emailText + "&password=" + passwordText + "&confirmPassword=" + confirmPasswordText + "&firstName=" + firstNameText + "&lastName=" + lastNameText);	
		}
	</script>
</body>
</html>