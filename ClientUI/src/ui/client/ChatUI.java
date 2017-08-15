package ui.client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChatUI extends Application {

	private ListView<String> userList;
	private TextArea display;
	private TextField input;
	private ClientImpl client;
	private Button btn;

	@Override
	public void start(Stage stage) throws Exception {
		Platform.setImplicitExit(false);
		this.dialog();
		this.setupNewStage(stage);
	}

	// OKNO S£U¯¥CE DO WPROWADZANIA NAZWY U¯YTKOWNIKA ORAZ SPRAWDZAJ¥CE POPRAWNOŒÆ
	private void dialog() {
		boolean loop = false;
		while(!loop) {
			TextInputDialog dialog = this.createTextInputDialog();
			Optional<String> result = dialog.showAndWait();
			String errorMessage = "";
			if(!result.isPresent()) {
				this.closeThis();
				loop = true;
			}
			if(result.get().length() > 0 && result.get().length() < 20) {
				this.initClient();
				if (this.isNicknameOkay(result.get())){
					loop = true;
					return;
				}
				else errorMessage = "U¿ytkownik ju¿ istnieje. Wybierz inn¹ nazwê.";
			}
			else errorMessage = "Nazwa u¿ytkownika musi mieæ d³ugoœæ od 1 do 20 znaków.";
			this.connectionError(errorMessage);
		}
		this.closeThis();
	}

	// Funkcja sprawdzajaca poprawnosc nicku
	private boolean isNicknameOkay(String s) {
		boolean b = false;
		try {
			b = this.client.isNicknameOkay(s);
		}
		catch (RemoteException e) {
			this.connectionError("Wyst¹pi³ problem podczas sprawdzania dostêpnoœci nazwy."
					+ "Spróbuj ponownie!");
		}
		return b;
	}

	private TextInputDialog createTextInputDialog() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Wprowadz nazwê u¿ytkownika");
		dialog.setHeaderText("");
		dialog.setContentText("Nazwa u¿ytkownika:");
		dialog.initModality(Modality.APPLICATION_MODAL);
		return dialog;
	}

	private void connectionError(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("B£¥D!");
		alert.setContentText(message);
		alert.showAndWait();
	}

	private void initClient() {
		if (this.client == null)
			try {
				this.client = new ClientImpl(this);
			}
		catch (RemoteException e) {
			this.connectionError("RemoteException - Serwer nie jest w³¹czony.");
			this.closeThis();
		}
		catch (MalformedURLException | NotBoundException e) {
			this.connectionError("B³¹d po³¹czenia!");
			this.closeThis();
		}
	}

	private void setupNewStage(Stage stage) {
		// POLE DO WPISYWANIA WIADOMOŒCI
		this.input = new TextField();
		input.setPrefWidth(535);
		input.setOnAction(e -> {
			if(!input.getText().isEmpty()){//sprawdzenie czy pole tekstowe zawiera tekst
				client.sendMessage(input.getText());
				input.setText("");
			}
		});
		// LISTA ZALOGOWANYCH U¯YTKOWNIKÓW
		this.userList = new ListView<String>();
		userList.setMinWidth(200.0);
		// OKNO CHATU
		this.display = new TextArea();
		display.prefHeight(300.0);
		display.setStyle("-fx-text-fill: black; -fx-font-size: 12;");
		display.prefWidth(400.0);
		display.setEditable(false);
		// PRZYCISK DO WYSY£ANIA WIADOMOŒCI
		this.btn = new Button("Wyœlij");
		btn.setPrefWidth(75);
		btn.setOnAction(e -> {
			if(!input.getText().isEmpty()){//sprawdzenie czy pole tekstowe zawiera tekst
				client.sendMessage(input.getText());
				input.setText("");
			}
		});
		// ---------- TWORZENIE SCENY ----------
		BorderPane root = new BorderPane();
		GridPane bottomRoot = new GridPane();
		bottomRoot.add(this.input, 0, 0);
		bottomRoot.add(this.btn, 1, 0);
		root.setLeft(this.display);
		root.setRight(this.userList);
		root.setBottom(bottomRoot);
		Scene scene = new Scene(root, 600, 450);
		stage.setOnCloseRequest(e -> {
			this.client.logout();
			this.closeThis();
		});
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Chat");
		stage.show();
	}

	private void closeThis() {
		Platform.exit();
		System.exit(0);
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	public void sendMessage(String message) {
		Platform.runLater(() -> ChatUI.this.display.appendText("\n" + message));
	}

	public void newUserList(String[] userList2) {
		Platform.runLater(() -> {
			this.userList.setItems(null);
			ObservableList<String> list = FXCollections.observableArrayList(userList2);
			FXCollections.sort(list);
			this.userList.setItems(list);
		});
	}
}