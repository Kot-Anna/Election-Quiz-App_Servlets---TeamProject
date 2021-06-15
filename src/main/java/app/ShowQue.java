package app;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Dao;
import data.Question;

/**
 * Servlet implementation class ShowQue
 * Displaying all existing questions for the admin
 */
@WebServlet("/jsp/admin-questions")
public class ShowQue extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Dao dao=null;
	
	@Override
	public void init() {
		dao = new Dao();
	}
    /**
     * @see HttpServlet#HttpServlet()	
     */
    public ShowQue() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Question> listQuestion = null;
		if (dao.getConnection()) {
			listQuestion = dao.readAllQuestions();
		}
		else {
			System.out.println("No connection to database");
		}
		request.setAttribute("listQuestion", listQuestion);
		
		RequestDispatcher rd=request.getRequestDispatcher("/jsp/admin-questions.jsp");
		rd.forward(request, response);
	}

}
