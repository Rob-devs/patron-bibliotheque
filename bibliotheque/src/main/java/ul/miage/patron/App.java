package ul.miage.patron;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	private static Scene scene;
	private static Stage primaryStage;

	@Override
	public void start(Stage stage) throws IOException {

		primaryStage = stage;

		loadAndSetScene("backoffice/MenuBack");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	private static void loadAndSetScene(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/ihm/" + fxml + ".fxml"));
		Parent root = fxmlLoader.load();

		// Set the scene
		scene = new Scene(root);
		primaryStage.setScene(scene);
	}

	public static void switchScene(String fxmlFile) {
		try {
			loadAndSetScene(fxmlFile);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch();
	}
}
