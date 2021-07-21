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
import com.fdmgroup.model.Movie;

/**
 * Servlet implementation class Home
 */
@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("homepage.jsp");
		
		MovieDao movieDao = new MovieDao();
		
		List<Movie> movies = new ArrayList<Movie>();
		
		if (request.getParameter("searchString") != null) {
			String searchString = request.getParameter("searchString");			
			List<Movie> searchResults = movieDao.search(searchString);			
			searchResults.forEach((movie) -> {
				if (movie.getMovieShowings() != null) {
					movies.add(movie);
				}
			});
		} else {
			movies.addAll(movieDao.getMoviesWithMovieShowings());
		}
		
		request.setAttribute("moviesPlaying", movies);
		
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
