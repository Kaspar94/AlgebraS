package projekt;
import java.util.ArrayList;

class Determinant extends Matrix {
	private ArrayList<ArrayList<Double>> matrixList;
	private int dRows;
	private int dCols;
	
	public Determinant(Matrix a) {
		matrixList = a.getList();
		dRows = a.getRows();
		dCols = a.getCols();
	}
	
	@Override
	public void print() {
		for (ArrayList<Double> rida : this.matrixList) {
			for (double element : rida) {
				if (element == Math.floor(element)) {
					System.out.print((int) element + " ");
				} else {
					System.out.print(element + " ");
				}
			}
			System.out.println();
		}
	}
	
	public double sarrus() throws FailException {

		 double sum = 0;
		 if (dRows == 3 && dCols == 3) {
			sum+= matrixList.get(0).get(0)*matrixList.get(1).get(1)*matrixList.get(2).get(2) + matrixList.get(1).get(0)*matrixList.get(2).get(1)*matrixList.get(0).get(2) +
					matrixList.get(0).get(1)*matrixList.get(1).get(2)*matrixList.get(2).get(0) - matrixList.get(0).get(2)*matrixList.get(1).get(1)*matrixList.get(2).get(0) -
					matrixList.get(0).get(1)*matrixList.get(1).get(0)*matrixList.get(2).get(2) - matrixList.get(0).get(0)*matrixList.get(1).get(2)*matrixList.get(2).get(1);
			System.out.print("Maatriksi determinant on " + sum);
				 
		 } else {
		 	throw new FailException();
		 }
	}
	
	public double x2() throws FailException {
		if (this.dRows == 2 && this.dCols == 2) {
			return this.matrixList.get(0).get(0)
					* this.matrixList.get(1).get(1)
					- this.matrixList.get(0).get(1)
					* this.matrixList.get(1).get(0);
		} else {
			throw new FailException();
		}
	}

	public double x1() throws FailException {
		if (this.dRows == 1 && this.dCols == 1) {
			return this.matrixList.get(0).get(0);
		} else {
			throw new FailException();
		}
	}
	
	public double calculate_det() {
		if (this.dCols != this.dRows) {
			System.out.println("Ruutmaatriks peab olema");
			return 0;
		}
		ArrayList<Double> rida = this.matrixList.get(0); // rida mille j2rgi
															// arendame
		double sum = 0;
		for (int i = 0; i < rida.size(); i++) { // k2ime l2bi k5ik

			double element = rida.get(i);
			double m = Math.pow(-1, i + 2);
			try {
				Determinant k = this.Algebraline_taiend(this, 0, i);
				double s;
				if (k.dCols == 3 && k.dRows == 3) {
					s = k.sarrus();
				} else if (k.dCols == 2 && k.dRows == 2) {
					s = k.x2();
				} else if (k.dRows == 1 && k.dCols == 1) {
					s = k.x1();
				} else { // siseneme rekursiivselt
					s = k.calculate_det();
				}
				sum += element * m * s;

			} catch (Exception e) {
				System.out.println("Viga" + e);
				System.exit(0);// v'ljume?
			}
		}
		return sum;
	}
	
	private Determinant Algebraline_taiend(Determinant d, int rida, int veerg) { // tagastab
																					// algebralise
																					// taiendi
		ArrayList<ArrayList<Double>> main = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> row = new ArrayList<Double>();

		for (int i = 0; i < d.matrixList.size(); i++) {
			if (i != rida) {
				for (int j = 0; j < d.matrixList.get(i).size(); j++) {
					if (j != veerg) {
						row.add(d.matrixList.get(i).get(j));
					}
				}
				main.add((ArrayList<Double>) row.clone()); // ????
				row.clear();
			}
		}
		return new Determinant(new Matrix(main));
	}

}
