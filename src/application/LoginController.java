package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	
	@FXML
	private Button sgin;
	
	@FXML
	private TextField email;
	@FXML
	private PasswordField passf;
	@FXML
	private Label errorlabel;
	
	@FXML
	public void signIn() throws IOException{
		UserInfor infor = new UserInfor(email.getText(),passf.getText());
		UserInfor check = UserDAO.checkUser(infor);
		if(infor.getEmailid().equals(check.getEmailid())) {
			sgin.getScene().getWindow().hide();
			goHome(check.getFullname(),check.getRole());
		}else {
			errorlabel.setVisible(true);
		}
	}
	
	public void goHome(String name,int role) throws IOException {
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("Home.fxml"));
		Parent root = (Parent)fxmlloader.load();
		HomeController hcl = fxmlloader.getController();
		hcl.setUsername(name);
		hcl.setRole(role);
				
		Scene scene = new Scene(root);
		Stage homeStage  = new Stage();
		homeStage.setScene(scene);
		homeStage.show();
				
	}
}
