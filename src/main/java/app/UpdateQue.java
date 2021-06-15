package app;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.Dao;
import data.Question;

/**
 * 
 * Servlet allowing logged in admin to change a question text
 * or add a new question
 *
 */
@WebServlet("/jsp/updateque")
public class UpdateQue extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Dao dao = null;

	@Override
	public void init() {
		dao = new Dao();
	}

	public UpdateQue() {
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

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
			String text = request.getParameter("question");

			if (request.getParameter("addNew") != null) {
				System.out.println("new question");
				Question q = new Question();
				q.setText(text);

				if (dao.getConnection()) {
					if (dao.addQuestion(q)) {
						String qid = String.valueOf(dao.getQuestionByRef(q.getRef_num()));
						response.sendRedirect("/randomAnswers?qid=" + qid);
					}
				}
			} else {
				Question q = new Question(id, text);

				if (dao.getConnection()) {
					if (dao.updateQuestion(q)) {
						response.sendRedirect("/jsp/admin-questions");
					}
				}
			}
		}
		
		

	}
}
