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
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect("homepage.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		CustomerDao customerDao = new CustomerDao();
		
		Customer customer = customerDao.findByEmail(email);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("Home");
		
		if (!(customer == null)) {
			if (customer.getPassword().equals(password)) {
				request.getSession().setAttribute("customer", customer);
				requestDispatcher.forward(request, response);
			} else {
				response.setStatus(400);
				response.getWriter().append("Wrong password");
			}
		} else {
			response.setStatus(400);
			response.getWriter().append("Account not found");
		}
	}

}
