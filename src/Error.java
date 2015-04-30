package application;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Error extends Application {
	private String err;
	public Error(String s) {
		this.err = s;
		this.start(new Stage());
	}
	public void start(Stage p) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Veateade");
		alert.setHeaderText(null);
		alert.setContentText(err);
		alert.showAndWait();
	}
}
