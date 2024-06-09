package application;

public class UserInfor {
	private String emailid;
	private String password;
	private String fullname;
	private int role;

	public UserInfor() {

	}

	public UserInfor(String emailid, String password) {
		this.emailid = emailid;
		this.password = password;
	}

	public UserInfor(String emailid, String password, int role) {
		this.emailid = emailid;
		this.password = password;
		this.role = role;
	}
	
	

	public UserInfor(String emailid, String password, String fullname, int role) {
		this.emailid = emailid;
		this.password = password;
		this.fullname = fullname;
		this.role = role;
	}
	
	public UserInfor(String emailid, String password, String fullname) {
		this.emailid = emailid;
		this.password = password;
		this.fullname = fullname;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getPassword() {
		return password;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	
}
