package data;

/**
 * Random answer class
 * Needed for inserting random answers for a new question
 * for existing candidates
 *
 */
public class RandomAnswer {
	
	/**
	 * Candidate id
	 */
	String canId;
	
	/**
	 * Question id
	 */
	String qid;
	
	/**
	 * random answer
	 */
	String answer;
	
	/**
	 * Candidate's profile picture, needed for display purposes
	 */
	String pic;
	public RandomAnswer(String canId, String qid, String answer, String pic) {
		super();
		this.canId = canId;
		this.qid = qid;
		this.answer = answer;
		this.pic = pic;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getCanId() {
		return canId;
	}
	public void setCanId(String canId) {
		this.canId = canId;
	}
	public String getQid() {
		return qid;
	}
	public void setQid(String qid) {
		this.qid = qid;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	

}
