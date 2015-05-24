package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class KasutajaLiides extends Application {

	private static final String WORDS = "See programm on mõeldud maatriksiga seotud operatsioonide tegemiseks.\n"
			+ "Programmi võimalused:\n"
			+ "Maatriksi arvuga läbi korrutamine\n"
			+ "Maatriksite liitmine\n"
			+ "Maatriksite korrutamine\n"
			+ "Maatriksite transponeerimine\n"
			+ "Determinandi leidmine\n"
			+ "Crameri kasutamine\n"
			+ "\n"
			+ "Programmi kasutamine:\n"
			+ "Maatriksi määramiseks tuleb Size taga olevatesse lahtritesse panna\n"
			+ "maatriksi mõõtmed (esimesse veergu ridade arv, teisse veergude arv)\n"
			+ "ning seejärel vajutada update nuppu.\n"
			+ "Maatriksi arvuga korrutamiseks kirjutada arv, millega korrutada, nupu taga olevasse lahtrisse.\n"
			+ "Crameri valemite kasutamiseks kasutada nuppu Määra Cramer. Ilmunud maatriksi viimasesse veergu kirjutada\n"
			+ "võrrandite vastused ning viimase veeru ees olevatesse veergudesse kirjutada tundmatute kordajad.";

	public void start(Stage primaryStage) {
		Group root = new Group();
		GridPane k = new GridPane();

		Button mat = new Button("Maatriksid");
		Button info = new Button("Programmi info");
		k.add(mat, 2, 1);
		k.add(info, 1, 1);
		k.setPadding(new Insets(10, 10, 10, 10));
		k.setHgap(10);
		mat.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				MatrixInterface a = new MatrixInterface();
				a.start(new Stage());
			}
		});

		info.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				HBox hbox = new HBox();
				Label label = new Label(WORDS);
				label.setWrapText(true);
				label.setStyle("-fx-font-family: \"Arial\"; -fx-font-size: 12; -fx-text-fill: darkgreen;");
				hbox.getChildren().add(label);
				Stage stage = new Stage();
				stage.setTitle("Informatsioon");
				stage.setScene(new Scene(hbox, 400, 370));
				stage.setResizable(false);
				stage.show();
			}
		});

		root.getChildren().addAll(k);
		Scene scene = new Scene(root, 230, 50);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);

	}

}
