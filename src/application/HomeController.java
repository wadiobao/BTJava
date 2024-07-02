package application;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.Normalizer;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
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
import javafx.scene.layout.HBox;
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

	private String oldname;
	private String defaulturl = "file:img/defautl.jpg";
	@FXML
	private TableView<Film> tbluser;
	@FXML
	private ImageView imgview;
	@FXML
	private Label hello;
	@FXML
	private TableColumn<Film, String> emailcol;
	@FXML
	private TableColumn<Film, String> fullnamecol;
	@FXML
	private TextField nametf, countrytf, directf, genretf, timetf, urltf;
	@FXML
	private TableColumn<Film, String> imgurl, genrecol, directcol;
	@FXML
	private TableColumn<Film, Integer> timecol;
	@FXML
	private Button changeimg,addbtn,deluser,modify,savebtn,cancelbtn;
	@FXML
	private HBox btnarea,modarea;
	@FXML
	private BorderPane bd;

	@FXML
	private Hyperlink logout;

	@FXML
	public void logOut() {
		logout.getScene().getWindow().hide();
	}

	@FXML
	public void add() {
		Film film = new Film(nametf.getText(), countrytf.getText(), genretf.getText(), directf.getText(),
				timetf.getText());
		UserDAO.addUser(film);
		cleartf();
		showUser();
	}

	@FXML
	public void deluser() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Thông báo");
		alert.setHeaderText("Bạn có muốn xóa không ?");
		alert.showAndWait().ifPresent(rep -> {
			if (rep == ButtonType.OK) {

				Film film = new Film(nametf.getText(), countrytf.getText(), genretf.getText(), directf.getText(),
						timetf.getText(), urltf.getText());
				String userurl = film.getImgurl().replace("'", "");

				if (!userurl.equals(defaulturl)) {
					Path del = Paths
							.get(new File("img").getAbsolutePath() + "/" + film.getImgurl().replace("file:img", ""));
					try {
						Files.deleteIfExists(del);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				UserDAO.delUser(film);
				nametf.setText(null);
				countrytf.setText(null);
				genretf.setText(null);
				directf.setText(null);
				timetf.setText(null);
				imgview.setImage(new Image(defaulturl));
				showUser();

			}
		});

	}

	@FXML
	public void choose() {
		Film film = tbluser.getSelectionModel().getSelectedItem();

		if (film != null) {
			nametf.setText(film.getName());
			countrytf.setText(film.getCountry());
			genretf.setText(film.getGenre());
			directf.setText(film.getDirector());
			timetf.setText(film.getTime() + "");
			urltf.setText(film.getImgurl().replace("'", ""));
			imgview.setImage(new Image(film.getImgurl().replace("'", "")));
			
			setAllTfEdit(false);
			
			if (role == 0) {
				oldname = nametf.getText();
				changeimg.setDisable(false);
				modify.setDisable(false);
				deluser.setDisable(false);
				addbtn.setDisable(true);
				
				if (nametf.getText().equals("(new)")) {
					cleartf();
					nametf.setPromptText("(new)");
					countrytf.setPromptText("(new)");
					genretf.setPromptText("(new)");
					directf.setPromptText("(new)");
					timetf.setPromptText("(new)");
					
					changeimg.setDisable(true);
					modify.setDisable(true);
					deluser.setDisable(true);
					addbtn.setDisable(false);
					
					setAllTfEdit(true);
					
				}
			}

		}
	}
	/*
	 * @FXML public void notchoose() { nametf.setText(null);
	 * countrytf.setText(null); genretf.setText(null); directf.setText(null);
	 * timetf.setText(null); imgview.setImage(new Image(defaulturl)); }
	 */

	@FXML
	public void changeImg() throws IOException, URISyntaxException {
		Stage stage = (Stage) bd.getScene().getWindow();
		FileChooser fc = new FileChooser();
		fc.setTitle("Chọn ảnh");
		FileChooser.ExtensionFilter imgfilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*,png");
		fc.getExtensionFilters().add(imgfilter);
		File file = fc.showOpenDialog(stage);

		if (file != null) {
			Film film = new Film(nametf.getText(), countrytf.getText(), genretf.getText(), directf.getText(),
					timetf.getText(), urltf.getText());

				String userurl = film.getImgurl().replace("'", "");
				if(!userurl.equals(defaulturl)) {
					Files.delete(Paths.get(userurl.replace("file:", "")));	
				}
				Image imgage = new Image(file.toURI().toString());
				imgview.setImage(imgage);

				Path source = Paths.get(file.toURI());
				Path target = Paths.get(new File("img").getAbsolutePath() + "/" + file.toPath().getFileName());
				Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);

				String ext = getExtension(target);

				Files.move(target, target
						.resolveSibling(hashCoding(nametf.getText(), ext, file.toPath().getFileName().toString())));

				imgurl.setText(
						"file:img" + "/" + hashCoding(nametf.getText(), ext, file.toPath().getFileName().toString()));
				
				film.setImgurl(imgurl.getText());
				
			UserDAO.imgChange(film);
			showUser();

		}
	}

	@FXML
	public void modifyAll() {
		displayNone(btnarea);
		display(modarea);
		
		setAllTfEdit(true);
		
	}
	
	@FXML
	public void save() {
		Film film = new Film(nametf.getText(), countrytf.getText(), genretf.getText(), directf.getText(),
				timetf.getText());
		UserDAO.updateUser(film, oldname);
		cleartf();
		imgview.setImage(new Image(defaulturl));
		
		displayNone(modarea);
		display(btnarea);
		
		setAllBtnDis(true);
		showUser();
	}
	@FXML
	public void cancel() {
		displayNone(modarea);
		display(btnarea);
		setAllTfEdit(false);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		Platform.runLater(() -> {
			hello.setText("Xin chào " + username);
			displayNone(urltf);
			displayNone(modarea);
			setAllTfEdit(false);
			setAllBtnDis(true);

			showUser();
		});

	}

	private void showUser() {
		emailcol.setCellValueFactory(new PropertyValueFactory<Film, String>("name"));
		fullnamecol.setCellValueFactory(new PropertyValueFactory<Film, String>("country"));
		imgurl.setCellValueFactory(new PropertyValueFactory<Film, String>("imgurl"));
		genrecol.setCellValueFactory(new PropertyValueFactory<Film, String>("genre"));
		directcol.setCellValueFactory(new PropertyValueFactory<Film, String>("director"));
		timecol.setCellValueFactory(new PropertyValueFactory<Film, Integer>("time"));

		List<Film> list = UserDAO.listAll();

		ObservableList<Film> oblist = FXCollections.observableArrayList(list);

		tbluser.setItems(oblist);
		if (role == 0) {
			autoAddCol();
		}
	}

	private void autoAddCol() {
		tbluser.getItems().add(new Film("(new)", "(new)", "(new)", "(new)", "(new)", defaulturl));
	}

	private void cleartf() {
		nametf.setText(null);
		countrytf.setText(null);
		genretf.setText(null);
		directf.setText(null);
		timetf.setText(null);
	}


	private String hashCoding(String name, String ext, String ran) {

		long hashint = Math.abs(name.toLowerCase().hashCode() * ran.toUpperCase().hashCode());
		String hashcoding = hashint + "." + ext;
		return hashcoding;
	}

	private String getExtension(Path path) {
		String fileName = path.getFileName().toString();
		int dotIndex = fileName.lastIndexOf('.');

		if (dotIndex == -1 || dotIndex == fileName.length() - 1) {
			return "";
		} else {
			return fileName.substring(dotIndex + 1);
		}
	}
	
	private void displayNone(Node node) {
		node.managedProperty().bind(node.visibleProperty());
		node.setVisible(false);		
	}
	
	private void display(Node node) {
		node.managedProperty().bind(node.visibleProperty());
		node.setVisible(true);		
	}
	
	
	private void setAllBtnDis(boolean a) {
		addbtn.setDisable(a);
		modify.setDisable(a);
		deluser.setDisable(a);
		changeimg.setDisable(a);
	}
	
	private void setAllTfEdit(boolean a) {
		nametf.setEditable(a);
		countrytf.setEditable(a);
		genretf.setEditable(a);
		directf.setEditable(a);
		timetf.setEditable(a);
	}
}
