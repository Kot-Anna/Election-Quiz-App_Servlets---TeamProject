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
import data.Answer;
import data.Candidate;
import data.RandomAnswer;

/**
 * 
 * Adding random answers to newly created question for existing candidates
 *
 */
@WebServlet("/randomAnswers")
public class randomAnswers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Dao dao = null;

	@Override
	public void init() {
		dao = new Dao();
	}

	public randomAnswers() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("qid");
		System.out.println("id is=" + id);
		ArrayList<Candidate> candidates = new ArrayList<>();
		ArrayList<RandomAnswer> randoms = new ArrayList<>();
		ArrayList<Answer> answers = new ArrayList<>();

		Question q = null;

		if (dao.getConnection()) {
			if (!dao.checkQuestion(id)) {
				q = dao.getQuestions(id);
				candidates = dao.readAllCandidate();
				randoms = assignAnswers(candidates, id);
				dao.addAnswersForNewQuestion(randoms);
			} else {
				q = dao.getQuestions(id);
				candidates = dao.readAllCandidate();
				answers = dao.getAnsersByQuestionId(id);
				randoms = assignAnswersElse(candidates, answers, id);
			}

		}

		request.setAttribute("question", q);
		request.setAttribute("cans", randoms);
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/askToCandidates.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	protected ArrayList<RandomAnswer> assignAnswers(ArrayList<Candidate> c, String qid) {
		ArrayList<RandomAnswer> answers = new ArrayList<>();

		for (Candidate can : c) {
			String randomAnswer = String.valueOf((int) Math.floor(Math.random() * 5 + 1));
			answers.add(new RandomAnswer(can.getId(), qid, randomAnswer, can.getProfile_pic()));
		}

		return answers;
	}

	protected ArrayList<RandomAnswer> assignAnswersElse(ArrayList<Candidate> c, ArrayList<Answer> a, String qid) {
		ArrayList<RandomAnswer> answers = new ArrayList<>();

		for (int i = 0; i < c.size(); i++) {
			String randomAnswer = a.get(i).getAnswer();
			answers.add(new RandomAnswer(c.get(i).getId(), qid, randomAnswer, c.get(i).getProfile_pic()));

		}

		return answers;
	}

}
