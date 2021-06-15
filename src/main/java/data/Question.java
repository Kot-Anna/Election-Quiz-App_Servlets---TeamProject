package data;

import java.sql.Timestamp;

/**
 * Class - question
 *
 */

public class Question {
	
	/**
	 * Question id
	 */
	private int id;
	
	/**
	 * Question text
	 */
	private String text;
	String ref_num = String.valueOf(new Timestamp(System.currentTimeMillis()).getTime());
	public Question(String id, String text) {
		// TODO Auto-generated constructor stub
		setId(id);
		this.text=text;
	}
	public Question() {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setId(String id) {
		try {
			this.id = Integer.parseInt(id);
		}
		catch(NumberFormatException | NullPointerException e) {
			//Do nothing - the value of id won't be changed
		}
	}
	public String getRef_num() {
		return ref_num;
	}
	public void setRef_num(String ref_num) {
		this.ref_num = ref_num;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
