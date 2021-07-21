package com.fdmgroup.servlet;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fdmgroup.dao.TicketDao;
import com.fdmgroup.model.Customer;
import com.fdmgroup.model.MovieShowing;
import com.fdmgroup.model.Ticket;

/**
 * Servlet implementation class Tickets
 */
@WebServlet("/Tickets")
public class Tickets extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Tickets() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Customer customer = (Customer) request.getSession().getAttribute("customer");
		TicketDao ticketDao = new TicketDao();
		
		List<Ticket> tickets = ticketDao.getTicketsByCustomer(customer);
		
		Set<MovieShowing> movieShowings = new HashSet<MovieShowing>();
		
		for (Ticket ticket : tickets) {
			movieShowings.add(ticket.getMovieShowing());
		}
		
		request.setAttribute("tickets", tickets);
		request.setAttribute("movieShowings", movieShowings);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("tickets.jsp");
		
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
