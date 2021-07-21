<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Showings</title>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=heebo">
<link rel="stylesheet" href="style.css">
</head>
<body>

	<%@ page import="java.util.List" %>
	<%@ page import="java.util.ArrayList" %>
	<%@ page import="java.util.Set" %>
	<%@ page import="java.util.HashSet" %>
	<%@ page import="com.fdmgroup.model.Movie" %>
	<%@ page import="com.fdmgroup.model.MovieShowing" %>
	<%@ page import="com.fdmgroup.model.Customer" %>
	<%@ page import="java.time.LocalDate" %>
	
	<% 	List<MovieShowing> allMovieShowings = (ArrayList<MovieShowing>) request.getAttribute("movieShowings"); 
		Set<Movie> moviesHashSet = new HashSet<Movie>();
		Set<LocalDate> datesHashSet = new HashSet<LocalDate>();
		
		for (MovieShowing movieShowing : allMovieShowings) {
			moviesHashSet.add(movieShowing.getMovie());
			datesHashSet.add(movieShowing.getShowingTime().toLocalDateTime().toLocalDate());
			System.out.println(movieShowing.getShowingTime());
		}
		
		List<Movie> movies = new ArrayList<Movie>(moviesHashSet);
		
		List<LocalDate> dates = new ArrayList<LocalDate>(datesHashSet);
		
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
	
	<div id="filterModal" class="modal">
		<div class="modal-content">
			<button id="close" onclick="closeFilter()">&times;</button>
			<br>
			<h1 class="page_title text">Filter</h1>
			<div id="filters">
				<h2 class="filter_title text">Movies</h2>
				<fieldset class="options">
					<% for (int i = 0; i < movies.size(); i++) { %>
					<div class="filter_row">
						<label class="filter_left text" for="movie_<%= movies.get(i).getId() %>"><%= movies.get(i).getTitle() %></label>
						<input class="filter_right movie_input text" type="checkbox" value="<%= movies.get(i).getId() %>" name="movie_<%= movies.get(i).getId()%>">
					</div>
					<% } %>
				</fieldset>
				<h2 class="filter_title text">Dates</h2>
				<fieldset class="options">
					<% for (int i = 0; i < dates.size(); i++) { %>
					<div class="filter_row">
						<label class="filter_left text" for="date_<%= dates.get(i).toString() %>"><%= dates.get(i).toString() %></label>
						<input class="filter_right date_input text" type="checkbox" value="<%= dates.get(i).toString() %>" name="date_<%= dates.get(i).toString()%>">
					</div>
				<% } %>
				</fieldset>
			</div>
			<button class="filter_apply button blue text" id="filter_apply" onclick="filter()">Apply</button>
		</div>
	</div>
	
	<h1 class="page_title text">Showings</h1>
	<div class="search_bar">
		<form class="search_form" action="Showings" method="GET">
			<button type="button" class="filter button blue text" onclick="showFilter()">Filter</button>
  			<button class="search button blue text" type="submit">Search</button>
			<span class="span">
  				<input class="search_input text"type="text" placeholder="Search..." name="searchString">
  			</span>
  		</form>
  	</div>
	<div class="movie_showings">
		<% for (int i = 0; i < movies.size(); i++) { %>
		<% Movie movie = movies.get(i); %>
		<% List<MovieShowing> movieShowings = new ArrayList<MovieShowing>();
		
			for (MovieShowing movieShowing : allMovieShowings) {
				if (movieShowing.getMovie() == movie) {
					movieShowings.add(movieShowing);
				}
			}
		%>
		<div class="movie" id="movie_div_<%= movie.getId() %>">
			<div class="movie_info">
				<div class="movie_left">
					<a href="movie.jsp?id=<%= movie.getId() %>"><img class="movie_image_showing" src="images/<%= movie.getTitle().toLowerCase() %>.jpg" alt=<%= movie.getTitle().toLowerCase() %>></a>
				</div>
				<div class="movie_right">
					<a class="title_link" href="movie.jsp?id=<%= movie.getId() %>"><h2 class="title text"><%= movie.getTitle() %></h2></a>
					<p class="summary text">Directed by <%= movie.getDirector() %></p>
					<p class="summary text"><%= movie.getDuration()%> minutes</p>
					<h2 class="summary text">Summary</h2>
					<p class="summary text"><%= movie.getDescription() %></p>
				</div>
			</div>
			<h2 class="showings_title text">Showings for <%= movie.getTitle() %></h2>
			<ol class="showing_list">
				<% for (int ii = 0; ii < movieShowings.size(); ii++) { %>
				<% MovieShowing movieShowing = movieShowings.get(ii); %>
				<li class="showing_item" id="<%= movieShowing.getShowingTime().toLocalDateTime().toLocalDate().toString()%>">
					<p class="showing text">Time: <%= movieShowing.getShowingTime().toString() %></p>
					<p class="showing text">Theatre: <%= movieShowing.getTheatre().getNumber() %></p> 
					<% if (customer != null) { %>
					<a href="Checkout?id=<%= movieShowing.getId() %>" class="buy orange button showing text">Buy</a>
					<% } else { %>
					<div onclick="showModal()" class="buy orange button showing text">Buy</div>
					<% } %>
				</li>
				<% } %>
			</ol>
		</div>
		<% } %>
	</div>
	<script src="script.js"></script>
	<script>
		var filterModal = document.getElementById("filterModal");
		
		function showFilter(){
			filterModal.style.display = "block";
		}

		function closeFilter(){
			filterModal.style.display = "none";
		}

		window.onclick = function(event) {
		  if (event.target == filterModal) {
		    closeFilter();
		  }
		}
		
		function filter() {
			var movie_inputs = document.getElementsByClassName("movie_input");
			var date_inputs = document.getElementsByClassName("date_input");
			
			for (let movie_input of movie_inputs) {
				var movie_id = movie_input.value;
				var movie_div = document.getElementById("movie_div_" + movie_id);
				if (movie_input.checked) {					
					var showing_items = movie_div.getElementsByClassName("showing_item");
					var showing_count = 0;
					movie_div.style.display = "block";
					for (let showing_item of showing_items) {
						showing_item.style.display = "none";
						var showing_date = showing_item.id;
						for (let date_input of date_inputs) {
							if (date_input.checked && showing_date == date_input.value) {
								showing_item.style.display = "block";
								showing_count++;
							}
						}
					}
					if (showing_count == 0) {
						movie_div.style.display = "none";
					}
				} else {
					movie_div.style.display = "none";
				}
			}
		}
	</script>
</body>
</html>