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

@WebServlet("/jsp/admin-candidate")

/**
 * Servlet responsible for showing all candidates for logged admin
 *
 */

public class adminShowCans extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private Dao dao=null;
       
	@Override
	public void init() 
	{
		dao = new Dao();
	}
	
	
    public adminShowCans() 
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
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/admin-candidate.jsp");
		rd.forward(request, response);
	}

}
