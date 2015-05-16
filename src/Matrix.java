package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.control.Alert;

class Matrix {
	private int rows = 0;
	private int cols = 0;
	private ArrayList<ArrayList<Double>> list;

	public Matrix() { // tavaline konstruktor
		this.list = new ArrayList<ArrayList<Double>>();
	}

	public Matrix(Determinant a) {
		this.rows = a.getDrows();
		this.cols = a.getDcols();
		this.list = a.getMatrixlist();
	}

	public Matrix(Scanner sc) { // k2surealt konstruktor
		System.out
				.println("Sisesta maatriksi read ykshaaval, tyhik eraldajaks: (Lopetamiseks ..)");
		this.list = new ArrayList<ArrayList<Double>>();
		int rowCount = 0; // rea loendur
		String p = sc.nextLine();
		while (!p.equals("")) {
			String[] row = p.split(" ");
			// kontrollime, kas koik read yhepikkkused
			if (this.kontrolli_pikkust(row.length) == false) {
				System.out.println("Read peavad olema yhepikkused!");
				p = sc.nextLine();
				continue; // alustame otsast
			}
			this.cols = row.length;
			// lisame rea
			list.add(new ArrayList<Double>());
			// lisame iga elemendi listi.

			for (String s : row) {
				try {
					double temp = Double.parseDouble(s);
					list.get(rowCount).add(temp);
				} catch (Exception e) {
					System.out
							.println("Viga maatriksi rea sisse lugemisega. Proovi uuesti."
									+ e);
					break;
				}
			}
			rowCount += 1; // l2hme j2rgmise rea juurde
			p = sc.nextLine(); // loeme j'rgmise rea sisse
		}
		this.rows = rowCount;

	}

	public Matrix(String filename) {
		this.list = new ArrayList<ArrayList<Double>>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			int rowCount = 0;
			while (line != null) {
				String[] row = line.split(" ");
				// kontrollime, kas koik read yhepikkkused
				if (this.kontrolli_pikkust(row.length) == false) {
					System.out
							.println("Read peavad olema yhepikkused! Programm sulgub. Vaadake fail Yle.");
					reader.close();
					System.exit(0);
				}
				this.cols = row.length;
				// lisame rea
				list.add(new ArrayList<Double>());
				// lisame iga elemendi listi.

				for (String s : row) {
					try {
						double temp = Double.parseDouble(s);
						list.get(rowCount).add(temp);
					} catch (Exception e) {
						System.out
								.println("Viga maatriksi rea sisse lugemisega. Vaadake fail yle."
										+ e);
						reader.close();
						return;
					}
				}
				rowCount += 1; // l2hme j2rgmise rea juurde
				line = reader.readLine();
			}
			this.rows = rowCount;
			reader.close();
		} catch (Exception e) {
			System.out.println("Viga" + e);
		}
	}

	public Matrix(ArrayList<ArrayList<Double>> t) {
		this.list = t;
		this.rows = t.size();
		if (this.rows != 0) {
			this.cols = this.list.get(0).size();
		}
	}

	public void print() {
		for (ArrayList<Double> rida : this.list) {
			for (double element : rida) {
				System.out.print(element + " ");
			}
			System.out.println();
		}
	}

	private boolean kontrolli_pikkust(int x) { // kontrollib kas pikkust yhtib
												// teiste ridade pikkusega.
		for (ArrayList<Double> rida : this.list) {
			if (rida.size() != x) {
				return false;
			}
		}
		return true;
	}

	public void multiply(double arv) {
		for (int rida = 0; rida < this.list.size(); rida++) {
			for (int el = 0; el < this.list.get(rida).size(); el++) {
				this.list.get(rida).set(el, this.list.get(rida).get(el) * arv);
			}
		}
	}

	public void liida(Matrix b) throws Exception {
		if (this.list.size() != b.list.size()) {
			throw new Exception("Ridade arvud peavad yhtima.");
		} else if (this.cols != b.cols) {
			throw new Exception("Veergude arvud peavad yhtima");
		} else {
			try {
				for (int i = 0; i < b.list.size(); i++) {
					for (int s = 0; s < b.list.get(i).size(); s++) {
						this.list.get(i).set(s,
								b.list.get(i).get(s) + this.list.get(i).get(s));
					}
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new Exception("Ei onnestunud maatrikseid liita: " + e);
			}

		}
	}

	public void transponeeri() {
		double[][] temp = new double[this.cols][this.rows];
		for (int i = 0; i < this.getRows(); i++) {
			for (int s = 0; s < this.getCols(); s++) {
				temp[s][i] = this.list.get(i).get(s);
			}
		}
		this.list.clear();
		for (int i = 0; i < temp.length; i++) {
			this.list.add(new ArrayList<Double>());
			for (int j = 0; j < temp[i].length; j++) {
				this.list.get(i).add(temp[i][j]);
			}
		}
		int tempcol = this.getCols();
		this.cols = this.getRows();
		this.rows = tempcol;
		
	}

	public Matrix multiply_matrix(Matrix b) throws Exception { // returns new
																// Matrix
		if (this.cols != b.getRows()) { // kontrollime, kas saab korrutada.
			throw new Exception("Veergude arv peab olema v]rdne ridade arvuga.");
		}

		ArrayList<ArrayList<Double>> bList = b.getList();
		ArrayList<ArrayList<Double>> rtrn = new ArrayList<ArrayList<Double>>();

		for (int i = 0; i < this.rows; i++) { // initialise rows
			rtrn.add(new ArrayList<Double>());
		}

		double sum = 0;
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < b.getCols(); j++) {
				for (int k = 0; k < b.getRows(); k++) {
					sum += this.list.get(i).get(k) * bList.get(k).get(j);
				}
				rtrn.get(i).add(sum);
				sum = 0;
			}
		}
		return new Matrix(rtrn);
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public ArrayList<ArrayList<Double>> getList() {
		return list;
	}

}
