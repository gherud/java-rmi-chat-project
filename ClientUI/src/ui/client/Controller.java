package ui.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {

	@FXML private TextField msg;
	@FXML private TextArea chat;
	
	@FXML
	private void handleButtonAction(ActionEvent ev){
		if(!msg.getText().isEmpty()){
			Client.sendToFriend(msg.getText().toString());
			System.out.println("new message: " + msg.getText().toString());
//			chat.appendText("Ja: " + msg.getText().toString()+"\n");
			msg.clear();
		}
	}

	@FXML
	private void about(){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Informacja");

		alert.setHeaderText("Program utworzony przez Grzegorza Herud na potrzeby pracy magisterskiej");

		alert.setContentText("Promotor: "
				+ "dr in¿. Lucjan Miêkina\n\nTytu³ pracy: \"System do ci¹g³ej integracji "
				+ "oprogramowania, zbudowany z u¿yciem œrodowiska Eclipse, Git"
				+ "i Jenkins\"");
		alert.show();
	}
	
	@FXML
	private void close(){
		Platform.exit();
	}
	
	public TextArea getTextArea(){
		return chat;
	}
}