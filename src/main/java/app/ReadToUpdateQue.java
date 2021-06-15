package app;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Dao;
import data.Question;

/**
 * Servlet implementation class ReadToUpdateQue
 * Displays question for the admin, possible edition
 */
@WebServlet("/jsp/ReadToUpdateQue")
public class ReadToUpdateQue extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Dao dao;
	public void init() {
		dao=new Dao();
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReadToUpdateQue() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id=request.getParameter("id");
		
		if(request.getParameter("addNew") != null) {
			request.setAttribute("addNew", "1");
			RequestDispatcher rd=request.getRequestDispatcher("/jsp/add-question.jsp");
			rd.forward(request, response);
		}
		else {
			Question q= null;
			if (dao.getConnection()) {
				q=dao.getQuestions(id);
			}
			request.setAttribute("question", q);
			
			RequestDispatcher rd=request.getRequestDispatcher("/jsp/add-question.jsp");
			rd.forward(request, response);
		}
		
	}
}
