package ul.miage.patron;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class Controller {
	// Eléments de l'interface JavaFX
	@FXML
	Button btnAdd;
	@FXML
	TextField tfNom, tfPrenom;
	@FXML
	ListView<User> listViewUsers;
	
	final Logger LOG = Logger.getLogger(App.class.getName());
	ObservableList<User> users = FXCollections.observableArrayList();
	
	
	public void initialize() {
		LOG.info("Initialisation du contrôleur");
		listViewUsers.setItems(users);
		listViewUsers.setCellFactory(lv -> new ListCell<User>() {
			@Override
			public void updateItem(User item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setText(null);
				} else {
					String text = item.getName() + " " + item.getSurname(); // get text from item
					setText(text);
				}
			}
		});
	}

	public void addUser(){
		users.add(new User(tfNom.getText(), tfPrenom.getText()));
		LOG.info("Ajout de l'utilisateur "+tfNom.getText()+" "+tfPrenom.getText());
		LOG.info("Nombre d'utilisateurs : "+users.size());
	}
}
