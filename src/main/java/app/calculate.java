package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Dao;
import data.Answer;
import data.Candidate;
import data.Question;

/**
 * 
 * Servlet responsible for calculating a match between voters and candidates answers
 * Sends result to top 3 page which displays result to a voter
 */
@WebServlet("/calculate")
public class calculate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Dao dao = null;

	@Override
	// connection
	public void init() {
		dao = new Dao();
	}

	public calculate() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Users answers collected from the js (questions.js) seprated by a % sign
		String[] str = new String[request.getParameter("answersArr").length()];
		// splits the answers using the % sign and creates an array storing them
		str = request.getParameter("answersArr").split("%");

		// declaring arrays

		// array storing all candidates as objects (it has all the candidates info
		// including picture, collection of answers etc
		ArrayList<Candidate> c = new ArrayList<>();

		// arraylist of arraylists storing answer objects per each candidate
		ArrayList<ArrayList<Answer>> list = new ArrayList<>();

		// array list storing candidate id, calculated percent, candidate picture,
		// first/last name, party name
		ArrayList<Answer> candi = new ArrayList<>();

		if (dao.getConnection()) {
			// adds candidate objects into a c array
			for (Candidate i : dao.readAllCandidate()) {
				c.add(dao.getCandidate(i.getId()));
			}
		}

		// passing parameters: candidate array and the voter answer into the getPer
		// method to get the % match
		list = getPer(c, str);

		// iterating through a "list" arraylist (as many times as there's candidates)
		for (int i = 0; i < list.size(); i++) {
			// for each candidate we store in "nums" array list the % value of match with
			// voter's aswers for each question
			ArrayList<Integer> nums = new ArrayList<>();

			// itering through "list" array for each arraylist item contained within
			// [a = [[123] [123]] - 3 times (for each candidate we iterate as many times as
			// there are answers)
			for (int j = 0; j < list.get(i).size(); j++) {
				// for each candidate we store % value of match
				nums.add(Integer.parseInt(list.get(i).get(j).getAnswer()));
			}
			
			String can_id = list.get(i).get(1).getCan_id();
			String que_id = list.get(i).get(1).getQuestion_id();
			String img = list.get(i).get(1).getImg();
			String party = list.get(i).get(1).getParty();
			String fname = list.get(i).get(1).getFname();
			String lname = list.get(i).get(1).getLname();
			String answer = String.valueOf(getResult(nums));
			// to each candidate we ad an answer object with candidate id and final average
			// of a % match with a voter
			candi.add(new Answer(can_id, que_id, answer, img, party, fname, lname ));
		}

	
		
		/*
		 * for (Answer x : customSort(candi)) { System.out.println("Name : " +
		 * x.getFname() + " " + x.getLname() + " / " + "Percentage : " + x.getAnswer() +
		 * "%"); }
		 */
		request.setAttribute("top", customSort(candi).subList(0, 3));
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/top-3.jsp");
		rd.forward(request, response);
	}

	// function returns an array of arrays that stores list of answers per each
	// candidate
	// returns a % value of a match between voter's answer and each candidate's
	// answer

	// in the function we are passing an arraylist of candidate objects which
	// include all the candidate's answers
	// and 2nd parameter is an array of the voter answers
	protected ArrayList<ArrayList<Answer>> getPer(ArrayList<Candidate> x, String[] y) {
		
		// declaring a new array of arrays of answer objects
		ArrayList<ArrayList<Answer>> list = new ArrayList<>();
		int count = 0;
		
		// looping as many times as there is registered candidates
		// per array stores calculated % for each answer of each candidate
		for (int i = 0; i < x.size(); i++) {
			ArrayList<Answer> per = new ArrayList<>();
			
			// looping through the amount of answers per each candidate
			// in short we are looping as many times as there are questions
			for (int j = 0; j < x.get(i).getAnswers().size(); j++) {
				
				//creating a new answer object per each iteration and adding it to the "per" arraylist
				// Answer object parameters: candidate id, question id and a % of a match between one specific answer
				// and voter answer
				String can_id = x.get(i).getAnswers().get(j).getCan_id();
				String que_id = x.get(i).getAnswers().get(j).getQuestion_id();
				String percent = String.valueOf(calculatePer(x.get(i).getAnswers().get(j).getAnswer(), y[j]));
				String img = x.get(i).getProfile_pic();
				String party = x.get(i).getPolitical_party();
				String fname = x.get(i).getFname();
				String lname = x.get(i).getLname();
				
				per.add(new Answer(can_id, que_id, percent, img, party, fname, lname));

			}
			// arraylist of arraylists storing answer objects per each candidate
			// for each candidate we are creating a "per" array list and then store all of them for all candidates
			// in the "list" arraylist
			list.add(per);
		}
		return list;
	}

	// method that calculates the average of a percent values of a match
	protected int getResult(ArrayList<Integer> x) {
		int sum = 0;

		for (int i : x) {
			sum += i;
		}

		// rounding down doubles to integers with math.floor (5.2 = 5)
		return (int) Math.floor(sum / x.size());
	}

	// calculating point system of match for each voter answer vs. candidate answer
	protected int calculatePer(String a, String s) {
		int num = 0;
		int i = Integer.parseInt(a);
		int j = Integer.parseInt(s);

		if (i == j) {
			num = 100;
		} else {
			// based on difference between answers we are giving the points
			int val = Math.max(i, j) - Math.min(i, j);

			if (val == 1) {
				num = 75;
			} else if (val == 2) {
				num = 50;
			} else if (val == 3) {
				num = 25;
			} else if (val == 4) {
				num = 0;
			}
		}

		return num;
	}
	
	// sorting the percentage average values in order
	public ArrayList<Answer> customSort(ArrayList<Answer> a) {
		boolean sorted = false;
		Answer temp;
		while (!sorted) {
			sorted = true;
			for (int i = 0; i < a.size() - 1; i++) {
				if (Integer.parseInt(a.get(i).getAnswer()) < Integer.parseInt(a.get(i + 1).getAnswer())) {
					temp = a.get(i);
					a.set(i, a.get(i + 1));
					a.set(i + 1, temp);
					sorted = false;
				}
			}
		}
		return a;
	}

}
