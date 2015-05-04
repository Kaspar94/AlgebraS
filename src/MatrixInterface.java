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

public class MatrixInterface extends Application {
	private static VBox matA, matB;
	private HBox root;
	private int colsA, colsB;
	private ArrayList<TextField> elementsA, elementsB;
	private ArrayList<ArrayList<Double>> listA;
	private ArrayList<ArrayList<Double>> listB;
	private TextField rowCountA, colCountA, rowCountB, colCountB;

	public void start(Stage primaryStage) {

		root = new HBox();
		matA = new VBox();
		HBox rowA = this.hboxA();
		VBox actionsA = this.vboxA();
		matA.getChildren().add(rowA);
		matA.getChildren().add(actionsA);

		matB = new VBox();
		HBox rowB = this.hboxB();
		VBox actionsB = this.vboxB();
		matB.getChildren().add(rowB);
		matB.getChildren().add(actionsB);

		root.getChildren().add(matA);
		root.getChildren().add(matB);

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
		Button update = new Button("Update A");
		update.setPrefSize(100, 20);

		row.getChildren().addAll(size, rowCount, colCount, update);

		update.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				try {
					// saame k'tte soovitutd mootmed
					int r = Integer.parseInt(rowCount.getText());
					int c = Integer.parseInt(colCount.getText());

					colsA = c;
					// kustutame vanad teksfieldid
					elementsA = new ArrayList<TextField>();
					if (matA.getChildren().size() == 3) {
						matA.getChildren().remove(1);
					}
					matA.getChildren()
							.add(1, setMatrixDisplay(r, c, elementsA));

				} catch (NumberFormatException n) { // kui proovitakse
													// skeemitada
					new Error("Rea ja veeru suurus peavad olema arv.");
				}
			}
		});
		return row;
	}

	private HBox hboxB() {

		HBox row = new HBox();
		row.setSpacing(20);
		row.setPadding(new Insets(15, 12, 15, 12));
		Text size = new Text("Size: ");
		TextField rowCount = new TextField();
		TextField colCount = new TextField();
		rowCount.setPrefSize(30, 10);
		colCount.setPrefSize(30, 10);
		Button update = new Button("Update B");
		update.setPrefSize(100, 20);

		row.getChildren().addAll(size, rowCount, colCount, update);

		update.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				try {
					// saame k'tte soovitutd mootmed
					int r = Integer.parseInt(rowCount.getText());
					int c = Integer.parseInt(colCount.getText());

					colsB = c;
					// kustutame vanad teksfieldid
					elementsB = new ArrayList<TextField>();
					if (matB.getChildren().size() == 3) {
						matB.getChildren().remove(1);
					}
					matB.getChildren()
							.add(1, setMatrixDisplay(r, c, elementsB));

				} catch (NumberFormatException n) { // kui proovitakse
													// skeemitada
					new Error("Rea ja veeru suurus peavad olema arv.");
				}
			}
		});
		return row;
	}

	private VBox setMatrixDisplay(int r, int c, ArrayList<TextField> elements) { // rows,
																					// cols,
		// loome uued textfieldid
		elements.clear(); // puhastame et uued lisada
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
				elements.add(temp); // et p22seksime hiljem ligi
				rida.getChildren().add(temp);
			}
			vb.getChildren().add(rida);
		}
		return vb;
	}

	private VBox vboxB() {
		VBox vb = new VBox();
		HBox multiplication = new HBox();
		Button puhasta = new Button("Puhasta");
		Button multiply = new Button("Korruta l2bi arvuga");
		TextField multiplier = new TextField();
		multiplication.getChildren().addAll(multiply, multiplier);
		Button liida_b = new Button("Liida maatriks A");
		Button transponeeri = new Button("Transponeeri"); // transp
		Button korruta_b = new Button("Korruta maatriksiga A");
		vb.getChildren().addAll(puhasta, multiplication, liida_b, transponeeri,
				korruta_b);

		multiply.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				korrutaja(elementsB, multiplier);
			}
		});

		puhasta.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				clear_textField(elementsB);
			}
		});

		transponeeri.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				listB = textFieldToList(elementsB, colsB);
				transponeerija(listB, matB, elementsB);
			}
		});

		return vb;
	}
	

	private VBox vboxA() {
		VBox vb = new VBox();
		HBox multiplication = new HBox();
		Button puhasta = new Button("Puhasta");
		Button multiply = new Button("Korruta l2bi arvuga");
		TextField multiplier = new TextField();
		multiplication.getChildren().addAll(multiply, multiplier);
		Button liida_b = new Button("Liida maatriks B");
		Button transponeeri = new Button("Transponeeri"); // transp
		Button korruta_b = new Button("Korruta maatriksiga B");
		vb.getChildren().addAll(puhasta, multiplication, liida_b, transponeeri,
				korruta_b);

		multiply.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				korrutaja(elementsA, multiplier);
			}
		});

		puhasta.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				clear_textField(elementsA);
			}
		});

		transponeeri.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				listA = textFieldToList(elementsA, colsA);
				transponeerija(listA, matA, elementsA);
			}
		});

		liida_b.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				listA = textFieldToList(elementsA, colsA);
				listB = textFieldToList(elementsB, colsB);
				liida_matrix(listA, listB, elementsA);
			}
		});
		
		korruta_b.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				listA = textFieldToList(elementsA, colsA);
				listB = textFieldToList(elementsB, colsB);
				Matrix a = new Matrix(listA);
				try {
					Matrix b = a.multiply_matrix(new Matrix(listB));
					b.print();
					listToTextField(b.getList(), elementsA);
				} catch(Exception m) {
					new Error(m.getMessage());
				}
			}
		});

		return vb;

	}

	private void liida_matrix(ArrayList<ArrayList<Double>> listA,
			ArrayList<ArrayList<Double>> listB, ArrayList<TextField> elements) {
		try {
			Matrix a = new Matrix(listA);
			a.liida(new Matrix(listB));
			this.listToTextField(a.getList(), elements);
		} catch (Exception e) {
			new Error(e.getMessage());
		}
	}

	private void korrutaja(ArrayList<TextField> elements, TextField multiplier) {
		try {
			double korrutaja = Double.parseDouble(multiplier.getText());
			for (TextField t : elements) {
				t.setText(Double.toString((Double.parseDouble(t.getText()) * korrutaja)));
			}
		} catch (NumberFormatException n) {
			new Error("Korrutaja ning elemendid maatriksis peavad olema arvud");
		}
	}

	private void clear_textField(ArrayList<TextField> elements) {
		for (TextField t : elements) {
			t.clear();
		}
	}

	private void transponeerija(ArrayList<ArrayList<Double>> list, VBox vb,
			ArrayList<TextField> elements) {
		Matrix a = new Matrix(list);
		a.transponeeri();
		vb.getChildren().remove(1);
		vb.getChildren().add(1,
				(setMatrixDisplay(a.getRows(), a.getCols(), elements)));
		this.listToTextField(a.getList(), elements);
	}

	private void listToTextField(ArrayList<ArrayList<Double>> list,
			ArrayList<TextField> elements) {
		int c = 0;
		for (ArrayList<Double> innerRow : list) {
			for (Double d : innerRow) {
				elements.get(c).setText(Double.toString(d));
				c += 1;
			}
		}
	}

	private ArrayList<ArrayList<Double>> textFieldToList(
			ArrayList<TextField> elements, int cols) {
		ArrayList<ArrayList<Double>> list = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> sisemine = new ArrayList<Double>();
		int temp = 0;
		try {
			for (int i = 0; i < elements.size(); i++) {
				if (i == elements.size() - 1) {
					sisemine.add(Double.parseDouble(elements.get(i).getText()));
					list.add((ArrayList<Double>) sisemine.clone());
				}
				if (temp < cols) {
					sisemine.add(Double.parseDouble(elements.get(i).getText()));
					temp += 1;
				} else {
					temp = 0;
					list.add((ArrayList<Double>) sisemine.clone());
					sisemine.clear();

					sisemine.add(Double.parseDouble(elements.get(i).getText()));
					temp += 1;
				}
			}
		} catch (NumberFormatException e) {
			new Error("Maatriks tohib sisaldada aind numbreid. Proovi uuesti.");
		} finally {
			return list;
		}
	}
}
