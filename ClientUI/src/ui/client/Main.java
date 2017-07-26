package ui.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static Controller controller;
	@Override
	public void start(Stage primaryStage) {
		try {
			// Załadowanie pliku FXML - UI.fxml
			Parent root = FXMLLoader.load(getClass().getResource("UI.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setOnCloseRequest(e -> Platform.exit());
			primaryStage.setTitle("Logged in as: " + Client.user.getUserName());
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}