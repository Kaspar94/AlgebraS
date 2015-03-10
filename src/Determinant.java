package projekt;
import java.util.ArrayList;
import java.util.Scanner;

class Determinant extends Matrix {
	private ArrayList<ArrayList<Double>> matrixList;
	private int dRows;
	private int dCols;
	
	public Determinant(Matrix a) {
		matrixList = a.getList();
		dRows = a.getRows();
		dCols = a.getCols();
	}
		
	public void sarrus() {

		 int sum = 0;
		 if (dRows == 3 && dCols == 3) {
			sum+= matrixList.get(0).get(0)*matrixList.get(1).get(1)*matrixList.get(2).get(2) + matrixList.get(1).get(0)*matrixList.get(2).get(1)*matrixList.get(0).get(2) +
					matrixList.get(0).get(1)*matrixList.get(1).get(2)*matrixList.get(2).get(0) - matrixList.get(0).get(2)*matrixList.get(1).get(1)*matrixList.get(2).get(0) -
					matrixList.get(0).get(1)*matrixList.get(1).get(0)*matrixList.get(2).get(2) - matrixList.get(0).get(0)*matrixList.get(1).get(2)*matrixList.get(2).get(1);
			System.out.print("Maatriksi determinant on " + sum);
				 
		 }
	}
		 

}
