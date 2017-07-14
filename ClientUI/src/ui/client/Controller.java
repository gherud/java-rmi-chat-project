package ui.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {

	@FXML
	TextField msg = new TextField();
	@FXML
	TextArea chat = new TextArea();
	
	@FXML
	private void handleButtonAction(ActionEvent ev){
		if(!msg.getText().isEmpty()){
			System.out.println("new message: " + msg.getText());
			chat.appendText(msg.getText()+"\n");
		}
	}
}
