package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class UserDAO {
	@FXML
	private TextField emailtf;
	@FXML
	private TextField fullnametf;
	
	public static List<Film> listAll(){
		List<Film> list  = new ArrayList<Film>();
		
		String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
		String jdbcUser = "";
		String jdbcPass ="";
		String sql = "SELECT * FROM chitiet";
		
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection connect = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
			Statement statement = connect.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			while(result.next()) {
				String name = result.getString("tenphim");
				String country = result.getString("quocgia");
				String imgurl = result.getString("imgurl");
				String genre = result.getString("theloai");
				String director = result.getString("daodien");
				String time = result.getString("namsx");
				
				Film film = new Film(name, country, genre, director, time, imgurl);
				list.add(film);
			}
			
			connect.close();
			statement.close();
			result.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}
	
	@SuppressWarnings("finally")
	public static boolean addUser(Film film) {
		String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
		String jdbcUser = "";
		String jdbcPass ="";
		String sql = "INSERT INTO chitiet (tenphim,quocgia,theloai,daodien,namsx) VALUES (?,?,?,?,?)";
		boolean inserted = false;
		
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection connect = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
			PreparedStatement statement = connect.prepareStatement(sql);
			statement.setString(1, film.getName());
			statement.setString(2, film.getCountry());
			statement.setString(3, film.getGenre());
			statement.setString(4, film.getDirector());
			statement.setString(5, film.getTime());
			
			
			inserted = statement.executeUpdate() > 0;
			statement.close();
			connect.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return inserted;
		}
			return inserted;	
	}
	
	public static void delUser(Film film) {
		String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
		String jdbcUser = "";
		String jdbcPass ="";
		String sql = "DELETE FROM chitiet WHERE tenphim=? AND quocgia=? AND theloai=? AND daodien=? AND namsx=?";
		boolean deleted = false;
		
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection connect = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
			PreparedStatement statement = connect.prepareStatement(sql);
			statement.setString(1, film.getName());
			statement.setString(2, film.getCountry());
			statement.setString(3, film.getGenre());
			statement.setString(4, film.getDirector());
			statement.setString(5, film.getTime());
			
			deleted = statement.executeUpdate() > 0;
			statement.close();
			connect.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void imgChange(Film film) {
		String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
		String jdbcUser = "";
		String jdbcPass ="";
		String sql = "UPDATE chitiet SET imgurl = ? WHERE tenphim =?";
		boolean inserted = false;
		
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection connect = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
			PreparedStatement statement = connect.prepareStatement(sql);
			statement.setString(1, film.getImgurl());
			statement.setString(2, film.getName());
			
			inserted = statement.executeUpdate() > 0;
			statement.close();
			connect.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean updateUser(Film film,String oldname) {
		String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
		String jdbcUser = "";
		String jdbcPass ="";
		String sql = "UPDATE chitiet SET tenphim = ?, quocgia =?, theloai=?, daodien=?, namsx=? WHERE tenphim =?";
		boolean updated = false;
		
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection connect = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
			PreparedStatement statement = connect.prepareStatement(sql);
			statement.setString(1, film.getName());
			statement.setString(2, film.getCountry());
			statement.setString(2, film.getCountry());
			statement.setString(3, film.getGenre());
			statement.setString(4, film.getDirector());
			statement.setString(5, film.getTime());
			statement.setString(6, oldname);
			
			updated = statement.executeUpdate() > 0;
			statement.close();
			connect.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return updated;
		}
		
		return updated;
	}
	
	public static UserInfor checkUser(UserInfor in) {
		String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
		String jdbcUser = "";
		String jdbcPass ="";
		String sql = "SELECT * FROM tbluser";
		UserInfor infor = new UserInfor("", "", "", 0);
				
		
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection connect = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
			Statement statement = connect.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			while(result.next()) {
				String email = result.getString("email");
				String password = result.getString("password");
				String fullname = result.getString("fullname");
				int role = result.getInt("role");
				if(in.getEmailid().equals(email)&&in.getPassword().equals(password)){
					infor = new UserInfor(email, password,fullname,role);
				}
			}
			
			statement.close();
			connect.close();
			result.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return infor;
	}
	
	
	
}
