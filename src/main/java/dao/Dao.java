package dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import data.Admin;
import data.Answer;
import data.Candidate;
import data.Question;
import data.RandomAnswer;

import java.sql.Connection;
/**
 * 
 * Dao class
 *
 */
public class Dao {
	private String url;
	private String user;
	private String pass;
	private Connection conn;
	private String uploadPath;

/**
 * Dao constructor
 * To be changed if info is different	
 */
	public Dao() {
		this.url = "jdbc:mysql://localhost:3306/vaalikone";
		this.user = "root";
		this.pass = "Password1";
		this.uploadPath = "C:\\Users\\kota\\git\\vaalikone\\src\\main\\webapp\\img\\";
	}

//	public Dao(String url, String user, String pass) {
//		this.url = url;
//		this.user = user;
//		this.pass = pass;
//	}

/**
 * Getter for the upload path
 * @return
 */
	public String getUploadPath() {
		return uploadPath;
	}

/**
 * Establishing Connection method
 * @return
 */
	public boolean getConnection() {
		try {
			if (conn == null || conn.isClosed()) {
				try {
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					System.out.println("Connected");
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
					throw new SQLException(e);
				}
				conn = DriverManager.getConnection(url, user, pass);
			}

			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

/**
 * Method returning all candidate objects stored in the database
 * @return
 */
	public ArrayList<Candidate> readAllCandidate() {
		ArrayList<Candidate> list = new ArrayList<>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet RS = stmt.executeQuery("select * from candidate");
			while (RS.next()) {
				Candidate c = new Candidate();
				c.setId(RS.getString("id"));
				c.setFname(RS.getString("fname"));
				c.setLname(RS.getString("lname"));
				c.setCity(RS.getString("city"));
				c.setAge(RS.getString("age"));
				c.setProfession(RS.getString("profession"));
				c.setPolitical_party(RS.getString("political_party"));
				c.setWhy_candidate(RS.getString("why_candidate"));
				c.setAbout(RS.getString("about"));
				c.setProfile_pic(RS.getString("profile_pic"));
				if (checkCanAnswer(c.getId())) {
					list.add(c);
				} else {
					deleteCandidate(c.getId());
				}

			}
			return list;
		} catch (SQLException e) {
			return null;
		}
	}
/**
 * Methods updating all information regarding a specific candidate in the database
 * @param c
 * @param dao
 * @return
 */
	public ArrayList<Candidate> updateCandidate(Candidate c, Dao dao) {
		try {
			String sql = "update candidate set fname=?, lname=?, city=?, age=?, profession=?, political_party=?, why_candidate=?, about=?, profile_pic=? where id=?";
			PreparedStatement pstmt = dao.conn.prepareStatement(sql);
			System.out.println(c.getProfile_pic());
			pstmt.setString(1, c.getFname());
			pstmt.setString(2, c.getLname());
			pstmt.setString(3, c.getCity());
			pstmt.setString(4, c.getAge());
			pstmt.setString(5, c.getProfession());
			pstmt.setString(6, c.getPolitical_party());
			pstmt.setString(7, c.getWhy_candidate());
			pstmt.setString(8, c.getAbout());
			pstmt.setString(9, c.getProfile_pic());
			pstmt.setString(10, c.getId());
			System.out.println(pstmt);
			pstmt.executeUpdate();
			return readAllCandidate();
		} catch (SQLException e) {
			return null;
		}
	}

/**
 * Method returning a candidate's profile picture based on his id in the database
 * @param id
 * @return
 */
	public String getPic(String id) {
		String img = "";

		try {
			String sql = "select profile_pic from candidate where id=?";
			Statement stmt = conn.createStatement();
			ResultSet RS = stmt.executeQuery(sql);

			img = RS.getString("profile_pic");

		} catch (SQLException e) {
			e.getStackTrace();
		}

		return img;

	}

/**
 * Method returning single candidate's info based on his id
 * @param id
 * @return
 */
	public Candidate getCandidate(String id) {
		Candidate c = null;
		try {

			ArrayList<Answer> list = getCanAnswerList(id);

			String sql = "select * from candidate where id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				c = new Candidate(String.valueOf(rs.getInt("id")), rs.getString("fname"), rs.getString("lname"),
						rs.getString("city"), rs.getString("age"), rs.getString("profession"),
						rs.getString("political_party"), rs.getString("why_candidate"), rs.getString("about"),
						rs.getString("profile_pic"), list);
			}

			return c;

		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 *Method returning single candidate's info based on his reference number
	 * @param ref
	 * @return
	 */
	public int getIdByRef(String ref) {

		System.out.println("ref >>" + ref);

		int id = 0;
		try {
			String sql = "select id from candidate where ref_num=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ref);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				id = rs.getInt("id");
			}
		} catch (SQLException e) {
			return 0;
		}
		return id;

	}

/**
 * Method inserting candidate object into database
 * @param c
 * @return
 */
	public boolean addCandidate(Candidate c) {
		String sql = "INSERT INTO candidate (fname, lname, city, age, profession, political_party, why_candidate, about, profile_pic, ref_num) VALUES (?,?,?,?,?,?,?,?,?,?)";
		try {

			System.out.println(c);

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, c.getFname());
			pstmt.setString(2, c.getLname());
			pstmt.setString(3, c.getCity());
			pstmt.setString(4, c.getAge());
			pstmt.setString(5, c.getProfession());
			pstmt.setString(6, c.getPolitical_party());
			pstmt.setString(7, c.getWhy_candidate());
			pstmt.setString(8, c.getAbout());
			pstmt.setString(9, c.getProfile_pic());
			pstmt.setString(10, c.getRef_num());

			System.out.println(pstmt);

			if (pstmt.executeUpdate() > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}

	}

	// Delete Candidate
//	public ArrayList<Candidate> deleteCandidate(String id) {
//		ArrayList<Candidate> a = new ArrayList<>();
//		try {
//			String sql = "delete from candidate where id=?";
//			PreparedStatement pstmt = conn.prepareStatement(sql);
//			// pstmt.setString(1, q.getText());
//			pstmt.setString(1, id);
//			if (pstmt.executeUpdate() > 0) {
//				a = readAllCandidate();
//			} else {
//				System.out.println("hmm");
//			}
//		} catch (SQLException e) {
//			e.getStackTrace();
//		}
//
//		return a;
//	}

	/**
	 * Boolean method deleting a candidate based on his id and returning true of false
	 * @param id
	 * @return
	 */
	
	public boolean deleteCandidate(String id) {
		try {
			String sql = "delete from candidate where id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			if (pstmt.executeUpdate() > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
	}
/**
 * Method creating an arraylist of answers for a specific candidate, fetching based on candidate's id stored in answer table
 * @param id
 * @return
 */
	public ArrayList<Answer> getCanAnswerList(String id) {
		ArrayList<Answer> list = new ArrayList<>();

		try {
			String sql = "select * from answer where can_id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				list.add(
						new Answer(String.valueOf(rs.getInt("id")), rs.getString("can_id"), rs.getString("question_id"),
								rs.getString("answer"), getQuestions(rs.getString("question_id")).getText()));
			}

			return list;

		} catch (SQLException e) {
			return null;
		}
	}
/**
 * Method returning an arraylist of answers for a specific questions based on question id stored in table answer
 * @param id
 * @return
 */
	public ArrayList<Answer> getAnsersByQuestionId(String id) {
		ArrayList<Answer> list = new ArrayList<>();

		try {
			String sql = "select * from answer where question_id=?;";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			System.out.println(pstmt);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				System.out.println(rs.getString("answer"));
				list.add(new Answer(rs.getString("can_id"), rs.getString("answer")));
			}

			return list;
		} catch (SQLException e) {
			return null;
		}

	}

/**
 * Method returning question object with specific id
 * @param id
 * @return
 */

	public Question getQuestions(String id) {
		Question q = null;

		try {
			String sql = "select * from question where id=?";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				q = new Question(String.valueOf(rs.getInt("id")), rs.getString("question"));
			}

			return q;

		} catch (SQLException e) {
			return null;
		}
	}

/**
 * Method returning id of a question stored with a specific reference number
 * @param ref
 * @return
 */
	public int getQuestionByRef(String ref) {
		int id = 0;
		try {
			String sql = "select id from question where question_ref=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ref);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				id = rs.getInt("id");
				System.out.println(id);
			}
		} catch (SQLException e) {
			return 0;
		}
		return id;
	}
/**
 * Method returning an arraylist of all questions stored in the database
 * @return
 */
	public ArrayList<Question> readAllQuestions() {
		ArrayList<Question> listQuestion = new ArrayList<>();
		try {
			String sql = "SELECT * FROM question";
			Statement stmt = conn.createStatement();
			ResultSet RS = stmt.executeQuery(sql);

			while (RS.next()) {
				Question q = new Question();
				q.setId(RS.getInt("id"));
				q.setText(RS.getString("question"));
				listQuestion.add(q);
			}
			return listQuestion;
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * Method inserting a new question object to database
	 * @param q
	 * @return
	 */
	public boolean addQuestion(Question q) {
		String sql = "INSERT INTO question (question, question_ref) VALUES (?,?)";
		try {

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, q.getText());
			pstmt.setString(2, q.getRef_num());

			if (pstmt.executeUpdate() > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}

	}
/**
 * Boolean method checking if a question with specific id exists, returns true or false
 * @param id
 * @return
 */
	public boolean checkQuestion(String id) {
		boolean check = true;
		String sql = "select count(answer) from answer where question_id=?";
		try {

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getInt("count(answer)") == 0) {
					check = false;
				}
			}
		} catch (SQLException e) {
			return false;
		}

		System.out.println(check);
		return check;
	}

	/**
	 * Method checking if answers for a candidate with a given id exist, returns true or false
	 * @param id
	 * @return
	 */
	public boolean checkCanAnswer(String id) {
		boolean check = true;
		String sql = "select count(answer) from answer where can_id=?";
		try {

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getInt("count(answer)") == 0) {
					check = false;
				}
			}
		} catch (SQLException e) {
			return false;
		}

		System.out.println(check);
		return check;
	}

	/**
	 * Method adding random answers for existing candidates to the newly added question
	 * @param r
	 */
	public void addAnswersForNewQuestion(ArrayList<RandomAnswer> r) {
		try {
			String sql = "insert into answer (can_id, question_id, answer) VALUES (?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			for (RandomAnswer ans : r) {
				pstmt.setString(1, ans.getCanId());
				pstmt.setString(2, ans.getQid());
				pstmt.setString(3, ans.getAnswer());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			System.out.println("succeed");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("error");
		}
	}

	/**
	 * Method updating a question based on its id
	 * @param q
	 * @return
	 */
	public boolean updateQuestion(Question q) {
		try {
			String sql = "update question set question=? where id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, q.getText());
			pstmt.setInt(2, q.getId());
			if (pstmt.executeUpdate() > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * Method deleting a question object based on its id
	 * @param q
	 * @return
	 */
	public boolean deleteQuestion(Question q) {
		try {
			String sql = "delete from question where id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// pstmt.setString(1, q.getText());
			pstmt.setInt(1, q.getId());
			if (pstmt.executeUpdate() > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * Get question statistics
	 * Method selecing count of a certain value answers for a question with specific id
	 * @param a
	 * @return
	 */
	public ArrayList<String> getStatistics(ArrayList<Integer> a) {
		ArrayList<String> s = new ArrayList<>();
		try {
			String sql = "select count(answer) from answer where question_id=? and answer=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);

			for (int i = 0; i < a.size(); i++) {

				String temp = "";
				for (int j = 1; j <= 5; j++) {
					pstmt.setInt(1, a.get(i));
					pstmt.setInt(2, j);
					ResultSet rs = pstmt.executeQuery();
					if (rs.next()) {
						if (j != 5) {
							temp += rs.getString("count(answer)") + ";";
						} else {
							temp += rs.getString("count(answer)");
						}
					}
				}
				s.add(temp);
			}
			System.out.println("succeed");
		} catch (SQLException e) {
			e.getStackTrace();
		}

		return s;
	}

	/**
	 * Log in method selecting an admin with specific email and password from database
	 * @param adm
	 * @return
	 */
	public boolean adminLogin(Admin adm) {
		try {
			int c = 0;
			String sql = "select * from admin where email=? and pwd=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, adm.getEmail());
			pstmt.setString(2, adm.getPass());
			ResultSet RS = pstmt.executeQuery();
			while (RS.next()) {
				c++;
			}
			if (c > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	/**
	 * Method that verifies and returns a list of admin objects that match data in the database
	 * @param adm
	 * @return
	 */
	public ArrayList<Admin> matchedAdmin(Admin adm) {
		ArrayList<Admin> list = new ArrayList<>();
		try {
			String sql = "select * from admin where email=? and pwd=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, adm.getEmail());
			pstmt.setString(2, adm.getPass());
			ResultSet RS = pstmt.executeQuery();
			while (RS.next()) {
				Admin ad = new Admin();
				ad.setId(RS.getString("id"));
				ad.setEmail(RS.getString("email"));
				list.add(ad);
			}
			System.out.println(RS.getFetchSize());
			return list;
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * Method that returns a count of admin objects that match certain data in the database
	 * @param adm
	 * @return
	 */
	public int matchedAdminInt(Admin adm) {
		try {
			int c = 0;
			String sql = "select * from admin where email=? and pwd=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, adm.getEmail());
			pstmt.setString(2, adm.getPass());
			ResultSet RS = pstmt.executeQuery();
			while (RS.next()) {
				c++;

			}
			return c;
		} catch (SQLException e) {
			return 100;
		}
	}

	/**
	 * Method that inserts an answer for a candidate into answer table
	 * @param answer
	 */
	public void addAnswerCandidate(ArrayList<Answer> answer) {
		try {
			String sql = "insert into answer (can_id, question_id, answer) VALUES (?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			for (Answer ans : answer) {
				pstmt.setString(1, ans.getCan_id());
				pstmt.setString(2, ans.getQuestion_id());
				pstmt.setString(3, ans.getAnswer());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			System.out.println("succeed");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("error");
		}
	}

	/**
	 * Method that updates an answer for a candidate in the answer table
	 * @param answer
	 */
	public void updateAnswerCandidate(ArrayList<Answer> answer) {
		try {
			String sql = "update answer set answer=? where can_id=? and question_id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			for (Answer ans : answer) {
				pstmt.setString(1, ans.getAnswer());
				pstmt.setString(2, ans.getCan_id());
				pstmt.setString(3, ans.getQuestion_id());
				System.out.println(pstmt);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			System.out.println("succeed");

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("error");
		}

	}

}