<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<title>Checkout</title>
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=heebo">
	<link rel="stylesheet" href="style.css">
</head>
<body>

	<%@ page import="java.util.List" %>
	<%@ page import="java.util.ArrayList" %>
	<%@ page import="com.fdmgroup.model.Customer" %>
	<%@ page import="com.fdmgroup.model.MovieShowing" %>
	
	<%
		Customer customer = null;
		if (request.getSession().getAttribute("customer") != null) {
			customer = (Customer) request.getSession().getAttribute("customer");
		}
		
		MovieShowing movieShowing = (MovieShowing) request.getAttribute("movieShowing");
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
	<h1 class="page_title text">Checkout</h1>
	
	<div class="checkout_info">
		<div class="movie_left">
			<img class="movie_image_checkout" src="images/<%= movieShowing.getMovie().getTitle().toLowerCase() %>.jpg"
				alt="<%= movieShowing.getMovie().getTitle().toLowerCase() %>">
		</div>
		<div class="movie_right">
			<h2 class="title text"><%= movieShowing.getMovie().getTitle() %></h2>
			<p class="summary text">Directed by <%= movieShowing.getMovie().getDirector() %></p>
			<p class="summary text"><%= movieShowing.getMovie().getDuration()%> minutes</p>
			<h2 class="summary text">Summary</h2>
			<p class="summary text"><%= movieShowing.getMovie().getDescription() %></p>
		</div>
	</div>
	
	<div id="checkout_showing">
		<p class="checkout_showing_item text">Time: <%= movieShowing.getShowingTime().toString() %></p>
		<p class="checkout_showing_item text">Theatre: <%= movieShowing.getTheatre().getNumber() %></p>
	</div>
	
	<div id="checkout_div">
	
		<h1 class="page_title text">Cart</h1>
	
		<form class="checkout_form" action="Checkout?id=<%= movieShowing.getId() %>" method="POST">
	
			<div class="form_row">
				<label class="form_item_left text" for="general">General $13.99</label>
				<input class="form_item_right text" onchange="valueChanged()" id="general" class="form_item" type="number" value=0 min=0 name="general" required>
			</div>
			<br>
			<div class="form_row">
				<label class="form_item_left text" for="child">Child (12 and under) $8.99</label>
				<input class="form_item_right text" onchange="valueChanged()" id="child" class="form_item" type="number" value=0 min=0 name="child" required>
			</div>
			<br>
			<div class="form_row">
				<label class="form_item_left text" for="senior">Senior (65 and over) $9.99</label>
				<input class="form_item_right text" onchange="valueChanged()" id="senior" class="form_item" type="number" value=0 min=0 name="senior" required>
			</div>
			<br>
			<div class="form_row">
				<label class="form_item_left text" for="total">Total</label>
				<p class="form_item_right text" id="amount">$0.00</p>
			</div>
			<br>
			<div class="form_row">
				<button class="form_item_middle button orange text" id="purchase" type="submit">Purchase</button>
			</div>
		</form>
	</div>
	
	<script>
		var total = 0.0;
		
		function valueChanged() {
			let general = document.getElementById("general");
			let child = document.getElementById("child");
			let senior = document.getElementById("senior");
			
			let total = 0;
			
			if (general.value > 0) {
				total += general.value * 13.99;
			}
			if (child.value > 0) {
				total += child.value * 8.99;
			}
			if (senior.value > 0) {
				total += senior.value * 9.99;
			}
			
			let totalElement = document.getElementById("amount");
			totalElement.innerText = "$" + total;
		}
	</script>
	<script src="script.js"></script>
</body>
</html>