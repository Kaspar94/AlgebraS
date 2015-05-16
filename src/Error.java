package application;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Error extends Application {
	private String err;
	private int count;

	public Error() {
		count = 0;
	};
	
	public void newError(String s) {
		if (count != 0)
			return;
		this.err = s;
		this.start(new Stage());
		count += 1;
	}
	
	public void resetCount() {
		count = 0;
	}

	public void start(Stage p) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Veateade");
		alert.setHeaderText(null);
		alert.setContentText(err);
		alert.showAndWait();
	}
}
