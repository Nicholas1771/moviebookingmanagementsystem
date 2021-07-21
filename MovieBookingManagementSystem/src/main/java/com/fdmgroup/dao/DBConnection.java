package com.fdmgroup.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBConnection {

	private static final String JPA_UNIT_NAME = "ItemPU";
	private static DBConnection connection = null;
	private EntityManagerFactory emf;

	private DBConnection() {
		init();
	}

	private void init() {
		if (emf == null || !emf.isOpen()) {
			emf = Persistence.createEntityManagerFactory(JPA_UNIT_NAME);
		}
	}

	public static DBConnection getInstance() {
		if (connection == null) {
			connection = new DBConnection();
		}
		return connection;
	}

	public EntityManager getEntityManager() {
		init();
		return emf.createEntityManager();
	}

	public void close() {
		emf.close();
	}

}
