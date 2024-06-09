package application;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class HomeController implements Initializable {
	private String username;
	private int role;
	
	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private String oldmail;
	private String defaultemail = "file:img/defautl.jpg";
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
	private Button changeimg;
	@FXML
	private Button addbtn;
	@FXML
	private Button deluser;
	@FXML
	private BorderPane bd;
	@FXML
	private Button modify;
	@FXML
	private Hyperlink logout;

	@FXML
	public void logOut() {
		logout.getScene().getWindow().hide();
	}

	@FXML
	public void add() {
		User user = new User(emailtf.getText(), fullnametf.getText());
		UserDAO.addUser(user);
		emailtf.setText(null);
		fullnametf.setText(null);
		showUser();
	}

	@FXML
	public void deluser() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Thông báo");
		alert.setHeaderText("Bạn có muốn xóa không ?");
		alert.showAndWait().ifPresent(rep -> {
			if (rep == ButtonType.OK) {
				User user = new User(emailtf.getText(), fullnametf.getText(), imgurl.getText());
				String userurl = user.getImgurl().replace("'", "");
				if (!userurl.equals(defaultemail)) {
					Path del = Paths
							.get(new File("img").getAbsolutePath() + "/" + user.getImgurl().replace("file:img", ""));
					try {
						Files.deleteIfExists(del);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				UserDAO.delUser(user);
				emailtf.setText(null);
				fullnametf.setText(null);
				imgview.setImage(new Image(defaultemail));
				showUser();

			}
		});

	}

	@FXML
	public void choose() {
		User user = tbluser.getSelectionModel().getSelectedItem();
		if (user != null) {
			emailtf.setText(user.getEmail());
			fullnametf.setText(user.getFullname());
			imgview.setImage(new Image(user.getImgurl().replace("'", "")));
			// System.out.println(user.getImgurl());
			oldmail = emailtf.getText();
		}
	}

	@FXML
	public void changeImg() throws IOException, URISyntaxException {
		Stage stage = (Stage) bd.getScene().getWindow();
		FileChooser fc = new FileChooser();
		fc.setTitle("Chọn ảnh");
		FileChooser.ExtensionFilter imgfilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*,png");
		fc.getExtensionFilters().add(imgfilter);
		File file = fc.showOpenDialog(stage);
		if (file != null) {
			Image imgage = new Image(file.toURI().toString());
			imgview.setImage(imgage);

			Path source = Paths.get(file.toURI());
			Path target = Paths.get(new File("img").getAbsolutePath() + "/" + file.toPath().getFileName());
			Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);

			imgurl.setText("file:img" + "/" + file.toPath().getFileName());
			User user = new User(emailtf.getText(), fullnametf.getText(), imgurl.getText());
			UserDAO.imgChange(user);
			showUser();
		}
	}

	@FXML
	public void modifyAll() {
		User user = new User(emailtf.getText(), fullnametf.getText());
		UserDAO.updateUser(user, oldmail);
		emailtf.setText(null);
		fullnametf.setText(null);
		showUser();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		Platform.runLater(() -> {
			hello.setText("Xin chao "+username);
			if(role==1) {
				addbtn.setDisable(true);
				modify.setDisable(true);
				deluser.setDisable(true);
				changeimg.setDisable(true);
				emailtf.setDisable(true);
				fullnametf.setDisable(true);
			}
		});
		
		showUser();

	}

	private void showUser() {
		emailcol.setCellValueFactory(new PropertyValueFactory<User, String>("Email"));
		fullnamecol.setCellValueFactory(new PropertyValueFactory<User, String>("Fullname"));
		imgurl.setCellValueFactory(new PropertyValueFactory<User, String>("imgurl"));

		List<User> list = UserDAO.listAll();

		ObservableList<User> oblist = FXCollections.observableArrayList(list);

		tbluser.setItems(oblist);
	}

}
