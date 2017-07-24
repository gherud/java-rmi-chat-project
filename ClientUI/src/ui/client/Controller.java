package ui.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {

	@FXML private TextField msg = new TextField();
	@FXML public TextArea chat = new TextArea();
	
	@FXML
	private void handleButtonAction(ActionEvent ev){
		if(!msg.getText().isEmpty()){
			Client.sendToFriend(msg.getText().toString());
			System.out.println("new message: " + msg.getText().toString());
			chat.appendText("Ja: " + msg.getText().toString()+"\n");
			msg.clear();
		}
	}

	@FXML
	private void about(){
		Alert alert = new Alert(AlertType.INFORMATION);
		String title = "Informacja";
		alert.setTitle(title);

		String header = "Program utworzony przez Grzegorza Herud na potrzeby pracy magisterskiej";
		alert.setHeaderText(header);

		String desc = "Promotor: "
				+ "dr in¿. Lucjan Miêkina\n\nTytu³ pracy: \"System do ci¹g³ej integracji "
				+ "oprogramowania, zbudowany z u¿yciem œrodowiska Eclipse, Git"
				+ "i Jenkins\"";
		alert.setContentText(desc);

		alert.show();
	}
	
	@FXML
	private void close(){
		Platform.exit();
	}

	public TextArea getChatText() {
		return chat;
	}
}