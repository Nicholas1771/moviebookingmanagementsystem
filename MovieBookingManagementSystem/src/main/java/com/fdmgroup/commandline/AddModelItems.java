package com.fdmgroup.commandline;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

import com.fdmgroup.dao.CustomerDao;
import com.fdmgroup.dao.MovieDao;
import com.fdmgroup.dao.MovieShowingDao;
import com.fdmgroup.dao.TheatreDao;
import com.fdmgroup.dao.TicketDao;
import com.fdmgroup.model.Customer;
import com.fdmgroup.model.Movie;
import com.fdmgroup.model.MovieShowing;
import com.fdmgroup.model.Theatre;
import com.fdmgroup.model.Ticket;
import com.fdmgroup.model.TicketTypes;

public class AddModelItems {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		MovieDao movieDao = new MovieDao();
		CustomerDao customerDao = new CustomerDao();
		TheatreDao theatreDao = new TheatreDao();
		MovieShowingDao movieShowingDao = new MovieShowingDao();
		TicketDao ticketDao = new TicketDao();

		while (true) {
			System.out.println("Create new item? (m/c/t/ms/tk)/q");
			String input = scanner.nextLine();

			if (input.equals("m")) {
				movieDao.getMovies().forEach((movie) -> System.out.println(movie));
				System.out.println("Creating movie");
				System.out.print("Title: ");
				String title = scanner.nextLine();
				System.out.print("Description: ");
				String description = scanner.nextLine();
				System.out.print("Director: ");
				String director = scanner.nextLine();
				System.out.print("Duration(minutes): ");
				int duration = scanner.nextInt();
				scanner.nextLine();
				movieDao.create(new Movie(title, description, director, duration));
				System.out.println(movieDao.findByTitle(title));
			} else if (input.equals("c")) {
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
				System.out.println(customerDao.findByEmail(email));
			} else if (input.equals("t")) {
				theatreDao.getTheatres().forEach((theatre) -> System.out.println(theatre));
				System.out.println("Creating theatre");
				System.out.print("Number: ");
				int number = scanner.nextInt();
				scanner.nextLine();
				System.out.print("Capacity: ");
				int capacity = scanner.nextInt();
				scanner.nextLine();
				theatreDao.create(new Theatre(number, capacity));
				System.out.println(theatreDao.findByNumber(number));
			} else if (input.equals("ms")) {
				movieShowingDao.getMovieShowings().forEach((movieShowing) -> System.out.println(movieShowing));
				System.out.println("Creating movie showing");
				movieDao.getMovies().forEach((movie) -> System.out.println(movie));
				System.out.print("Movie id: ");
				int movieId = scanner.nextInt();
				scanner.nextLine();
				theatreDao.getTheatres().forEach((theatre) -> System.out.println(theatre));
				System.out.print("Theatre: ");
				int theatreNumber = scanner.nextInt();
				scanner.nextLine();
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
				Movie movie = movieDao.findById(movieId);
				Theatre theatre = theatreDao.findByNumber(theatreNumber);
				LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute);
				Timestamp timestamp = Timestamp.valueOf(dateTime);
				movieShowingDao.create(new MovieShowing(movie, timestamp, theatre));
				System.out.println(movieShowingDao.getMovieShowingsByDateTime(dateTime));
			} else if (input.equals("tk")) {
				ticketDao.getTickets().forEach((ticket) -> System.out.println(ticket));
				System.out.println("Creating ticket");
				movieShowingDao.getMovieShowings().forEach((movieShowing) -> System.out.println(movieShowing));
				System.out.print("Movie showing id: ");
				int movieShowingId = scanner.nextInt();
				scanner.nextLine();
				customerDao.getCustomers().forEach((customer) -> System.out.println(customer));
				System.out.print("Customer email: ");
				String customerEmail = scanner.nextLine();
				System.out.print("Ticket type (s/g/c): ");
				char typeChar = scanner.nextLine().charAt(0);
				MovieShowing movieShowing = movieShowingDao.findById(movieShowingId);
				Customer customer = customerDao.findByEmail(customerEmail);
				TicketTypes type;
				double price;
				switch (typeChar) {
				case 'c':
					price = 8.99;
					type = TicketTypes.CHILD;
					break;
				case 's':
					price = 9.99;
					type = TicketTypes.SENIOR;
					break;
				case 'g':
				default:
					price = 13.99;
					type = TicketTypes.GENERAL;

				}
				Ticket ticket = new Ticket(movieShowing, price, type, customer);
				ticketDao.create(ticket);
			} else if (input.equals("q")) {
				System.out.println("Quitting");
				break;
			} else {
				System.out.println("Invalid selection");
			}
		}
		scanner.close();
	}
}
