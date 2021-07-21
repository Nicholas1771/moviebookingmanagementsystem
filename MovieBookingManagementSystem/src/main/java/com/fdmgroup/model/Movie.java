package com.fdmgroup.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
@NamedQueries({ @NamedQuery(name = "Movie.findAll", query = "SELECT m FROM Movie m"),
		@NamedQuery(name = "Movie.findById", query = "SELECT m FROM Movie m WHERE m.id = :id"),
		@NamedQuery(name = "Movie.findByTitle", query = "SELECT m FROM Movie m WHERE LOWER(m.title) = LOWER(:title)"),
		@NamedQuery(name = "Movie.findByDirector", query = "SELECT m FROM Movie m WHERE LOWER(m.director) = LOWER(:director)"),
		@NamedQuery(name = "Movie.findUnderDuration", query = "SELECT m FROM Movie m WHERE m.duration <= :duration"),
		@NamedQuery(name = "Movie.findOverDuration", query = "SELECT m FROM Movie m WHERE m.duration >= :duration") })
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_sequence")
	@SequenceGenerator(name = "movie_sequence", sequenceName = "movie_seq", allocationSize = 1)
	private int id;
	private String title;
	private String description;
	private String director;
	private int duration;

	@OneToMany(mappedBy = "movie")
	private List<MovieShowing> movieShowings;

	public Movie() {
	}

	public Movie(int id, String title, String description, String director, int duration) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.director = director;
		this.duration = duration;
	}

	public Movie(String title, String description, String director, int duration) {
		this.title = title;
		this.description = description;
		this.director = director;
		this.duration = duration;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<MovieShowing> getMovieShowings() {
		return movieShowings;
	}

	public void setMovieShowings(List<MovieShowing> movieShowings) {
		this.movieShowings = movieShowings;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", director=" + director + ", duration=" + duration + "]";
	}

}
