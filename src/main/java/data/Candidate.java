package data;

import java.util.ArrayList;
import java.sql.Timestamp;

/**
 * Candidate class
 *
 */
public class Candidate {
	
	/**
	 * Candidate id
	 */
	String id;
	
	/**
	 * Candidate first name
	 */
	String fname;
	
	/**
	 * Candidate last name
	 */
	String lname;
	
	/**
	 * Candidate city
	 */
	String city;
	
	/**
	 * Candidate age
	 */
	String age;
	
	/**
	 * Candidate profession/occupation
	 */
	String profession;
	
	/**
	 * Candidate political party
	 */
	String political_party;
	
	/**
	 * Short text: Why did you become a candidate
	 */
	String why_candidate;
	
	/**
	 * Short text: about the candidate
	 */
	String about;
	
	/**
	 * Profile picture name with extension
	 */
	String profile_pic;
	
	/**
	 * Picture folder/path
	 */
	String pic_path = "/img/";
	
	/**
	 * Reference number (time of creation down to miliseconds
	 */
	String ref_num = String.valueOf(new Timestamp(System.currentTimeMillis()).getTime());
	public String getRef_num() {
		return ref_num;
	}
	ArrayList<Answer> answers;
	
	
	public Candidate() 
	{
		
	}

	public Candidate(String id, String fname, String lname, String city, String age, String profession,
			String political_party, String why_candidate, String about, String profile_pic) {
		super();
		setId(id);
		this.fname = fname;
		this.lname = lname;
		this.city = city;
		this.age = age;
		this.profession = profession;
		this.political_party = political_party;
		this.why_candidate = why_candidate;
		this.about = about;
		this.profile_pic =  profile_pic;
	}
	
	public Candidate(String id, String fname, String lname, String city, String age, String profession,
			String political_party, String why_candidate, String about, String profile_pic, ArrayList<Answer> answers) {
		super();
		setId(id);
		this.fname = fname;
		this.lname = lname;
		this.city = city;
		this.age = age;
		this.profession = profession;
		this.political_party = political_party;
		this.why_candidate = why_candidate;
		this.about = about;
		this.profile_pic = profile_pic;
		this.answers = answers;
	}
	
	
	public Candidate( String fname, String lname, String city, String age, String profession,
			String political_party, String why_candidate, String about, String profile_pic) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.city = city;
		this.age = age;
		this.profession = profession;
		this.political_party = political_party;
		this.why_candidate = why_candidate;
		this.about = about;
		this.profile_pic = getPic_path() + profile_pic;
	}
	
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
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getPolitical_party() {
		return political_party;
	}
	public void setPolitical_party(String political_party) {
		this.political_party = political_party;
	}
	public String getWhy_candidate() {
		return why_candidate;
	}
	public void setWhy_candidate(String why_candidate) {
		this.why_candidate = why_candidate;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public String getProfile_pic() {
		return profile_pic;
	}
	public void setProfile_pic(String profile_pic) {
		this.profile_pic = profile_pic;
	}
	public String getPic_path() {
		return pic_path;
	}
	public void setPic_path(String pic_path) {
		this.pic_path = pic_path;
	}
	
	public ArrayList<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
	}
	
	
}
