package com.fdmgroup.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.fdmgroup.model.Customer;
import com.fdmgroup.model.Movie;
import com.fdmgroup.model.MovieShowing;
import com.fdmgroup.model.Theatre;
import com.fdmgroup.model.Ticket;

public class TicketDao {

	private DBConnection connection;

	public TicketDao() {
		connection = DBConnection.getInstance();
	}

	public void create(Ticket ticket) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		em.persist(ticket);
		em.getTransaction().commit();
		em.close();
	}

	public void remove(Ticket ticket) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		if (em.find(Ticket.class, ticket.getId()) != null) {
			em.remove(em.find(Ticket.class, ticket.getId()));
			em.getTransaction().commit();
		} else {
			System.err.println("Ticket does not exist, cannot be removed");
		}
		em.close();
	}

	public void update(Ticket ticket) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		em.merge(ticket);
		em.getTransaction().commit();
		em.close();
	}

	public Ticket findById(int id) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Ticket ticket = em.find(Ticket.class, id);
		em.close();
		return ticket;
	}

	public List<Ticket> getTickets() {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Ticket.findAll");
		em.close();
		return q.getResultList();
	}

	public List<Ticket> getTicketsByMovie(Movie movie) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Ticket.findByMovieId");
		q.setParameter("id", movie.getId());
		em.close();
		return q.getResultList();
	}

	public List<Ticket> getTicketsByMovieShowing(MovieShowing movieShowing) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Ticket.findByMovieShowingId");
		q.setParameter("id", movieShowing.getId());
		em.close();
		return q.getResultList();
	}

	public List<Ticket> getTicketsByTheatre(Theatre theatre) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Ticket.findByTheatreNumber");
		q.setParameter("number", theatre.getNumber());
		em.close();
		return q.getResultList();
	}

	public List<Ticket> getTicketsByCustomer(Customer customer) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Ticket.findByCustomerEmail");
		q.setParameter("email", customer.getEmail());
		em.close();
		return q.getResultList();
	}

	public List<Ticket> getTicketsByDate(LocalDate date) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Ticket.findAll");
		List<Ticket> allTickets = q.getResultList();
		List<Ticket> results = new ArrayList<Ticket>();

		for (Ticket ticket : allTickets) {
			LocalDateTime showingTime = ticket.getMovieShowing().getShowingTime().toLocalDateTime();
			if (showingTime.getDayOfMonth() == date.getDayOfMonth()
					&& showingTime.getMonthValue() == date.getMonthValue() && showingTime.getYear() == date.getYear()) {
				results.add(ticket);
			}
		}

		em.close();
		return results;
	}

	public List<Ticket> search(String searchString) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Ticket.findAll");

		List<Ticket> results = new ArrayList<Ticket>();
		List<Ticket> allTickets = q.getResultList();

		searchString = searchString.toLowerCase();

		for (Ticket ticket : allTickets) {
			String customerFullName = ticket.getCustomer().getFirstName().toLowerCase() + " "
					+ ticket.getCustomer().getLastName().toLowerCase();
			if (ticket.getMovieShowing().getMovie().getTitle().toLowerCase().contains(searchString)) {
				results.add(ticket);
			} else if (ticket.getMovieShowing().getMovie().getDirector().toLowerCase().contains(searchString)) {
				results.add(ticket);
			} else if (ticket.getMovieShowing().getShowingTime().toString().contains(searchString)) {
				results.add(ticket);
			} else if (ticket.getType().toString().toLowerCase().contains(searchString)) {
				results.add(ticket);
			} else if (ticket.getCustomer().getEmail().toLowerCase().contains(searchString)) {
				results.add(ticket);
			} else if (customerFullName.contains(searchString)) {
				results.add(ticket);
			}
		}

		em.close();
		return results;
	}
}
