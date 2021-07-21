package com.fdmgroup.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.fdmgroup.model.Movie;
import com.fdmgroup.model.MovieShowing;
import com.fdmgroup.model.Theatre;
import com.fdmgroup.model.Ticket;

public class MovieShowingDao {

	private DBConnection connection;

	public MovieShowingDao() {
		connection = DBConnection.getInstance();
	}

	public void create(MovieShowing movieShowing) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		em.persist(movieShowing);
		em.getTransaction().commit();
		em.close();
	}

	public void remove(MovieShowing movieShowing) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		if (em.find(MovieShowing.class, movieShowing.getId()) != null) {
			em.remove(em.find(MovieShowing.class, movieShowing.getId()));
			em.getTransaction().commit();
		} else {
			System.err.println("Movie Showing with id does not exist, cannot be removed");
		}
		em.close();
	}

	public void update(MovieShowing movieShowing) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		em.merge(movieShowing);
		em.getTransaction().commit();
		em.close();
	}
	
	public MovieShowing findById(int id) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		MovieShowing movieShowing = em.find(MovieShowing.class, id);
		em.close();
		return movieShowing;
	}

	public List<MovieShowing> getMovieShowings() {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("MovieShowing.findAll");
		em.close();
		return q.getResultList();
	}

	public List<MovieShowing> getMovieShowingsByMovieDirector(String director) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("MovieShowing.findByMovieDirector");
		q.setParameter("director", director);
		em.close();
		return q.getResultList();
	}

	public List<MovieShowing> getMovieShowingsByMovieTitle(String title) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("MovieShowing.findByMovieTitle");
		q.setParameter("title", title);
		em.close();
		return q.getResultList();
	}

	public List<MovieShowing> getMovieShowingsByMovie(Movie movie) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("MovieShowing.findByMovieId");
		q.setParameter("id", movie.getId());
		em.close();
		return q.getResultList();
	}

	public List<MovieShowing> getMovieShowingsByTheatre(Theatre theatre) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("MovieShowing.findByTheatreNumber");
		q.setParameter("number", theatre.getNumber());
		em.close();
		return q.getResultList();
	}

	public List<MovieShowing> getMovieShowingsByDate(LocalDate date) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("MovieShowing.findAll");
		List<MovieShowing> allMovieShowings = q.getResultList();
		List<MovieShowing> results = new ArrayList<MovieShowing>();

		for (MovieShowing movieShowing : allMovieShowings) {
			LocalDateTime showingTime = movieShowing.getShowingTime().toLocalDateTime();
			if (showingTime.getDayOfMonth() == date.getDayOfMonth()
					&& showingTime.getMonthValue() == date.getMonthValue() && showingTime.getYear() == date.getYear()) {
				results.add(movieShowing);
			}
		}

		em.close();
		return results;
	}
	
	public List<MovieShowing> getMovieShowingsByDateTime(LocalDateTime dateTime) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("MovieShowing.findAll");
		List<MovieShowing> allMovieShowings = q.getResultList();
		List<MovieShowing> results = new ArrayList<MovieShowing>();

		for (MovieShowing movieShowing : allMovieShowings) {
			LocalDateTime showingTime = movieShowing.getShowingTime().toLocalDateTime();
			if (dateTime.equals(showingTime)) {
				results.add(movieShowing);
			}
		}

		em.close();
		return results;
	}

	public List<MovieShowing> getMovieShowingsNearDateTime(LocalDateTime dateTime, int maxMinuteDifference) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("MovieShowing.findAll");
		List<MovieShowing> allMovieShowings = q.getResultList();
		List<MovieShowing> results = new ArrayList<MovieShowing>();

		for (MovieShowing movieShowing : allMovieShowings) {
			LocalDateTime showingTime = movieShowing.getShowingTime().toLocalDateTime();
			int minuteDifference = Math.abs((showingTime.getHour() * 60 + showingTime.getMinute())
					- (dateTime.getHour() * 60 + showingTime.getMinute()));
			if (showingTime.getDayOfMonth() == dateTime.getDayOfMonth()
					&& showingTime.getMonthValue() == dateTime.getMonthValue()
					&& showingTime.getYear() == dateTime.getYear() && minuteDifference <= maxMinuteDifference) {
				results.add(movieShowing);
			}
		}

		em.close();
		return results;
	}

	public List<Ticket> getTicketsByMovieShowing(MovieShowing movieShowing) {
		return movieShowing.getTickets();
	}

	public List<MovieShowing> search(String searchString) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("MovieShowing.findAll");

		List<MovieShowing> results = new ArrayList<MovieShowing>();
		List<MovieShowing> allMovieShowings = q.getResultList();

		searchString = searchString.toLowerCase();

		for (MovieShowing movieShowing : allMovieShowings) {
			if (movieShowing.getMovie().getTitle().toLowerCase().contains(searchString)) {
				results.add(movieShowing);
			} else if (movieShowing.getMovie().getDirector().toLowerCase().contains(searchString)) {
				results.add(movieShowing);
			} else if (movieShowing.getMovie().getDescription().toLowerCase().contains(searchString)) {
				results.add(movieShowing);
			} else if (movieShowing.getShowingTime().toString().contains(searchString)) {
				results.add(movieShowing);
			}
		}

		em.close();
		return results;
	}

}
