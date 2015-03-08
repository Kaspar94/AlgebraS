import java.util.ArrayList;
import java.util.Scanner;

class Matrix {
	private int rows = 0;
	private int cols = 0;
	private ArrayList<ArrayList<Double>> list;

	public Matrix() { // tavaline konstruktor
		this.list = new ArrayList<ArrayList<Double>>();
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

	public Matrix multiply_matrix(Matrix b) { // returns new Matrix
		if (this.cols != b.getRows()) { // kontrollime, kas saab korrutada.
			System.out.println("Column count of a doesnt match row count of b"
					+ this.cols + " " + b.getRows());
			return null;
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

	public ArrayList<ArrayList<Double>> getList() {
		return list;
	}

}
