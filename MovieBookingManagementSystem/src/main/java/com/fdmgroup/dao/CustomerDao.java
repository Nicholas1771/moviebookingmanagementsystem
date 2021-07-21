package com.fdmgroup.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.fdmgroup.model.Customer;
import com.fdmgroup.model.Ticket;

public class CustomerDao {

	private DBConnection connection;

	public CustomerDao() {
		connection = DBConnection.getInstance();
	}

	public void create(Customer customer) {
		if (findByEmail(customer.getEmail()) == null) {
			EntityManager em = connection.getEntityManager();
			em.getTransaction().begin();
			em.persist(customer);
			em.getTransaction().commit();
			em.close();
		} else {
			System.err.println("Customer already exists");
		}
	}

	public void remove(Customer customer) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		if (em.find(Customer.class, customer.getEmail()) != null) {
			em.remove(em.find(Customer.class, customer.getEmail()));
			em.getTransaction().commit();
		} else {
			System.err.println("Customer does not exist, cannot be removed");
		}
		em.close();
	}

	public void update(Customer customer) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		em.merge(customer);
		em.getTransaction().commit();
		em.close();
	}

	public Customer findByEmail(String email) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Customer customer = em.find(Customer.class, email);
		em.close();
		return customer;
	}

	public Customer findByBothNames(String firstName, String lastName) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Customer.findByBothNames");
		q.setParameter("firstName", firstName);
		q.setParameter("lastName", lastName);
		List<Customer> customers = q.getResultList();
		em.close();
		if (customers.size() > 0) {
			return customers.get(0);
		} else {
			return null;
		}
	}

	public List<Customer> getCustomers() {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Customer.findAll");
		em.close();
		return q.getResultList();
	}

	public List<Ticket> getTicketsByCustomer(Customer customer) {
		return customer.getTickets();
	}

	public List<Customer> search(String searchString) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Customer.findAll");

		List<Customer> results = new ArrayList<Customer>();
		List<Customer> allCustomers = q.getResultList();

		searchString = searchString.toLowerCase();

		for (Customer customer : allCustomers) {
			String customerFullName = customer.getFirstName().toLowerCase() + " "
					+ customer.getLastName().toLowerCase();
			if (customer.getEmail().toLowerCase().contains(searchString)) {
				results.add(customer);
			} else if (customerFullName.contains(searchString)) {
				results.add(customer);
			}
		}

		em.close();
		return results;
	}

}
