package com.fdmgroup.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fdmgroup.dao.MovieShowingDao;
import com.fdmgroup.dao.TicketDao;
import com.fdmgroup.model.Customer;
import com.fdmgroup.model.MovieShowing;
import com.fdmgroup.model.Ticket;
import com.fdmgroup.model.TicketTypes;

/**
 * Servlet implementation class Checkout
 */
@WebServlet("/Checkout")
public class Checkout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Checkout() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MovieShowingDao movieShowingDao = new MovieShowingDao();

		int movieShowingId = Integer.parseInt(request.getParameter("id"));
		MovieShowing movieShowing = movieShowingDao.findById(movieShowingId);

		request.setAttribute("movieShowing", movieShowing);

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("checkout.jsp");

		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int general = Integer.parseInt(request.getParameter("general"));
		int child = Integer.parseInt(request.getParameter("child"));
		int senior = Integer.parseInt(request.getParameter("senior"));

		if (general >= 0 && child >= 0 && senior >= 0) {
			
			Customer customer = (Customer) request.getSession().getAttribute("customer");
			
			int movieShowingId = Integer.parseInt(request.getParameter("id"));
			
			MovieShowingDao movieShowingDao = new MovieShowingDao();
			MovieShowing movieShowing = movieShowingDao.findById(movieShowingId);
			TicketDao ticketDao = new TicketDao();

			for (int i = 0; i < general; i++) {
				ticketDao.create(new Ticket(movieShowing, 13.99, TicketTypes.GENERAL, customer));
			}
			
			for (int i = 0; i < child; i++) {
				ticketDao.create(new Ticket(movieShowing, 8.99, TicketTypes.CHILD, customer));
			}

			for (int i = 0; i < senior; i++) {
				ticketDao.create(new Ticket(movieShowing, 9.99, TicketTypes.SENIOR, customer));
			}
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("Tickets");
			
			requestDispatcher.forward(request, response);
			
		} else {
			doGet(request, response);
		}
	}

}
