<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<title>Tickets</title>
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=heebo">
	<link rel="stylesheet" href="style.css">
</head>
<body>
	<%@ page import="java.util.List" %>
	<%@ page import="java.util.ArrayList" %>
	<%@ page import="com.fdmgroup.model.Customer" %>
	<%@ page import="com.fdmgroup.model.Ticket" %>
	<%@ page import="com.fdmgroup.model.MovieShowing" %>
	<%@ page import="java.util.Set" %>
	<%@ page import="java.util.HashSet" %>
	
	<%
		Customer customer = null;
		if (request.getSession().getAttribute("customer") != null) {
			customer = (Customer) request.getSession().getAttribute("customer");
		}
		
		List<Ticket> tickets = (ArrayList<Ticket>) request.getAttribute("tickets");
		List<MovieShowing> movieShowings = new ArrayList<MovieShowing>((HashSet<MovieShowing>) request.getAttribute("movieShowings"));
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
	
	<h1 class="page_title text">Tickets</h1>
		<div class="movie_showings">
		<% for (int i = 0; i < movieShowings.size(); i++) { %>
			<% MovieShowing movieShowing = movieShowings.get(i); %>
		<div class="movie">
			<div class="movie_info">
				<div class="movie_left">
					<a href="movie.jsp?id=<%= movieShowing.getMovie().getId() %>"><img class="movie_image_showing" src="images/<%= movieShowing.getMovie().getTitle().toLowerCase() %>.jpg" alt=<%= movieShowing.getMovie().getTitle().toLowerCase() %>></a>
				</div>
				<div class="movie_right">
					<a class="title_link text" href="movie.jsp?id=<%= movieShowing.getMovie().getId()%>"><h2 class="title text"><%= movieShowing.getMovie().getTitle() %></h2></a>
					<p class="summary text">Directed by <%= movieShowing.getMovie().getDirector() %></p>
					<p class="summary text"><%= movieShowing.getMovie().getDuration()%> minutes</p>
					<h2 class="summary text">Summary</h2>
					<p class="summary text"><%= movieShowing.getMovie().getDescription() %></p>
				</div>
			</div>
			<h2 class="tickets_showing text">Showing</h2>
			<div id="checkout_showing">	
				<p class="checkout_showing_item text">Movie: <%= movieShowing.getMovie().getTitle() %></p>
				<p class="checkout_showing_item text">Date: <%= movieShowing.getShowingTime().toString() %></p>
				<p class="checkout_showing_item text">Theatre: <%= movieShowing.getTheatre().getNumber() %></p>
			</div>
			<h2 class="tickets_showing text">Tickets</h2>
			<ol class="tickets_lists">
				<% for (int ii = 0; ii < tickets.size(); ii++) { %>
					<% Ticket ticket = tickets.get(ii);
						if (ticket.getMovieShowing() == movieShowing) {
					%>
			<li class="ticket_item">
				<p class="ticket text">Type: <%= ticket.getType().toString().toLowerCase() %></p>
				<p class="ticket text">Price: <%= ticket.getPrice() %></p>
				<form method="POST" action="Cancel?id=<%= ticket.getId() %>">
					<button type="submit" class="cancel red button ticket text">Cancel</button>
				</form>
			</li>
						<% } %>
				<% } %>
			</ol>
		</div>
		<% } %>
	</div>
	<script src="script.js"></script>
</body>
</html>