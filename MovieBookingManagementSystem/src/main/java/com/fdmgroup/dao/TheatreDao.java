package com.fdmgroup.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.fdmgroup.model.Theatre;

public class TheatreDao {

	private DBConnection connection;

	public TheatreDao() {
		connection = DBConnection.getInstance();
	}

	public void create(Theatre theatre) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		em.persist(theatre);
		em.getTransaction().commit();
		em.close();
	}

	public void remove(Theatre theatre) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		if (em.find(Theatre.class, theatre.getNumber()) != null) {
			em.remove(em.find(Theatre.class, theatre.getNumber()));
			em.getTransaction().commit();
		} else {
			System.err.println("Theatre does not exist, cannot be removed");
		}
		em.close();
	}

	public void update(Theatre theatre) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		em.merge(theatre);
		em.getTransaction().commit();
		em.close();
	}

	public Theatre findByNumber(int number) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Theatre theatre = em.find(Theatre.class, number);
		em.close();
		return theatre;
	}

	public List<Theatre> findOverCapacity(int capacity) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Theatre.findOverCapacity");
		q.setParameter("capacity", capacity);
		List<Theatre> theatres = q.getResultList();
		em.close();
		return theatres;
	}

	public List<Theatre> getTheatres() {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Theatre.findAll");
		em.close();
		return q.getResultList();
	}

}
