package com.fdmgroup.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fdmgroup.dao.CustomerDao;
import com.fdmgroup.model.Customer;

/**
 * Servlet implementation class SignUp
 */
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect("signup.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		
		CustomerDao customerDao = new CustomerDao();
		
		System.out.println("signing up");
		
		if (password.equals(confirmPassword)) {
			if (customerDao.findByEmail(email) == null) {
				if (password.length() >= 8) {
					customerDao.create(new Customer(email, firstName, lastName, password));
					response.getWriter().append("Successfully signed up!");
				} else {
					response.setStatus(400);
					response.getWriter().append("Password must be at least 8 characters");
				}
			} else {
				response.setStatus(400);
				response.getWriter().append("User exists with that email");		
			}
		} else {
			response.setStatus(400);
			response.getWriter().append("Passwords do not match");
		}
	}
}
