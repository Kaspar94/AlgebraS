package projekt;

import java.util.Scanner;

public class TestDeterminant {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Matrix a = new Matrix(sc);
		Determinant ofA = new Determinant(a);
		ofA.sarrus();
		
	}

}
