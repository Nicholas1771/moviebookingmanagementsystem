package com.fdmgroup.dao;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import com.fdmgroup.model.Customer;
import com.fdmgroup.model.Movie;
import com.fdmgroup.model.MovieShowing;
import com.fdmgroup.model.Theatre;
import com.fdmgroup.model.Ticket;
import com.fdmgroup.model.TicketTypes;

public class DaoTest {

	private static MovieDao movieDao;
	private static CustomerDao customerDao;
	private static TheatreDao theatreDao;
	private static MovieShowingDao movieShowingDao;
	private static TicketDao ticketDao;

	public static void main(String[] args) {
		movieDao = new MovieDao();
		customerDao = new CustomerDao();
		theatreDao = new TheatreDao();
		movieShowingDao = new MovieShowingDao();
		ticketDao = new TicketDao();

		write();
//		read();
		update();
//		remove();
//		search();
//		advancedQuery();
	}

	private static void demo() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Press enter to persist customer");
		scanner.nextLine();
		demoPersistCustomer(scanner);
		System.out.println("Press enter to remove ticket");
		scanner.nextLine();
		demoRemoveTicket(scanner);
		System.out.println("Press enter to find all movies");
		scanner.nextLine();
		demoFindAllMovies();
		System.out.println("Press enter to find all movie showings");
		scanner.nextLine();
		demoFindAllMovieShowings();
		System.out.println("Press enter to search movie showing");
		scanner.nextLine();
		demoSearchMovieShowings(scanner);
		System.out.println("Press enter to get movies showings on specific date within specific time frame");
		scanner.nextLine();
		demoFindMovieShowingsAtSpecificDateTimeWithinRange(scanner);
	}

	// Basic operations
	private static void demoPersistCustomer(Scanner scanner) {
		System.out.println("Existing customers:");
		customerDao.getCustomers().forEach((customer) -> System.out.println(customer));
		System.out.println("Creating customer");
		System.out.print("Email: ");
		String email = scanner.nextLine();
		System.out.print("First name: ");
		String firstName = scanner.nextLine();
		System.out.print("Last name: ");
		String lastName = scanner.nextLine();
		System.out.print("Password: ");
		String password = scanner.nextLine();
		customerDao.create(new Customer(email, firstName, lastName, password));
		System.out.println("Customers after creation:");
		customerDao.getCustomers().forEach((customer) -> System.out.println(customer));
	}

	private static void demoRemoveTicket(Scanner scanner) {
		System.out.println("Existing tickets:");
		ticketDao.getTickets().forEach((ticket) -> System.out.println(ticket));
		System.out.println("Remove ticket with id: ");
		int removeTicketId = scanner.nextInt();
		scanner.nextLine();
		Ticket ticket = ticketDao.findById(removeTicketId);
		System.out.println("Removing ticket " + ticket);
		ticketDao.remove(ticket);
		System.out.println("Tickets after remove:");
		ticketDao.getTickets().forEach((ticket2) -> System.out.println(ticket2));
	}

	//Basic queries
	private static void demoFindAllMovies() {
		System.out.println("Getting all movies");
		List<Movie> movies = movieDao.getMovies();
		System.out.println("Movies:");
		for (Movie movie : movies) {
			System.out.println(movie);
		}
	}

	private static void demoFindAllMovieShowings() {
		System.out.println("Getting all movie showings");
		List<MovieShowing> movieShowings = movieShowingDao.getMovieShowings();
		System.out.println("Movie Showings:");
		for (MovieShowing movieShowing : movieShowings) {
			System.out.println(movieShowing);
		}

	}

	// Advanced queries
	private static void demoSearchMovieShowings(Scanner scanner) {
		System.out.print("Search for movie showing: ");
		String movieShowingSearchString = scanner.nextLine();
		System.out.println("Searching movie showings with string " + movieShowingSearchString);
		List<MovieShowing> movieShowings = movieShowingDao.search(movieShowingSearchString);
		for (MovieShowing movieShowing : movieShowings) {
			System.out.println(movieShowing);
		}

		System.out.print("Search for movie showing: ");
		String movieShowingSearchString2 = scanner.nextLine();
		System.out.println("Searching movie showings with string " + movieShowingSearchString2);
		List<MovieShowing> movieShowings2 = movieShowingDao.search(movieShowingSearchString2);
		for (MovieShowing movieShowing : movieShowings2) {
			System.out.println(movieShowing);
		}
	}

	private static void demoFindMovieShowingsAtSpecificDateTimeWithinRange(Scanner scanner) {
		System.out.println("Getting movies showings on a specific day within a specific time range");
		System.out.print("Use current day?(y/n): ");
		char response = scanner.nextLine().charAt(0);
		int year;
		int month;
		int day;
		if (response == 'y') {
			LocalDate date = LocalDate.now();
			year = date.getYear();
			month = date.getMonthValue();
			day = date.getDayOfMonth();
		} else {
			System.out.print("Year: ");
			year = scanner.nextInt();
			scanner.nextLine();
			System.out.print("Month: ");
			month = scanner.nextInt();
			scanner.nextLine();
			System.out.print("Day: ");
			day = scanner.nextInt();
			scanner.nextLine();
		}
		System.out.print("Hour: ");
		int hour = scanner.nextInt();
		scanner.nextLine();
		System.out.print("Minute: ");
		int minute = scanner.nextInt();
		scanner.nextLine();
		LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute);
		System.out.print("Get movie showings within minutes: ");
		int maxMinuteDifference = scanner.nextInt();
		scanner.nextLine();
		System.out.println("Getting movie showings within " + maxMinuteDifference + " minutes of " + dateTime);
		List<MovieShowing> movieShowings5 = movieShowingDao.getMovieShowingsNearDateTime(dateTime, maxMinuteDifference);
		for (MovieShowing movieShowing : movieShowings5) {
			System.out.println(movieShowing);
		}

	}

	private static void write() {
		System.err.println("WRITE");
		// Movies
		System.err.println("WRITE - MOVIE");
		Movie movie1 = new Movie("Us", "In order to get away from their busy lives, the Wilson family takes a vacation to Santa Cruz, California with the plan of spending time with their friends, the Tyler family. On a day at the beach, their young son Jason almost wanders off, causing his mother Adelaide to become protective of her family. That night, four mysterious people break into Adelaide's childhood home where they're staying. The family is shocked to find out that the intruders look like them, only with grotesque appearances.", "Nick", 104);
		Movie movie2 = new Movie("Lion King", "In Africa, the lion cub Simba is the pride and joy of his parents King Mufasa and Queen Sarabi. Mufasa prepares Simba to be the next king of the jungle. However, the naive Simba believes in his envious uncle Scar that wants to kill Mufasa and Simba to become the next king. He lures Simba and his friend Nala to go to a forbidden place and they are attacked by hyenas but they are rescued by Mufasa. Then Scar plots another scheme to kill Mufasa and Simba but the cub escapes alive and leaves the kingdom believing he was responsible for the death of his father. Now Scar becomes the king supported by the evil hyenas while Simba grows in a distant land. Sometime later, Nala meets Simba and tells that the kingdom has become a creepy wasteland. What will Simba do?", "Jimmy", 125);
		Movie movie3 = new Movie("Spongebob", "In the first-ever all CGI SpongeBob motion picture event, THE SPONGEBOB MOVIE: SPONGE ON THE RUN, SpongeBob SquarePants, his best friend Patrick, and the Bikini Bottom gang star in their most epic adventure movie yet! When SpongeBob's beloved pet snail Gary goes missing, a path of clues leads SpongeBob and Patrick to the powerful King Poseidon, who has Gary held captive in the Lost City of Atlantic City. On their mission to save Gary, SpongeBob and his pals team up for a heroic and hilarious journey where they discover nothing is stronger than the power of friendship.", "Nick", 90);
		Movie movie4 = new Movie("Avenger", "S.H.I.E.L.D. has "
				+ "located the mysterious Tesseract device and the Army's super "
				+ "soldier Captain America. The Tesseract is actually a gateway to an "
				+ "entirely new world called Asgard. A mysterious being known as Loki "
				+ "arrives on earth and immediately assumes that he can rule all "
				+ "human beings. But that irks S.H.I.E.L.D. director Nick Fury the "
				+ "wrong way. As Loki escapes with the Tesseract, Nick Fury believes "
				+ "this is an act of war against Earth. His only hope is to assemble "
				+ "an actual team of super heroes. Dr. Bruce Banner, who turns into "
				+ "an enormous green rage monster known as the Hulk. Tony Stark and "
				+ "his venerable Iron Man armor. Captain America, the Stark "
				+ "Enterprises created super soldier. Thor, the god of thunder, "
				+ "protector of Earth and his home planet of Asgard, and Loki's "
				+ "brother. Master assassins Hawkeye and Natasha Romanoff. Together "
				+ "they will become a team to take on an attack that will call them "
				+ "to become the greatest of all time.", "Nick", 130);
		Movie movie5 = new Movie("Aladdin", "A young kindly street thief named Aladdin finds his world turned upside down when he falls in love with a beautiful young girl, who in reality is the gorgeous Princess Jasmine. As Aladdin is determined to win her heart, he comes across a mysterious oil lamp, which holds a powerful genie, who may be the key to making his dreams come true. As the duo becomes close friends, they soon find out that the lamp is also being sought by the wily Jafar, who intends to use the lamp in an evil plot to take over the kingdom and wage war on the neighboring towns.", "Rob", 95);
		Movie movie6 = new Movie("Spiderman", "Peter Parker balances his life as an ordinary high school student in Queens with his superhero alter-ego Spider-Man, and finds himself on the trail of a new menace prowling the skies of New York City. A young Peter Parker/Spider-Man begins to navigate his newfound identity as the web-slinging super hero Spider-Man.", "Rob", 100);
		Movie movie7 = new Movie("Starwars", "Set thirty years after Return of the Jedi, The Force Awakens follows Rey, Finn, Poe Dameron, and Han Solo's search for Luke Skywalker and their fight in the Resistance, led by General Leia Organa and veterans of the Rebel Alliance, against Kylo Ren and the First Order, a successor to the Galactic Empire.", "Jimmy", 110);
		Movie movie8 = new Movie("Guardians of the Galaxy", "Brash space adventurer Peter Quill (Chris Pratt) finds himself the quarry of relentless bounty hunters after he steals an orb coveted by Ronan, a powerful villain. To evade Ronan, Quill is forced into an uneasy truce with four disparate misfits: gun-toting Rocket Raccoon, treelike-humanoid Groot, enigmatic Gamora, and vengeance-driven Drax the Destroyer. But when he discovers the orb's true power and the cosmic threat it poses, Quill must rally his ragtag group to save the universe.", "Rob", 165);
		Movie movie9 = new Movie("Terminator", "Sent back from a dystopian 2029--where the cold machines have conquered the entire world--to 1984 Los Angeles, the indestructible cyborg-assassin known as the \"Terminator\" commences his deadly mission to kill humankind's most important woman: the unsuspecting Sarah Connor. However, from the same war-torn post-apocalyptic future comes a battle-scarred defender--Kyle Reese, a brave soldier of the human Resistance Army--bent on stopping the cybernetic killer from eliminating the world's last hope. But, the Terminator has no feelings, he doesn't sleep, and above all, he won't stop until he carries out his grim task. Does our future lie in our past?", "Nick", 89);
		Movie movie10 = new Movie("Black Widow", "In Marvel Studios’"
				+ "action-packed spy thriller “Black Widow,” Natasha Romanoff aka"
				+ "Black Widow confronts the darker parts of her ledger when a"
				+ "dangerous conspiracy with ties to her past arises. Pursued by a"
				+ "force that will stop at nothing to bring her down, Natasha must"
				+ "deal with her history as a spy and the broken relationships left"
				+ "in her wake long before she became an Avenger.", "Jimmy", 115);

		movieDao.create(movie1);
		movieDao.create(movie2);
		movieDao.create(movie3);
		movieDao.create(movie4);
		movieDao.create(movie5);
		movieDao.create(movie6);
		movieDao.create(movie7);
		movieDao.create(movie8);
		movieDao.create(movie9);
		movieDao.create(movie10);

		// Customers
		System.err.println("WRITE - CUSTOMER");
		Customer customer1 = new Customer("nicholas@email.com", "Nicholas", "Lozzo", "nicholasPassword");
		Customer customer2 = new Customer("aurora@email.com", "Aurora", "Guar", "auroraPassword");
		Customer customer3 = new Customer("sam@email.com", "Sam", "Jones", "samPassword");
		Customer customer4 = new Customer("susan@email.com", "Susan", "Smith", "susanPass123");
		Customer customer5 = new Customer("John@email.com", "John", "Doe", "johnPass");

		customerDao.create(customer1);
		customerDao.create(customer2);
		customerDao.create(customer3);
		customerDao.create(customer4);
		customerDao.create(customer5);

		// Theatres
		System.err.println("WRITE - THEATRE");
		Theatre theatre1 = new Theatre(1, 250);
		Theatre theatre2 = new Theatre(2, 235);
		Theatre theatre3 = new Theatre(3, 386);
		Theatre theatre4 = new Theatre(4, 125);

		theatreDao.create(theatre1);
		theatreDao.create(theatre2);
		theatreDao.create(theatre3);
		theatreDao.create(theatre4);

		// Movie Showings
		System.err.println("WRITE - MOVIE SHOWING");
		MovieShowing movieShowing1 = new MovieShowing(movie1, Timestamp.valueOf(LocalDateTime.of(2021, 8, 1, 11, 0)),
				theatre1);
		MovieShowing movieShowing2 = new MovieShowing(movie2, Timestamp.valueOf(LocalDateTime.of(2021, 8, 1, 13, 0)),
				theatre2);
		MovieShowing movieShowing3 = new MovieShowing(movie3, Timestamp.valueOf(LocalDateTime.of(2021, 8, 1, 15, 0)),
				theatre3);
		MovieShowing movieShowing4 = new MovieShowing(movie4, Timestamp.valueOf(LocalDateTime.of(2021, 8, 1, 17, 0)),
				theatre1);
		MovieShowing movieShowing5 = new MovieShowing(movie5, Timestamp.valueOf(LocalDateTime.of(2021, 8, 1, 19, 0)),
				theatre2);
		MovieShowing movieShowing6 = new MovieShowing(movie6, Timestamp.valueOf(LocalDateTime.of(2021, 8, 1, 21, 0)),
				theatre3);
		MovieShowing movieShowing7 = new MovieShowing(movie7, Timestamp.valueOf(LocalDateTime.of(2021, 8, 2, 11, 0)),
				theatre1);
		MovieShowing movieShowing8 = new MovieShowing(movie8, Timestamp.valueOf(LocalDateTime.of(2021, 8, 2, 13, 0)),
				theatre2);
		MovieShowing movieShowing9 = new MovieShowing(movie9, Timestamp.valueOf(LocalDateTime.of(2021, 8, 2, 15, 0)),
				theatre3);
		MovieShowing movieShowing10 = new MovieShowing(movie1, Timestamp.valueOf(LocalDateTime.of(2021, 8, 2, 17, 0)),
				theatre1);
		MovieShowing movieShowing11 = new MovieShowing(movie2, Timestamp.valueOf(LocalDateTime.of(2021, 8, 2, 19, 0)),
				theatre2);
		MovieShowing movieShowing12 = new MovieShowing(movie3, Timestamp.valueOf(LocalDateTime.of(2021, 8, 2, 21, 0)),
				theatre3);

		movieShowingDao.create(movieShowing1);
		movieShowingDao.create(movieShowing2);
		movieShowingDao.create(movieShowing3);
		movieShowingDao.create(movieShowing4);
		movieShowingDao.create(movieShowing5);
		movieShowingDao.create(movieShowing6);
		movieShowingDao.create(movieShowing7);
		movieShowingDao.create(movieShowing8);
		movieShowingDao.create(movieShowing9);
		movieShowingDao.create(movieShowing10);
		movieShowingDao.create(movieShowing11);
		movieShowingDao.create(movieShowing12);

		// Tickets
		System.err.println("WRITE - TICKET");
		Ticket ticket1 = new Ticket(movieShowing2, 9.99, TicketTypes.SENIOR, customer1);
		Ticket ticket2 = new Ticket(movieShowing3, 13.99, TicketTypes.GENERAL, customer2);
		Ticket ticket3 = new Ticket(movieShowing5, 8.99, TicketTypes.CHILD, customer3);
		Ticket ticket4 = new Ticket(movieShowing6, 9.99, TicketTypes.SENIOR, customer4);
		Ticket ticket5 = new Ticket(movieShowing7, 13.99, TicketTypes.GENERAL, customer1);
		Ticket ticket6 = new Ticket(movieShowing8, 8.99, TicketTypes.CHILD, customer2);
		Ticket ticket7 = new Ticket(movieShowing11, 9.99, TicketTypes.SENIOR, customer3);
		Ticket ticket8 = new Ticket(movieShowing12, 13.99, TicketTypes.GENERAL, customer4);
		Ticket ticket9 = new Ticket(movieShowing5, 8.99, TicketTypes.CHILD, customer1);
		Ticket ticket10 = new Ticket(movieShowing6, 9.99, TicketTypes.SENIOR, customer2);
		Ticket ticket11 = new Ticket(movieShowing8, 13.99, TicketTypes.GENERAL, customer3);
		Ticket ticket12 = new Ticket(movieShowing8, 8.99, TicketTypes.CHILD, customer4);
		Ticket ticket13 = new Ticket(movieShowing8, 9.99, TicketTypes.SENIOR, customer1);
		Ticket ticket14 = new Ticket(movieShowing11, 13.99, TicketTypes.GENERAL, customer2);
		Ticket ticket15 = new Ticket(movieShowing12, 8.99, TicketTypes.CHILD, customer3);
		Ticket ticket16 = new Ticket(movieShowing2, 9.99, TicketTypes.SENIOR, customer4);
		Ticket ticket17 = new Ticket(movieShowing3, 13.99, TicketTypes.GENERAL, customer1);
		Ticket ticket18 = new Ticket(movieShowing5, 8.99, TicketTypes.CHILD, customer2);
		Ticket ticket19 = new Ticket(movieShowing3, 9.99, TicketTypes.SENIOR, customer3);
		Ticket ticket20 = new Ticket(movieShowing2, 13.99, TicketTypes.GENERAL, customer4);

		ticketDao.create(ticket1);
		ticketDao.create(ticket2);
		ticketDao.create(ticket3);
		ticketDao.create(ticket4);
		ticketDao.create(ticket5);
		ticketDao.create(ticket6);
		ticketDao.create(ticket7);
		ticketDao.create(ticket8);
		ticketDao.create(ticket9);
		ticketDao.create(ticket10);
		ticketDao.create(ticket11);
		ticketDao.create(ticket12);
		ticketDao.create(ticket13);
		ticketDao.create(ticket14);
		ticketDao.create(ticket15);
		ticketDao.create(ticket16);
		ticketDao.create(ticket17);
		ticketDao.create(ticket18);
		ticketDao.create(ticket19);
		ticketDao.create(ticket20);
	}

	private static void read() {
		System.err.println("READ");

		// Movie
		System.err.println("READ - MOVIE");
		List<Movie> movies = movieDao.getMovies();
		System.out.println("Movies:");
		for (Movie movie : movies) {
			System.out.println(movie);
		}

		// Customer
		System.err.println("READ - CUSTOMER");
		List<Customer> customers = customerDao.getCustomers();
		System.out.println("Customers:");
		for (Customer customer : customers) {
			System.out.println(customer);
		}

		// Theatre
		System.err.println("READ - THEATRE");
		List<Theatre> theatres = theatreDao.getTheatres();
		System.out.println("Theatres:");
		for (Theatre theatre : theatres) {
			System.out.println(theatre);
		}

		// Movie Showing
		System.err.println("READ - MOVIE SHOWING");
		List<MovieShowing> movieShowings = movieShowingDao.getMovieShowings();
		System.out.println("Movie Showings:");
		for (MovieShowing movieShowing : movieShowings) {
			System.out.println(movieShowing);
		}

		// Ticket
		System.err.println("READ - TICKET");
		List<Ticket> tickets = ticketDao.getTickets();
		System.out.println("Tickets:");
		for (Ticket ticket : tickets) {
			System.out.println(ticket);
		}

	}

	private static void update() {
		System.err.println("UPDATE");
		// Movie
		System.err.println("UPDATE - MOVIE");
		Movie movie = movieDao.findByTitle("Avenger");
		System.out.println("Avengers movies before update: " + movie);
		movie.setTitle("Avengers");
		movieDao.update(movie);
		Movie movieAfterUpdate = movieDao.findByTitle("Avengers");
		System.out.println("Avengers movies after update: " + movieAfterUpdate);

		// Customer
		System.err.println("UPDATE - CUSTOMER");
		Customer customer = customerDao.findByEmail("nicholas@email.com");
		System.out.println("nicholas@email.com customer before update: " + customer);
		customer.setPassword("NEW_PASSWORD_123");
		customer.setLastName("Iozzo");
		customerDao.update(customer);
		Customer customerAfterUpdate = customerDao.findByEmail("nicholas@email.com");
		System.out.println("nicholas@email.com customer after update: " + customerAfterUpdate);

		// Theatre
		System.err.println("UPDATE - THEATRE");
		Theatre theatre = theatreDao.findByNumber(1);
		System.out.println("Theatre 1 before update: " + theatre);
		theatre.setCapacity(220);
		theatreDao.update(theatre);
		Theatre theatreAfterUpdate = theatreDao.findByNumber(1);
		System.out.println("Theatre 1 after update: " + theatreAfterUpdate);

		// Movie Showing
		System.err.println("UPDATE - MOVIE SHOWING");
		MovieShowing movieShowing1 = movieShowingDao.findById(2);
		System.out.println("Movie showing 2 before update: " + movieShowing1);
		movieShowing1.setShowingTime(Timestamp.valueOf(LocalDateTime.of(2021, 8, 1, 15, 30)));
		movieShowingDao.update(movieShowing1);
		MovieShowing movieShowingAfterUpdate = movieShowingDao.findById(2);
		System.out.println("Movie showing 2 after update: " + movieShowingAfterUpdate);

		// Ticket
		System.err.println("UPDATE - TICKET");
		Ticket ticket = ticketDao.findById(1);
		System.out.println("Ticket 1 before update: " + ticket);
		ticket.setType(TicketTypes.GENERAL);
		ticket.setPrice(13.99);
		ticketDao.update(ticket);
		Ticket ticketAfterUpdate = ticketDao.findById(1);
		System.out.println("Ticket 1 after update: " + ticketAfterUpdate);
	}

	private static void remove() {
		System.err.println("REMOVE");
		// Movie
		System.err.println("REMOVE - MOVIE");
		int spidermanMovieId = 6;
		Movie movie = movieDao.findById(spidermanMovieId);
		System.out.println("Removing movie " + movie);
		movieDao.remove(movie);
		Movie movie2 = movieDao.findById(spidermanMovieId);
		System.out.println("spiderman movie after remove(should be null):" + movie2);

		// Customer
		System.err.println("REMOVE - CUSTOMER");
		String removeEmail = "John@email.com";
		Customer customer = customerDao.findByEmail(removeEmail);
		System.out.println("Removing customer " + customer);
		customerDao.remove(customer);
		Customer customer2 = customerDao.findByEmail(removeEmail);
		System.out.println(removeEmail + " customer after remove(should be null):" + customer2);

		// Theatre
		System.err.println("REMOVE - THEATRE");
		int removeNumber = 4;
		Theatre theatre = theatreDao.findByNumber(removeNumber);
		System.out.println("Removing theatre " + theatre);
		theatreDao.remove(theatre);
		Theatre theatre2 = theatreDao.findByNumber(removeNumber);
		System.out.println("Theatre " + removeNumber + " after remove(should be null):" + theatre2);

		// Movie Showing
		System.err.println("REMOVE - MOVIE SHOWING");
		int movieShowingId = 4;
		MovieShowing movieShowing = movieShowingDao.findById(movieShowingId);
		System.out.println("Removing movie showing " + movieShowing);
		movieShowingDao.remove(movieShowing);
		MovieShowing movieShowing2 = movieShowingDao.findById(movieShowingId);
		System.out.println("Movie showing " + movieShowingId + " after remove(should be null):" + movieShowing2);

		// Ticket
		System.err.println("REMOVE - TICKET");
		int removeTicketId = 5;
		Ticket ticket = ticketDao.findById(removeTicketId);
		System.out.println("Removing ticket " + ticket);
		ticketDao.remove(ticket);
		Ticket ticket2 = ticketDao.findById(removeTicketId);
		System.out.println("ticket " + removeTicketId + " after remove(should be null):" + ticket2);
	}

	private static void search() {
		System.err.println("SEARCH");
		// Movie
		System.err.println("SEARCH - MOVIE");
		String movieSearchString = "M";
		System.out.println("Getting all movies with search string " + movieSearchString);
		List<Movie> moviesMatchingString = movieDao.search(movieSearchString);
		for (Movie movie : moviesMatchingString) {
			System.out.println(movie);
		}

		// Customer
		System.err.println("SEARCH - CUSTOMER");
		String customerSearchString = "nicholas i";
		System.out.println("Searching customers with string " + customerSearchString);
		List<Customer> customers = customerDao.search(customerSearchString);
		for (Customer customer : customers) {
			System.out.println(customer);
		}

		// Theatre - N/A

		// Movie Showing
		System.err.println("SEARCH - MOVIE SHOWING");
		String movieShowingSearchString = "term";
		System.out.println("Searching movie showings with string " + movieShowingSearchString);
		List<MovieShowing> movieShowings = movieShowingDao.search(movieShowingSearchString);
		for (MovieShowing movieShowing : movieShowings) {
			System.out.println(movieShowing);
		}

		String movieShowingSearchString2 = "01 15";
		System.out.println("Searching movie showings with string " + movieShowingSearchString2);
		List<MovieShowing> movieShowings2 = movieShowingDao.search(movieShowingSearchString2);
		for (MovieShowing movieShowing : movieShowings2) {
			System.out.println(movieShowing);
		}

		// Ticket
		System.err.println("SEARCH - TICKET");
		String ticketSearchString1 = "nick";
		System.out.println("Getting all tickets with search string " + ticketSearchString1);
		List<Ticket> tickets = ticketDao.search(ticketSearchString1);
		for (Ticket ticket : tickets) {
			System.out.println(ticket);
		}

		String ticketSearchString2 = "aur";
		System.out.println("Searching tickets with string " + ticketSearchString2);
		List<Ticket> tickets2 = ticketDao.search(ticketSearchString2);
		for (Ticket ticket : tickets2) {
			System.out.println(ticket);
		}

		String ticketSearchString3 = "guardian";
		System.out.println("Searching tickets with string " + ticketSearchString3);
		List<Ticket> tickets3 = ticketDao.search(ticketSearchString3);
		for (Ticket ticket : tickets3) {
			System.out.println(ticket);
		}

		String ticketSearchString4 = "02 21";
		System.out.println("Searching tickets with string " + ticketSearchString4);
		List<Ticket> tickets4 = ticketDao.search(ticketSearchString4);
		for (Ticket ticket : tickets4) {
			System.out.println(ticket);
		}
	}

	private static void advancedQuery() {
		System.err.println("ADVANCED QUERY");
		// Movie
		System.err.println("ADVANCED QUERY - MOVIE");
		String director = "Nick";
		System.out.println("Getting all movies from director " + director + ":");
		List<Movie> moviesOfDirector = movieDao.getMoviesByDirector(director);
		for (Movie movie : moviesOfDirector) {
			System.out.println(movie);
		}

		int duration = 150;
		System.out.println("Getting all movies under " + duration + " minutes long");
		List<Movie> moviesUnderDuration = movieDao.getMoviesUnderDuration(duration);
		for (Movie movie : moviesUnderDuration) {
			System.out.println(movie);
		}

		// Customer
		System.err.println("ADVANCED QUERY - CUSTOMER");
		String firstName = "susan";
		String lastName = "smith";
		System.out.println("Getting customer by first and last name: " + firstName + " " + lastName);
		Customer customer1 = customerDao.findByBothNames(firstName, lastName);
		System.out.println(customer1);

		// Theatre
		System.err.println("ADVANCED QUERY - THEATRE");
		int capacity = 225;
		System.out.println("Getting all theatres with capacity " + capacity + " and above");
		List<Theatre> theatres = theatreDao.findOverCapacity(capacity);
		for (Theatre theatre : theatres) {
			System.out.println(theatre);
		}

		// Movie Showing
		System.err.println("ADVANCED QUERY - MOVIE SHOWING");
		String movieTitle = "Lion king";
		System.out.println("Getting movie showings playing " + movieTitle);
		List<MovieShowing> movieShowings1 = movieShowingDao.getMovieShowingsByMovieTitle(movieTitle);
		for (MovieShowing movieShowing : movieShowings1) {
			System.out.println(movieShowing);
		}

		int theatreNumber = 3;
		Theatre theatre = theatreDao.findByNumber(theatreNumber);
		System.out.println("Getting movie showings in theatre " + theatreNumber);
		List<MovieShowing> movieShowings2 = movieShowingDao.getMovieShowingsByTheatre(theatre);
		for (MovieShowing movieShowing : movieShowings2) {
			System.out.println(movieShowing);
		}

		String movieDirector = "Nick";
		System.out.println("Getting movie showings playing movie by director " + movieDirector);
		List<MovieShowing> movieShowings3 = movieShowingDao.getMovieShowingsByMovieDirector(movieDirector);
		for (MovieShowing movieShowing : movieShowings3) {
			System.out.println(movieShowing);
		}

		LocalDate date = LocalDate.of(2021, 8, 1);
		System.out.println("Getting movie showings playing on date " + date);
		List<MovieShowing> movieShowings4 = movieShowingDao.getMovieShowingsByDate(date);
		for (MovieShowing movieShowing : movieShowings4) {
			System.out.println(movieShowing);
		}

		LocalDateTime dateTime = LocalDateTime.of(2021, 8, 2, 13, 0);
		int maxMinuteDifference = 120;
		System.out.println("Getting movie showings within " + maxMinuteDifference + " minutes of " + dateTime);
		List<MovieShowing> movieShowings5 = movieShowingDao.getMovieShowingsNearDateTime(dateTime, maxMinuteDifference);
		for (MovieShowing movieShowing : movieShowings5) {
			System.out.println(movieShowing);
		}

		// Ticket
		System.err.println("ADVANCED QUERY - TICKET");
		String movieTitle2 = "guardians of the galaxy";
		System.out.println("Getting tickets for " + movieTitle2);
		Movie movie = movieDao.findByTitle(movieTitle2);
		List<Ticket> tickets1 = ticketDao.getTicketsByMovie(movie);
		for (Ticket ticket : tickets1) {
			System.out.println(ticket);
		}

		int movieShowingId = 11;
		System.out.println("Getting tickets for movie showing id " + movieShowingId);
		MovieShowing movieShowing = movieShowingDao.findById(movieShowingId);
		List<Ticket> tickets2 = ticketDao.getTicketsByMovieShowing(movieShowing);
		for (Ticket ticket : tickets2) {
			System.out.println(ticket);
		}

		int theatreNumber2 = 2;
		System.out.println("Getting tickets for theatre " + theatreNumber2);
		Theatre theatre2 = theatreDao.findByNumber(theatreNumber2);
		List<Ticket> tickets3 = ticketDao.getTicketsByTheatre(theatre2);
		for (Ticket ticket : tickets3) {
			System.out.println(ticket);
		}

		String customerEmail = "sam@email.com";
		System.out.println("Getting tickets for customer " + customerEmail);
		Customer customer = customerDao.findByEmail(customerEmail);
		List<Ticket> tickets4 = ticketDao.getTicketsByCustomer(customer);
		for (Ticket ticket : tickets4) {
			System.out.println(ticket);
		}

		LocalDate date2 = LocalDate.of(2021, 8, 1);
		System.out.println("Getting tickets for date " + date2);
		List<Ticket> tickets5 = ticketDao.getTicketsByDate(date2);
		for (Ticket ticket : tickets5) {
			System.out.println(ticket);
		}
	}

}
