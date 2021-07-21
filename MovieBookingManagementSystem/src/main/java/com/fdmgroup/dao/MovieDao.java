package com.fdmgroup.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.fdmgroup.model.Movie;
import com.fdmgroup.model.MovieShowing;

public class MovieDao {

	private DBConnection connection;

	public MovieDao() {
		connection = DBConnection.getInstance();
	}

	public void create(Movie movie) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		em.persist(movie);
		em.getTransaction().commit();
		em.close();
	}

	public void remove(Movie movie) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		if (em.find(Movie.class, movie.getId()) != null) {
			em.remove(em.find(Movie.class, movie.getId()));
			em.getTransaction().commit();
		} else {
			System.err.println("Movie does not exist, cannot be removed");
		}
		em.close();
	}

	public void update(Movie movie) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		em.merge(movie);
		em.getTransaction().commit();
		em.close();
	}

	public Movie findById(int id) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Movie movie = em.find(Movie.class, id);
		em.close();
		return movie;
	}

	public Movie findByTitle(String title) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Movie.findByTitle");
		q.setParameter("title", title);
		List<Movie> movies = q.getResultList();
		em.close();
		if (movies.size() > 0) {
			return movies.get(0);
		} else {
			return null;
		}
	}

	public List<Movie> getMovies() {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Movie.findAll");
		em.close();
		return q.getResultList();
	}
	
	public List<Movie> getMoviesWithMovieShowings() {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Movie.findAll");
		List<Movie> allMovies = q.getResultList();
		List<Movie> results = new ArrayList<Movie>();
		
		for (Movie movie : allMovies) {
			if (movie.getMovieShowings().size() > 0) {
				results.add(movie);
			}
		}
		
		em.close();
		return results;
	}

	public List<Movie> getMoviesByDirector(String director) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Movie.findByDirector");
		q.setParameter("director", director);
		em.close();
		return q.getResultList();
	}

	public List<Movie> getMoviesUnderDuration(int duration) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Movie.findUnderDuration");
		q.setParameter("duration", duration);
		em.close();
		return q.getResultList();
	}

	public List<Movie> getMoviesOverDuration(int duration) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Movie.findOverDuration");
		q.setParameter("duration", duration);
		em.close();
		return q.getResultList();
	}

	public List<MovieShowing> getMovieShowingsFromMovie(Movie movie) {
		return movie.getMovieShowings();
	}

	public List<Movie> search(String searchString) {
		EntityManager em = connection.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Movie.findAll");

		List<Movie> results = new ArrayList<Movie>();
		List<Movie> allMovies = q.getResultList();

		searchString = searchString.toLowerCase();

		for (Movie movie : allMovies) {
			if (movie.getTitle().toLowerCase().contains(searchString)) {
				results.add(movie);
			} else if (movie.getDirector().toLowerCase().contains(searchString)) {
				results.add(movie);
			} else if (movie.getDescription().toLowerCase().contains(searchString)) {
				results.add(movie);
			}
		}

		em.close();
		return results;
	}

}
