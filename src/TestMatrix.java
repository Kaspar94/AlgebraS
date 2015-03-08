import java.util.Scanner;


public class TestMatrix {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		Matrix a = new Matrix(sc);
		Matrix b = new Matrix(sc);
		Matrix c = a.multiply_matrix(b);
		c.print();
	}

}
