package com.fdmgroup.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "movie_showing")
@NamedQueries({ @NamedQuery(name = "MovieShowing.findAll", query = "SELECT m FROM MovieShowing m"),
		@NamedQuery(name = "MovieShowing.findById", query = "SELECT m FROM MovieShowing m WHERE m.id = :id"),
		@NamedQuery(name = "MovieShowing.findByMovieTitle", query = "SELECT m FROM MovieShowing m WHERE LOWER(m.movie.title) = LOWER(:title)"),
		@NamedQuery(name = "MovieShowing.findByMovieId", query = "SELECT m FROM MovieShowing m WHERE m.movie.id = :id"),
		@NamedQuery(name = "MovieShowing.findByMovieDirector", query = "SELECT m FROM MovieShowing m WHERE LOWER(m.movie.director) = LOWER(:director)"),
		@NamedQuery(name = "MovieShowing.findByTheatreNumber", query = "SELECT m FROM MovieShowing m WHERE m.theatre.number = :number") })
public class MovieShowing {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_showing_sequence")
	@SequenceGenerator(name = "movie_showing_sequence", sequenceName = "movie_showing_seq", allocationSize = 1)
	private int id;
	@Column(name = "showing_time")
	private Timestamp showingTime;
	@ManyToOne
	@JoinColumn(name = "movie_id")
	private Movie movie;
	@ManyToOne
	@JoinColumn(name = "theatre_id")
	private Theatre theatre;

	@OneToMany(mappedBy = "movieShowing")
	private List<Ticket> tickets;

	public MovieShowing() {
	}

	public MovieShowing(Movie movie, Timestamp showingTime, Theatre theatre) {
		this.movie = movie;
		this.showingTime = showingTime;
		this.theatre = theatre;
	}

	public MovieShowing(int id, Movie movie, Timestamp showingTime, Theatre theatre) {
		this.id = id;
		this.movie = movie;
		this.showingTime = showingTime;
		this.theatre = theatre;
	}

	public void removeTicket(Ticket ticket) {
		tickets.remove(ticket);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getShowingTime() {
		return showingTime;
	}

	public void setShowingTime(Timestamp showingTime) {
		this.showingTime = showingTime;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Theatre getTheatre() {
		return theatre;
	}

	public void setTheatre(Theatre theatre) {
		this.theatre = theatre;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	@Override
	public String toString() {
		return "MovieShowing [id=" + id + ", showingTime=" + showingTime + ", movie=" + movie.getTitle() + ", theatre="
				+ theatre.getNumber() + "]";
	}

}
