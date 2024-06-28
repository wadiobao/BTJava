package application;

public class Film {

	private String name;
	private String country;
	private String imgurl;
	private String genre;
	private String director;
	private String time;
	public Film(String name, String country, String imgurl) {
		
		this.name = name;
		this.country = country;
		this.imgurl = imgurl;
	}
	public Film(String name, String country) {
		
		this.name = name;
		this.country = country;
	}
	
	public Film() {}
	

	public Film(String name, String country, String genre, String director, String time,String imgurl) {
		this.name = name;
		this.country = country;
		this.imgurl = imgurl;
		this.genre = genre;
		this.director = director;
		this.time = time;
	}
	
	public Film(String name, String country, String genre, String director, String time) {
		this.name = name;
		this.country = country;
		this.genre = genre;
		this.director = director;
		this.time = time;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
	
	
}
