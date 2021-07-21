package com.fdmgroup.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({ @NamedQuery(name = "Theatre.findAll", query = "SELECT t FROM Theatre t"),
		@NamedQuery(name = "Theatre.findByNumber", query = "SELECT t FROM Theatre t WHERE t.number = :number"),
		@NamedQuery(name = "Theatre.findOverCapacity", query = "SELECT t FROM Theatre t WHERE t.capacity >= :capacity") })
public class Theatre {

	@Id
	@Column(name = "id")
	private int number;
	private int capacity;

	@OneToMany(mappedBy = "theatre")
	private List<MovieShowing> movieShowings;

	public Theatre() {
	}

	public Theatre(int number, int capacity) {
		this.capacity = capacity;
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public List<MovieShowing> getMovieShowings() {
		return movieShowings;
	}

	public void setMovieShowings(List<MovieShowing> movieShowings) {
		this.movieShowings = movieShowings;
	}

	@Override
	public String toString() {
		return "Theatre [number=" + number + ", capacity=" + capacity + "]";
	}

}
