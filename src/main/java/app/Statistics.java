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
import data.Question;

/**
 * 
 * Servlet handling the statisctis page
 *
 */
@WebServlet("/Statistics")
public class Statistics extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Dao dao = null;

	@Override
	public void init() {
		dao = new Dao();
	}

	public Statistics() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<Question> q = new ArrayList<>();
		ArrayList<Integer> s = new ArrayList<>();
		ArrayList<String> sta = new ArrayList<>();
		ArrayList<Candidate> c = new ArrayList<>();
		ArrayList<ArrayList<ArrayList<Candidate>>> h = new ArrayList<>();

		if (dao.getConnection()) {
			q = dao.readAllQuestions();
			for (int i = 0; i < q.size(); i++) {
				s.add(q.get(i).getId());
			}
			sta = dao.getStatistics(s);
			for (Candidate i : dao.readAllCandidate()) {
				c.add(dao.getCandidate(i.getId()));
			}
		}

		h = seperator(c, q);


		request.setAttribute("statistics", sta);
		request.setAttribute("cans", h);
		request.setAttribute("questions", q);
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/statistics.jsp");
		rd.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected ArrayList<ArrayList<ArrayList<Candidate>>> seperator(ArrayList<Candidate> c, ArrayList<Question> q) {
		ArrayList<ArrayList<ArrayList<Candidate>>> list = new ArrayList<>();

		for (int i = 0; i < q.size(); i++) {
			ArrayList<ArrayList<Candidate>> wrap = new ArrayList<>();

			ArrayList<Candidate> can1 = new ArrayList<>();
			ArrayList<Candidate> can2 = new ArrayList<>();
			ArrayList<Candidate> can3 = new ArrayList<>();
			ArrayList<Candidate> can4 = new ArrayList<>();
			ArrayList<Candidate> can5 = new ArrayList<>();
			for (int j = 0; j < c.size(); j++) {
				for (int a = 1; a <= 5; a++) {
					if (Integer.parseInt(c.get(j).getAnswers().get(i).getAnswer()) == a) {
						switch (a) {
						case 1:
							can1.add(c.get(j));
							break;
						case 2:
							can2.add(c.get(j));
							break;
						case 3:
							can3.add(c.get(j));
							break;
						case 4:
							can4.add(c.get(j));
							break;
						case 5:
							can5.add(c.get(j));
							break;
						}
					}
				}

			}
			wrap.add(can1);
			wrap.add(can2);
			wrap.add(can3);
			wrap.add(can4);
			wrap.add(can5);
			list.add(wrap);
		}
		return list;
	}
}
