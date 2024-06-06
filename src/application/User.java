package application;

public class User {

	private String email;
	private String fullname;
	private String imgurl;
	public User(String email, String fullname, String imgurl) {
		
		this.email = email;
		this.fullname = fullname;
		this.imgurl = imgurl;
	}
	public User(String email, String fullname) {
		
		this.email = email;
		this.fullname = fullname;
	}
	
	public User() {}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	
	
}
