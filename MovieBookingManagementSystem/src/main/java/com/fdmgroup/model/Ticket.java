package com.fdmgroup.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "movie_ticket")
@NamedQueries({ @NamedQuery(name = "Ticket.findAll", query = "SELECT t FROM Ticket t"),
		@NamedQuery(name = "Ticket.findById", query = "SELECT t FROM Ticket t WHERE t.id = :id"),
		@NamedQuery(name = "Ticket.findByMovieShowingId", query = "SELECT t FROM Ticket t WHERE t.movieShowing.id = :id"),
		@NamedQuery(name = "Ticket.findByMovieId", query = "SELECT t FROM Ticket t WHERE t.movieShowing.movie.id = :id"),
		@NamedQuery(name = "Ticket.findByCustomerEmail", query = "SELECT t FROM Ticket t WHERE LOWER(t.customer.email) = LOWER(:email)"),
		@NamedQuery(name = "Ticket.findByTheatreNumber", query = "SELECT t FROM Ticket t WHERE t.movieShowing.theatre.number = :number") })
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_sequence")
	@SequenceGenerator(name = "ticket_sequence", sequenceName = "movie_ticket_seq", allocationSize = 1)
	private int id;
	private double price;
	@Enumerated(EnumType.STRING)
	private TicketTypes type;
	@ManyToOne
	@JoinColumn(name = "customer_email")
	private Customer customer;
	@ManyToOne
	@JoinColumn(name = "movie_showing_id")
	private MovieShowing movieShowing;

	public Ticket() {
	}

	public Ticket(MovieShowing movieShowing, double price, TicketTypes type, Customer customer) {
		this.movieShowing = movieShowing;
		this.price = price;
		this.type = type;
		this.customer = customer;
	}

	public Ticket(int id, MovieShowing movieShowing, double price, TicketTypes type, Customer customer) {
		this.id = id;
		this.movieShowing = movieShowing;
		this.price = price;
		this.type = type;
		this.customer = customer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public TicketTypes getType() {
		return type;
	}

	public void setType(TicketTypes type) {
		this.type = type;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public MovieShowing getMovieShowing() {
		return movieShowing;
	}

	public void setMovieShowing(MovieShowing movieShowing) {
		this.movieShowing = movieShowing;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", price=" + price + ", type=" + type + ", customer=" + customer.getFirstName()
				+ " " + customer.getLastName() + ", theatre=" + movieShowing.getTheatre().getNumber() + ", movie="
				+ movieShowing.getMovie().getTitle() + ", showing time=" + movieShowing.getShowingTime() + "]";
	}

}
