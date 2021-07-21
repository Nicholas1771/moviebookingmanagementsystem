package com.fdmgroup.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fdmgroup.dao.MovieDao;
import com.fdmgroup.dao.MovieShowingDao;
import com.fdmgroup.model.Movie;
import com.fdmgroup.model.MovieShowing;

/**
 * Servlet implementation class Showings
 */
@WebServlet("/Showings")
public class Showings extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Showings() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("showings.jsp");
		List<MovieShowing> movieShowings = new ArrayList<MovieShowing>();
		MovieShowingDao movieShowingDao = new MovieShowingDao();
		
		if (request.getParameter("id") != null) {
			int movieId = Integer.parseInt(request.getParameter("id"));		
			MovieDao movieDao = new MovieDao();		
			Movie movie = movieDao.findById(movieId);		
			movieShowings.addAll(movieShowingDao.getMovieShowingsByMovie(movie));		
		} else  if (request.getParameter("searchString") != null) {
			String searchString = request.getParameter("searchString");			
			movieShowings.addAll(movieShowingDao.search(searchString));			
		} else {
			movieShowings.addAll(movieShowingDao.getMovieShowings());
		}
		
		request.setAttribute("movieShowings", movieShowings);
		
		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
