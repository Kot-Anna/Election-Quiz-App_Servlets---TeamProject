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
import data.Candidate;

/**
 * 
 * Servlet responsible for displaying a list of all candidates for voter
 *
 */
@WebServlet("/jsp/candidates")
public class ShowCan extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private Dao dao=null;
       
	@Override
	public void init() 
	{
		dao = new Dao();
	}
	
	
    public ShowCan() 
    {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ArrayList<Candidate> list = null;
		
		if(dao.getConnection()) 
		{
			list = dao.readAllCandidate();
		} else 
		{
			System.out.println("No connection to database");
		}
		request.setAttribute("candidatelist", list);
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/candidates.jsp");
		rd.forward(request, response);
	}

}
