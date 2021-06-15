package app;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Dao;
import data.Answer;

/**
 * Servlet implementation class ChangeAnswer
 */
@WebServlet("/jsp/ChangeAnswer")
public class ChangeAnswer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Dao dao = null;
	
	public void init() 
	{
		dao = new Dao();
	}

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeAnswer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		/*String id = request.getParameter("id");
		
		int size = Integer.parseInt(request.getParameter("size"));
		ArrayList<Answer> answer = new ArrayList<Answer>();
		
		if (dao.getConnection()) 
		{
			for (int i=0; i<size; i++) {
				Answer ans = new Answer(id, request.getParameter("questionID".concat(String.valueOf(i))), request.getParameter(String.valueOf(i)));
				answer.add(ans);
			}
		}*/
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		System.out.println(request.getParameter("size"));
		System.out.println(request.getParameter("id"));
		
		int size = Integer.parseInt(request.getParameter("size"));
		ArrayList<Answer> answer = new ArrayList<Answer>();
		
		if (dao.getConnection()) {
			for (int i=0; i<size; i++) {
				Answer ans = new Answer(request.getParameter("id"), request.getParameter("questionID".concat(String.valueOf(i))), request.getParameter(String.valueOf(i)));
				answer.add(ans);
			}
			dao.updateAnswerCandidate(answer);
			response.sendRedirect("admin-candidate");
		}
	}

}
