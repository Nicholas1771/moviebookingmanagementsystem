package com.fdmgroup.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fdmgroup.dao.CustomerDao;
import com.fdmgroup.model.Customer;

/**
 * Servlet implementation class Change
 */
@WebServlet("/Change")
public class Change extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Change() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Customer customer = (Customer) request.getSession().getAttribute("customer");
		
		CustomerDao customerDao = new CustomerDao();
		
		System.out.println("updating customer");
		
		boolean deactivation = false;
		
		if (request.getParameter("firstName") != null) {
			String firstName = (String) request.getParameter("firstName");
			customer.setFirstName(firstName);
		} else if (request.getParameter("lastName") != null) {
			customer.setLastName((String) request.getParameter("lastName"));
		} else if (request.getParameter("password") != null) {
			String password = (String) request.getParameter("password");
			if (password.length() >= 8) {
				customer.setPassword(password);
			} else {
				response.setStatus(400);
				response.getWriter().append("Password must be at least 8 characters");
			}
		} else if (request.getParameter("email") != null) {
			//Deactivating
			deactivation = true;
			if (customer.getEmail().equals(request.getParameter("email"))) {
				customerDao.remove(customer);
				request.getSession().setAttribute("customer", null);

				RequestDispatcher requestDispatcher = request.getRequestDispatcher("Home");
				requestDispatcher.forward(request, response);
			} else {
				response.setStatus(400);
				response.getWriter().append("Incorrect Email");
			}
		}
		
		if (response.getStatus() != 400 && !deactivation) {
			String email = customer.getEmail();
			customerDao.update(customer);
			Customer newCustomer = customerDao.findByEmail(email);
			request.getSession().setAttribute("customer", newCustomer);
			response.getWriter().append("Successfully updated");
		}		
	}
}
