package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class HomeController implements Initializable {
	@FXML
	private TableView<User> tbluser;
	@FXML
	private ImageView imgview;
	@FXML
	private Label hello;
	@FXML
	private TableColumn<User, String> emailcol;
	@FXML
	private TableColumn<User, String> fullnamecol;
	@FXML
	private TextField emailtf;
	@FXML
	private TextField fullnametf;
	@FXML
	private TableColumn<User, String> imgurl;
	@FXML
	public void add() {
		User user = new User(emailtf.getText(),fullnametf.getText());
		UserDAO.addUser(user);
		emailtf.setText(null);fullnametf.setText(null);
		showUser();
	}
	
	@FXML
	public void deluser() {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Thông báo");
		alert.setHeaderText("Bạn có muốn xóa không ?");
		alert.showAndWait().ifPresent(rep -> {
			if(rep == ButtonType.OK) {
				User user = new User(emailtf.getText(),fullnametf.getText());
				UserDAO.delUser(user);
				emailtf.setText(null);fullnametf.setText(null);
				showUser();
			} 
		});
		
	}
	
	@FXML
	public void choose() {
		User user = tbluser.getSelectionModel().getSelectedItem();
		if(user != null) {
			emailtf.setText(user.getEmail());
			fullnametf.setText(user.getFullname());
			imgview.setImage(new Image(user.getImgurl()));
			//System.out.println(user.getImgurl());
		}
	}
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		Platform.runLater(()->{
			hello.setText("Hello Bao");
		});
		
		showUser();
		
	}
	private void showUser() {
		emailcol.setCellValueFactory(new PropertyValueFactory<User,String>("Email"));
		fullnamecol.setCellValueFactory(new PropertyValueFactory<User,String>("Fullname"));
		imgurl.setCellValueFactory(new PropertyValueFactory<User,String>("imgurl"));
		
		List<User> list = UserDAO.listAll();
		
		ObservableList<User> oblist = FXCollections.observableArrayList(list);
		
		tbluser.setItems(oblist);
	}
	
	
}
