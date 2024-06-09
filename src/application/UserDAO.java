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
	
	public static List<User> listAll(){
		List<User> list  = new ArrayList<User>();
		
		String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
		String jdbcUser = "";
		String jdbcPass ="";
		String sql = "SELECT * FROM tbluser";
		
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection connect = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
			Statement statement = connect.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			while(result.next()) {
				String email = result.getString("email");
				String fullname = result.getString("fullname");
				String imgurl = result.getString("imgurl");
				
				User user = new User(email, fullname,imgurl);
				list.add(user);
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
	
	public static void addUser(User user) {
		String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
		String jdbcUser = "";
		String jdbcPass ="";
		String sql = "INSERT INTO tbluser (email,fullname) VALUES (?,?)";
		boolean inserted = false;
		
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection connect = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
			PreparedStatement statement = connect.prepareStatement(sql);
			statement.setString(1, user.getEmail());
			statement.setString(2, user.getFullname());
			
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
	
	public static void delUser(User user) {
		String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
		String jdbcUser = "";
		String jdbcPass ="";
		String sql = "DELETE FROM tbluser WHERE email=? AND fullname=?";
		boolean deleted = false;
		
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection connect = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
			PreparedStatement statement = connect.prepareStatement(sql);
			statement.setString(1, user.getEmail());
			statement.setString(2, user.getFullname());
			
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
	
	public static void imgChange(User user) {
		String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
		String jdbcUser = "";
		String jdbcPass ="";
		String sql = "UPDATE tbluser SET imgUrl = ? WHERE email =?";
		boolean inserted = false;
		
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection connect = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
			PreparedStatement statement = connect.prepareStatement(sql);
			statement.setString(1, user.getImgurl());
			statement.setString(2, user.getEmail());
			
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
	
	public static void updateUser(User user,String oldmail) {
		String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
		String jdbcUser = "";
		String jdbcPass ="";
		String sql = "UPDATE tbluser SET email = ?, fullname =? WHERE email =?";
		boolean updated = false;
		
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection connect = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
			PreparedStatement statement = connect.prepareStatement(sql);
			statement.setString(1, user.getEmail());
			statement.setString(2, user.getFullname());
			statement.setString(3, oldmail);
			
			updated = statement.executeUpdate() > 0;
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
