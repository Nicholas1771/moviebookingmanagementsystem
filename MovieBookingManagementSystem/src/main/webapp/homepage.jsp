<%@ page 
	language="java" 
	contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    %>
<!DOCTYPE html>
<html>
<head>
	<title>Homepage</title>
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=heebo">
	<link rel="stylesheet" href="style.css">
</head>
<body>
	
	<%@ page import="java.util.List" %>
	<%@ page import="java.util.ArrayList" %>
	<%@ page import="com.fdmgroup.model.Movie" %>
	<%@ page import="com.fdmgroup.model.Customer" %>
	
	<%
		List<Movie> movies = (ArrayList<Movie>) request.getAttribute("moviesPlaying");
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
	
	<h1 class="page_title text">Movies Playing</h1>
	<div class="search_bar">
		<form class="search_form_home" action="Home" method="GET">
			<button class="search button blue text" type="submit">Search</button>
			<span class="span">
  				<input class="search_input text"type="text" placeholder="Search..." name="searchString">
  			</span>
  		</form>
  	</div>
  	<div id="movies_home">
		<% for (int i = 0; i < movies.size(); i++) { %>			
			<% if (i % 6 == 0) { %>
		<div class="movie_container">
			<% } else if ((i+1) % 6 == 0) { %>
		<div class="movie_container">
			<% } else { %>
		<div class="movie_container">
			<% } %>
			<a href=movie.jsp?id=<%= movies.get(i).getId() %>><img class="movie_image_home" src="images/<%= movies.get(i).getTitle().toLowerCase()%>.jpg" alt="<%= movies.get(i).getTitle()%>"></a>
			<h2 class="movie_title text"><%= movies.get(i).getTitle() %></h2>
		</div>
		<% } %>
	</div>
	<script src="script.js"></script>
</body>
</html>