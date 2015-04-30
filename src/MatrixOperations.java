package application;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MatrixOperations extends Application {
	static BorderPane root;
	private int rowsA, colsA, rowsB, colsB;
	private ArrayList<TextField> elementsA, elementsB;
	private ArrayList<ArrayList<Double>> listA;
	private ArrayList<ArrayList<Double>> listB;
	TextField rowCountA, colCountA, rowCountB, colCountB;

	public void start(Stage primaryStage) {

		root = new BorderPane();
		HBox rowA = this.hboxA();
		VBox actionsA = this.vbox();
		root.setTop(rowA);
		root.setBottom(actionsA);

		Scene scene = new Scene(root, 640, 400);
		primaryStage.setTitle("Operatsioonid maatriksitega");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private HBox hboxA() {
		HBox row = new HBox();
		row.setSpacing(20);
		row.setPadding(new Insets(15, 12, 15, 12));
		Text size = new Text("Size: ");
		TextField rowCount = new TextField();
		TextField colCount = new TextField();
		rowCount.setPrefSize(30, 10);
		colCount.setPrefSize(30, 10);
		Button update = new Button("Update");
		update.setPrefSize(100, 20);

		row.getChildren().addAll(size, rowCount, colCount, update);

		update.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				try {
					// saame k'tte soovitutd mootmed
					int r = Integer.parseInt(rowCount.getText());
					int c = Integer.parseInt(colCount.getText());

					rowsA = r;
					colsA = c;
					// kustutame vanad teksfieldid
					elementsA = new ArrayList<TextField>();

					// loome uued textfieldid
					VBox vb = new VBox(); // hakkab hoidma ridu
					Text matA = new Text("Maatriks A");
					vb.getChildren().add(matA);
					vb.setPadding(new Insets(15, 12, 15, 12));
					for (int i = 0; i < r; i++) {
						HBox rida = new HBox(); // igale reale hbox
						for (int j = 0; j < c; j++) {
							TextField temp = new TextField();
							temp.setPrefWidth(45);
							temp.setPrefHeight(30);
							elementsA.add(temp); // et p22seksime hiljem ligi
							rida.getChildren().add(temp);
						}
						vb.getChildren().add(rida);
					}
					root.setCenter(vb);
				} catch (NumberFormatException n) { // kui proovitakse
													// skeemitada
					new Error("Rea ja veeru suurus peavad olema arv.");
				}
			}
		});
		return row;
	}

	private VBox vbox() {
		VBox vb = new VBox();
		HBox multiplication = new HBox();
		Button multiply = new Button("Korruta l2bi arvuga");
		TextField multiplier = new TextField();
		multiplication.getChildren().addAll(multiply, multiplier);
		Button liida_b = new Button("Liida maatriks B");
		Button transponeeri = new Button("Transponeeri"); // transp
		Button korruta_b = new Button("Korruta maatriksiga B");
		vb.getChildren().addAll(multiplication, liida_b, transponeeri,
				korruta_b);

		return vb;

	}

	private void parseTextFields() {
		listA = new ArrayList<ArrayList<Double>>();
		listB = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> sisemine = new ArrayList<Double>();
		int temp = 0;
		try {
			for (int i = 0; i < elementsA.size(); i++) {
				if (i == elementsA.size() - 1) {
					sisemine.add(Double.parseDouble(elementsA.get(i).getText()));
					listA.add((ArrayList<Double>) sisemine.clone());
				}
				if (temp < colsA) {
					sisemine.add(Double.parseDouble(elementsA.get(i).getText()));
					temp += 1;
				} else {
					temp = 0;
					listA.add((ArrayList<Double>) sisemine.clone());
					System.out.println("ListA size: " + listA.size());
					sisemine.clear();

					sisemine.add(Double.parseDouble(elementsA.get(i).getText()));
					temp += 1;
				}
			}
		} catch (NumberFormatException e) {
			new Error("Maatriks tohib sisaldada aind numbreid. Proovi uuesti.");
		}
	}
}
