package data;

/**
 * Admin Class
 *
 */
public class Admin {
	/**
	 * Parameters defining admin class
	 */
	
	/**
	 * id
	 */
	private String id;
	/**
	 * first name
	 */
	private String fname;
	
	/**
	 * last name
	 */
	private String lname;
	
	/**
	 * email address
	 */
	private String email;
	
	/**
	 * Password, stored as md5 encrypted
	 */
	private String pass;
	
	
	/**
	 * getters and setters for the admin class
	 * @return
	 */
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	
}
