package application;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MatrixInterface extends Application {
	private static VBox matA, matB;
	private HBox root;
	private int colsA, colsB;
	private ArrayList<TextField> elementsA, elementsB;
	private ArrayList<TextField> answers;
	private ArrayList<ArrayList<Double>> listA;
	private ArrayList<ArrayList<Double>> listB;
	// private TextField rowCountA, colCountA, rowCountB, colCountB;
	private TextField vastusA, vastusB;
	private Error errorHandler;
	private Scene scene;

	public void start(Stage primaryStage) {
		errorHandler = new Error();

		root = new HBox(); // Paneb kaks mati üksteise kõrvale (matA ja matB)
		matA = new VBox(); // Selle sees pannakse asjad üksteise alla
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
		this.scene = scene;
		primaryStage.show();
	}

	private HBox hboxA() {

		HBox row = new HBox();
		row.setSpacing(20); // määrab vahed Size, kastide ja update nupu vahel
		row.setPadding(new Insets(15, 12, 15, 12));
		Text size = new Text("Size: ");
		TextField rowCount = new TextField(); // kast ridade arvu jaoks
		TextField colCount = new TextField(); // kast veergude arvu jaoks
		rowCount.setPrefSize(30, 10); // mõõtmed
		colCount.setPrefSize(30, 10);
		Button update = new Button("Update A");
		Button cramer = new Button("Määra Cramer");
		update.setPrefSize(100, 20);
		cramer.setPrefSize(100, 20);// nupu mõõtmed

		row.getChildren().addAll(size, rowCount, colCount, update, cramer); // lisab
																	// HBoxi
																	// sulgudes
																	// olevad
																	// asjad,
																	// horisontaalselt
																	// üksteise
																	// kõrvale

		update.setOnMouseClicked(new EventHandler<MouseEvent>() { // hiiresündmus
																	// käsitlemaks
																	// update
																	// nupule
																	// vajutust
			public void handle(MouseEvent e) {
				try {
					errorHandler.resetCount();
					// saame k'tte soovitutd mootmed
					int r = Integer.parseInt(rowCount.getText()); // saab
																	// kastist
																	// rowCount
																	// kätte
																	// soovitud
																	// ridade
																	// arvu
					int c = Integer.parseInt(colCount.getText()); // saab
																	// kastist
																	// colCount
																	// kätte
																	// soovitud
																	// veergude
																	// arvu

					colsA = c; // väärtustab isendivälja kasutaja valitud
								// veergude arvuga

					// kustutame vanad teksfieldid
					elementsA = new ArrayList<TextField>();

					if (matA.getChildren().size() == 3) {
						matA.getChildren().remove(1);
					}
					matA.getChildren().add(1,
							setMatrixDisplay(r, c, elementsA, "A")); // väärtustab
																		// isendiväljad
				} catch (NumberFormatException n) { // kui proovitakse
					errorHandler
							.newError("Rea ja veeru suurus peavad olema arv.");
				}
			}
		});
		
		cramer.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				try {
					errorHandler.resetCount();
					// saame k'tte soovitutd mootmed
					int r = Integer.parseInt(rowCount.getText());
					int c = Integer.parseInt(colCount.getText());
					colsA = c;
					// kustutame vanad teksfieldid
					elementsA = new ArrayList<TextField>();
					answers = new ArrayList<TextField>();
					if (matA.getChildren().size() == 3) {
						matA.getChildren().remove(1);
					}
					matA.getChildren().add(1,
							setCramerDisplay(r, c, elementsA, answers, "A"));

				} catch (NumberFormatException n) { // kui proovitakse
													// skeemitada
					errorHandler
							.newError("Rea ja veeru suurus peavad olema arv.");

				}
			}
		});
				
		
		
		
		return row; // tagastabki HBoxi
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
					errorHandler.resetCount();
					// saame k'tte soovitutd mootmed
					int r = Integer.parseInt(rowCount.getText());
					int c = Integer.parseInt(colCount.getText());

					colsB = c;
					// kustutame vanad teksfieldid
					elementsB = new ArrayList<TextField>();
					if (matB.getChildren().size() == 3) {
						matB.getChildren().remove(1);
					}
					matB.getChildren().add(1,
							setMatrixDisplay(r, c, elementsB, "B"));

				} catch (NumberFormatException n) { // kui proovitakse
													// skeemitada
					errorHandler
							.newError("Rea ja veeru suurus peavad olema arv.");

				}
			}
		});
		return row;
	}

	private VBox setMatrixDisplay(int r, int c, ArrayList<TextField> elements,
			String s) { // rows,
		// cols,
		// loome uued textfieldid
		elements.clear(); // puhastame et uued lisada
		VBox vb = new VBox(); // hakkab hoidma ridu
		Text mat = new Text("Maatriks " + s);
		vb.getChildren().add(mat);
		vb.setPadding(new Insets(15, 12, 15, 12));
		for (int i = 0; i < r; i++) {
			HBox rida = new HBox(); // igale reale hbox
			for (int j = 0; j < c; j++) {
				TextField temp = new TextField();
				temp.textProperty().addListener(new ChangeListener<String>() {
					public void changed(ObservableValue<? extends String> arg0,
							String arg1, String arg2) {
						errorHandler.resetCount();
					}
				});
				temp.setPrefWidth(45);
				temp.setPrefHeight(30);
				elements.add(temp); // et p22seksime hiljem ligi
				rida.getChildren().add(temp);
			}
			vb.getChildren().add(rida);
		}
		return vb;
	}
	
	private VBox setCramerDisplay(int r, int c, ArrayList<TextField> elements, ArrayList<TextField> vastused,
			String s) { // rows,
		// cols,
		// loome uued textfieldid
		elements.clear(); // puhastame et uued lisada
		vastused.clear();
		VBox vb = new VBox(); // hakkab hoidma ridu
		Text mat = new Text("Cramer " + s);
		vb.getChildren().add(mat);
		vb.setPadding(new Insets(15, 12, 15, 12));
		for (int i = 0; i < r; i++) {
			HBox rida = new HBox(); // igale reale hbox
			for (int j = 0; j < c; j++) {
				
				if (j != c-1) {
					TextField temp = new TextField();
					temp.textProperty().addListener(new ChangeListener<String>() {
						public void changed(ObservableValue<? extends String> arg0,
								String arg1, String arg2) {
							errorHandler.resetCount();
						}
					});
					temp.setPrefWidth(45);
					temp.setPrefHeight(30);
					elements.add(temp); // et p22seksime hiljem ligi
					rida.getChildren().add(temp);

				}
				else {
					TextField temp = new TextField();
					TextField vastus = new TextField();
					temp.textProperty().addListener(new ChangeListener<String>() {
						public void changed(ObservableValue<? extends String> arg0,
								String arg1, String arg2) {
							errorHandler.resetCount();
						}
					});
					vastus.textProperty().addListener(new ChangeListener<String>() {
						public void changed(ObservableValue<? extends String> arg0,
								String arg1, String arg2) {
							errorHandler.resetCount();
						}
					});
					temp.setPrefWidth(45);
					temp.setPrefHeight(30);
					vastus.setPrefWidth(35);
					vastus.setPrefHeight(30);
					elements.add(temp); // et p22seksime hiljem ligi
					vastused.add(vastus);
					rida.getChildren().addAll(temp,vastus);
				}
			}
			vb.getChildren().add(rida);
		}
		return vb;
	}

	private VBox vboxB() {
		VBox vb = new VBox();
		HBox multiplication = new HBox();
		HBox determinant = new HBox();
		Button puhasta = new Button("Puhasta");
		Button multiply = new Button("Korruta l2bi arvuga");
		TextField multiplier = new TextField();
		multiplier.setPrefSize(50, 20);
		multiplication.getChildren().addAll(multiply, multiplier);
		Button liida_b = new Button("Liida maatriks A");
		Button transponeeri = new Button("Transponeeri"); // transp
		Button korruta_b = new Button("Korruta maatriksiga A");
		Button det_b = new Button("Arvuta determinant");
		Button cramer_b = new Button("Kasuta Cramerit");
		vastusB = new TextField();
		vastusB.setPrefSize(50, 20);
		determinant.getChildren().addAll(det_b, vastusB);
		vb.getChildren().addAll(puhasta, multiplication, liida_b, transponeeri,
				korruta_b, determinant, cramer_b);

		multiply.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				korrutaja(elementsB, multiplier);
			}
		});

		puhasta.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				clear_textField(elementsB);
				vastusB.clear();
			}
		});

		transponeeri.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				listB = textFieldToList(elementsB, colsB);
				transponeerija(listB, matB, elementsB);
			}
		});

		det_b.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				listB = textFieldToList(elementsB, colsB);
				detArvutaja(listB, vastusB);
			}
		});
		
		cramer_b.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println("nupp");
			}
		});

		return vb;
	}

	private VBox vboxA() {
		VBox vb = new VBox();
		HBox multiplication = new HBox();
		HBox determinant = new HBox();
		Button puhasta = new Button("Puhasta");
		Button multiply = new Button("Korruta l2bi arvuga");
		TextField multiplier = new TextField();
		multiplier.setPrefSize(50, 20);
		multiplication.getChildren().addAll(multiply, multiplier);
		Button liida_b = new Button("Liida maatriks B");
		Button transponeeri = new Button("Transponeeri"); // transp
		Button korruta_b = new Button("Korruta maatriksiga B");
		Button det_a = new Button("Arvuta determinant");
		Button cramer_a = new Button("Kasuta Cramerit");
		vastusA = new TextField();
		vastusA.setPrefSize(50, 20);
		determinant.getChildren().addAll(det_a, vastusA);
		vb.getChildren().addAll(puhasta, multiplication, liida_b, transponeeri,
				korruta_b, determinant, cramer_a);

		multiply.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				korrutaja(elementsA, multiplier);
			}
		});

		puhasta.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				clear_textField(elementsA);
				vastusA.clear();
			}
		});

		transponeeri.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				listA = textFieldToList(elementsA, colsA);
				transponeerija(listA, matA, elementsA);
			}
		});

		liida_b.setOnMouseClicked(new EventHandler<MouseEvent>() { // siin error
			public void handle(MouseEvent e) {
				listA = textFieldToList(elementsA, colsA);
				listB = textFieldToList(elementsB, colsB);
				liida_matrix(listA, listB, elementsA);
			}
		});

		korruta_b.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				try {
					listA = textFieldToList(elementsA, colsA);
					listB = textFieldToList(elementsB, colsB);
					Matrix a = new Matrix(listA);
					Matrix b = a.multiply_matrix(new Matrix(listB));
					if (b.getCols() == 0 || b.getRows() == 0) {
						return;
					}
					if (matA.getChildren().size() == 3) {
						matA.getChildren().remove(1);
					}
					matA.getChildren().add(
							1,
							setMatrixDisplay(b.getRows(), b.getCols(),
									elementsA, "A")); // väärtustab
					listToTextField(b.getList(), elementsA);
					colsA = b.getCols();
					// isendiväljad
				} catch (Exception m) {
					errorHandler.newError(m.getMessage());
				}
			}
		});

		det_a.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				listA = textFieldToList(elementsA, colsA);
				detArvutaja(listA, vastusA);
			}
		});
		
		
		cramer_a.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				listA = textFieldToList(elementsA, colsA);
				cramerArvutus(listA);
				
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
			errorHandler.newError(e.getMessage());
		}
	}

	private void korrutaja(ArrayList<TextField> elements, TextField multiplier) {
		try {
			double korrutaja = Double.parseDouble(multiplier.getText());
			for (TextField t : elements) {
				t.setText(Double.toString((Double.parseDouble(t.getText()) * korrutaja)));
			}
		} catch (NumberFormatException n) {
			errorHandler
					.newError("Korrutaja ning elemendid maatriksis peavad olema arvud");
		} catch (NullPointerException e) {
			errorHandler.newError("Maatriks pole m22ratud.");
		}
	}

	private void clear_textField(ArrayList<TextField> elements) {
		try {
			for (TextField t : elements) {
				t.clear();
			}
		} catch (NullPointerException e) {
			errorHandler.newError("Pole midagi puhastada.");
		}
	}

	private void transponeerija(ArrayList<ArrayList<Double>> list, VBox vb,
			ArrayList<TextField> elements) {
		if (!list.isEmpty()) {
			try {
				Matrix a = new Matrix(list);
				a.transponeeri();
				if (matA.getChildren().size() == 3) {
					matA.getChildren().remove(1);
				}
				matA.getChildren().add(
						1,
						setMatrixDisplay(a.getRows(), a.getCols(), elements,
								"A")); // väärtustab
				this.colsA = a.getCols();
				listToTextField(a.getList(), elements);
			} catch (Exception e) {
				errorHandler.newError(e.getMessage());
			}
		} else {
			errorHandler.newError("Andmeid pole sisestatud");
		}
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

	private void detArvutaja(ArrayList<ArrayList<Double>> list, TextField vastus) {
		try {
			Determinant a = new Determinant(list);
			double result = a.calculate_det();
			vastus.setText(Double.toString(result));
		} catch (Exception i) {
			errorHandler.newError("Maatriks on vigane." + i);
		}
	}
	
	private void cramerArvutus(ArrayList<ArrayList<Double>> list) {
		
		ArrayList<Double> vastused = new ArrayList<Double>();
		
		for (int i = 0; i < answers.size(); i++) {
			double solution = Double.parseDouble(answers.get(i).getText());
			System.out.println(solution);
			vastused.add(solution);
		}
		
		Cramer cramer = new Cramer(list,vastused);
		System.out.println(cramer.tagastaja(list));
		
	}

	private ArrayList<ArrayList<Double>> textFieldToList(
			ArrayList<TextField> elements, int cols) {
		if (elements == null) {
			return null;
		}
		ArrayList<ArrayList<Double>> list = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < elements.size() / cols; i++) {
			ArrayList<Double> sisemine = new ArrayList<Double>();
			list.add(sisemine);
		}
		int rowC = 0;
		int colC = 0;
		try {
			for (TextField t : elements) {
				Double p = Double.parseDouble(t.getText());
				list.get(rowC).add(p);
				colC += 1;
				if (colC == cols) {
					rowC += 1;
					colC = 0;
				}
			}
		} catch (Exception e) {
			errorHandler.newError(e.getMessage());
		}
		return list;
	}
}
