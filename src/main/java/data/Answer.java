package data;

/**
 * Answer class
 *
 */
public class Answer {
	
	/**
	 * answers id
	 */
	String id;
	
	/**
	 * candidate's id 
	 * foreign key in the candidate table
	 */
	String can_id;
	
	/**
	 * question's id
	 * foreign key in the question tab,e
	 */
	String question_id;
	String answer;
	
	/**
	 * answer's text
	 */
	String answer_text;
	
	/**
	 * answer value
	 */
	String answer_value;
	
	/**
	 * profile image of a candidate who has given a specific answer
	 */
	String img;
	
	/**
	 *  party of a candidate who has given a specific answer
	 */
	String party;
	
	/**
	 * first name of a candidate who has given a specific answer
	 */
	String fname;
	
	/**
	 * last name of a candidate who has given a specific answer
	 */
	String lname;
	
	public Answer(String can_id, String question_id, String answer, String img, String party, String fname, String lname) {
		this.can_id = can_id;
		this.question_id = question_id;
		this.answer = answer;
		this.img = img;
		this.party = party;
		this.fname = fname;
		this.lname = lname;
		setAnswer_value(this.answer);
	}
	
	public Answer(String id, String can_id, String question_id, String answer, String answer_text) {
		setId(id);
		this.can_id = can_id;
		this.question_id = question_id;
		this.answer = answer;
		this.answer_text = answer_text;
		setAnswer_value(this.answer);
	}
	
	public Answer(String id, String can_id, String question_id, String answer) {
		setId(id);
		this.can_id = can_id;
		this.question_id = question_id;
		this.answer = answer;
	}

	public Answer(String can_id, String question_id, String answer) {
		this.can_id = can_id;
		this.question_id = question_id;
		this.answer = answer;
	}
	
	public Answer(String can_id, String answer) {
		this.can_id = can_id;
		this.answer = answer;
		setAnswer_value(this.answer);
	}
	
	
	/**
	 * @return the img
	 */
	public String getImg() {
		return img;
	}


	/**
	 * @param img the img to set
	 */
	public void setImg(String img) {
		this.img = img;
	}


	/**
	 * @return the party
	 */
	public String getParty() {
		return party;
	}


	/**
	 * @param party the party to set
	 */
	public void setParty(String party) {
		this.party = party;
	}


	/**
	 * @return the fname
	 */
	public String getFname() {
		return fname;
	}


	/**
	 * @param fname the fname to set
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}


	/**
	 * @return the lname
	 */
	public String getLname() {
		return lname;
	}


	/**
	 * @param lname the lname to set
	 */
	public void setLname(String lname) {
		this.lname = lname;
	}


	public String getAnswer_text() {
		return answer_text;
	}


	public void setAnswer_text(String answer_text) {
		this.answer_text = answer_text;
	}


	public String getAnswer_value() {
		return answer_value;
	}


	public void setAnswer_value(String answer_value) {
		switch(answer_value) {
		case "1": this.answer_value = "Strongly Disagree"; break;
		case "2": this.answer_value = "Disagree"; break;
		case "3": this.answer_value = "Neutral"; break;
		case "4": this.answer_value = "Agree"; break;
		case "5": this.answer_value = "Strongly Agree"; break;
		}
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getCan_id() {
		return can_id;
	}


	public void setCan_id(String can_id) {
		this.can_id = can_id;
	}


	public String getQuestion_id() {
		return question_id;
	}


	public void setQuestion_id(String question_id) {
		this.question_id = question_id;
	}


	public String getAnswer() {
		return answer;
	}


	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	
	
}
