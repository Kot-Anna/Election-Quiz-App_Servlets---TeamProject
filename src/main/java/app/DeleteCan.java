package app;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.Dao;
import data.Candidate;
import data.Question;

/**
 * Servlet implementation class DeleteCan
 * Responsible for deleting candidate
 */
@WebServlet("/deleteCan")
public class DeleteCan extends HttpServlet {
	private Dao dao;

	public void init() {
		dao = new Dao();
	}

	private static final long serialVersionUID = 1L;

	public DeleteCan() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		boolean isLoggedIn = false;
		if (session == null) {
		} else {
			if (session.getAttribute("isLoggedIn") == null) {

			} else {
				isLoggedIn = (boolean) session.getAttribute("isLoggedIn");
			}
		}

		if (!isLoggedIn) {
			response.sendRedirect("/index.jsp");
		}

		else {
			String id = request.getParameter("id");

			if (dao.getConnection()) {
				if (dao.deleteCandidate(id)) {
					response.sendRedirect("/jsp/admin-candidate");
				}
			}
		}
	}

}
