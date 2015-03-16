import java.util.ArrayList;
import java.util.Scanner;

class Determinant extends Matrix {
	private ArrayList<ArrayList<Double>> matrixList;
	private int dRows;
	private int dCols;
	private StringBuilder [] tundmatud;
	private ArrayList<Double> vastus;
	
	public Determinant(ArrayList<ArrayList<Double>> list) {
		this.matrixList = list;
		this.dRows = list.size();
		this.dCols = list.get(0).size();
	}
	
	public Determinant(Matrix a) {
		matrixList = a.getList();
		dRows = a.getRows();
		dCols = a.getCols();
	}
	public Determinant(Scanner sc) {
		System.out.println("Sisesta võrrandid ükshaaval, tühik eraldajaks: (Lõpetamiseks ..)");
		this.matrixList = new ArrayList<ArrayList<Double>>();
		int rowCount = 0;
		String pp = sc.nextLine();
		this.vastus = new ArrayList<Double>();
		while (!pp.equals("")) {
			String [] row = pp.split(" ");
			this.vastus.add(Double.parseDouble(row[row.length-1]));
			this.tundmatud = new StringBuilder[row.length - 1]; //siin massiivis hoiame kõiki võrrandite tundmatuid
			this.tundmatud = tundmatuteLeidja(row);
			if (kontrolli_pikkust(row.length - 1) == false) {
				System.out.println("Read peavad olema yhepikkused!");
				pp = sc.nextLine();
				continue; // alustame otsast
			}
			this.dCols = row.length - 1; // -1 sest, viimast veergu me ei taha
			matrixList.add(new ArrayList<Double>());
			for (int i = 0; i < dCols; i++) {
				try {
					double s = digifier(row)[i];
					matrixList.get(rowCount).add(s);
				} catch (Exception e) {
					System.out
							.println("Viga maatriksi rea sisse lugemisega. Proovi uuesti."
									+ e);
					break;
				}
			}
			rowCount += 1; // l2hme j2rgmise rea juurde
			pp = sc.nextLine(); // loeme j'rgmise rea sisse
		}
		this.dRows = rowCount;

	}
	
	public int getDrows() {
		return dRows;
	}
	
	public int getDcols() {
		return dCols;
	}
	
	public ArrayList<ArrayList<Double>> getMatrixlist(){
		return matrixList;
	}
	
	public void getTundmatud() {
		for (StringBuilder tundmatu: this.tundmatud) {
			System.out.println(tundmatu);
		}
	}
	
	public void getVastus() {
		for (double solution: this.vastus) {
			System.out.println(solution);
		}
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
	private int ridadeLeidja(Scanner sc) {
		
		int ridadeArv = 0;
		String line = sc.nextLine();
		while (!line.equals("")) {
			ridadeArv += 1;
			sc.nextLine();
		}
		return ridadeArv;
	}
	
	private StringBuilder [] tundmatuteLeidja(String [] rida) {
		StringBuilder [] tundmatuteList = new StringBuilder[rida.length-1];
		for (int i = 0; i < rida.length; i++) {
			StringBuilder tundmatus = new StringBuilder();
			for (int k = 0; k < rida[i].length();k++) {
				if (rida[i].charAt(k) != '-') {
					if (Character.isDigit(rida[i].charAt(k)) == false) {
						for (int n = k; n < rida[i].length(); n++) {
							tundmatus.append(rida[i].charAt(n));
						}
						for (int s = 0; s < tundmatuteList.length; s++) {
							if (tundmatuteList[s] == null) {
								tundmatuteList[s] = tundmatus;
								break;
							}	
						}
						break;
					}
				}
				
			}
			
		}
		return tundmatuteList;
	}
	
	
	private double [] digifier(String [] s) {
		double [] uusRida = new double[s.length]; // siin massiivis hoiame kõiki võrrandite kordajaid
		
		for (int k = 0; k < s.length; k ++) {
			StringBuilder uus = new StringBuilder(s[k]);  //s[k] on mingi veeru üks element näiteks võrrandi 3x2 + 2x5 + 3x7 puhul võib see olla näiteks 3x2, oleneb k-st (kui k = 1, siis s[1] = 3x2).
			
				for (int i = 0; i < uus.length(); i++) { // tsükkel, mis käib läbi stringi kõik elemendid(charid)
					if (uus.indexOf("-") != i ) {	//kui leima miinuse, siis peame teistmoodi otsima
						if (Character.isDigit(uus.charAt(i)) == false) { //kontrollime, kas element kohal i on number
							uus.delete(i, uus.length());	//eemaldame tundmatud
							break;
						}
					}
					else { // kui kordaja on negatiivne 
						char [] jarjend = new char[s.length-i];
						StringBuilder miinus = new StringBuilder("-"); // Kuna - on String, siis peab selle tegema StringBuilderiks, ei saa panna char [] jarjendisse.
						for (int r = 1; r < s.length-i;r++) {
							if (Character.isDigit(uus.charAt(r+i)) == true) { // nii kaua, kui element pole täht võtame numbreid
								jarjend[r-1] = uus.charAt(r+i);
							}
							else {
								break;
							}
						}
						String f = new String(jarjend);
						miinus.append(f);
						uus = miinus;
						break;
					}
						
				}
			try {
				double nr = Double.parseDouble(uus.toString());
				uusRida[k] = nr;
			} catch(Exception e) {
				try {
					if (uus.indexOf("-") == 0){
						uusRida[k] = -1;
					}
					else {
						uusRida[k] = 1;
					}
				} catch (Exception f) {
					continue;
				}
			}
		}
		return uusRida;
	}
	
	
	public boolean kontrolli_pikkust(int x) { // kontrollib kas pikkust yhtib
		// teiste ridade pikkusega.
		for (ArrayList<Double> rida : this.matrixList) {
			if (rida.size() != x) {
				return false;
			}
		}
		return true;
	}
		
	private double sarrus() throws FailException {

		 double sum = 0;
		 if (dRows == 3 && dCols == 3) {
			sum+= matrixList.get(0).get(0)*matrixList.get(1).get(1)*matrixList.get(2).get(2) + matrixList.get(1).get(0)*matrixList.get(2).get(1)*matrixList.get(0).get(2) +
					matrixList.get(0).get(1)*matrixList.get(1).get(2)*matrixList.get(2).get(0) - matrixList.get(0).get(2)*matrixList.get(1).get(1)*matrixList.get(2).get(0) -
					matrixList.get(0).get(1)*matrixList.get(1).get(0)*matrixList.get(2).get(2) - matrixList.get(0).get(0)*matrixList.get(1).get(2)*matrixList.get(2).get(1);
			return sum;
				 
		 } else {
		 	throw new FailException();
		 }
	}
	
	public void getSarrus() {
		try {
			System.out.println(this.sarrus());
		} catch(Exception e) {
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
	public void poordMat() {
		
		double [][] temp = new double[this.dRows][this.dCols];
		
		if (this.dCols == dRows) {
			if (this.calculate_det() != 0) {
				System.out.println("Maatriksi ");
				this.print();
				double kordaja = 1/this.calculate_det();
	
				for (int i = 0;i<this.matrixList.size();i++) {
					for (int s = 0; s<this.matrixList.get(i).size();s++) {
						int aste = i + s;
						if (aste%2 == 0) {
							temp [i][s] = (Algebraline_taiend(this,i,s).calculate_det());
						}
						else {
							temp [i][s] = (Algebraline_taiend(this,i,s).calculate_det())*-1;
						}
						
						
					}
				}
				this.matrixList.clear();
				for (int i = 0; i < temp.length; i++) {
					this.matrixList.add(new ArrayList<Double>());
					for (int j = 0; j < temp[i].length; j++) {
						this.matrixList.get(i).add(temp[i][j]);
					}
				}
				
				Matrix uus = new Matrix(this);
				uus.transponeeri();
				System.out.println("Pöördmaatriks on selline maatriks " );
				uus.print();
				System.out.println(",mis tuleb korrutada arvuga " + kordaja);
				uus.multiply(kordaja);
				uus.print();
			}
		
		}
	}
	
	public void cramer() {
		double algDet = this.calculate_det();
		ArrayList<ArrayList<Double>> list = new ArrayList<ArrayList<Double>>();
		double [][] massiiv = new double[this.dRows][this.dCols];
		ArrayList<Double> asendaja = this.vastus;
		int siseminePikkus = this.dCols;
		double o = 0;
		for (int i = 0; i < this.dRows; i++) {
			list.add(new ArrayList<Double>());
			for (int k = 0; k < this.dCols; k++) {
				list.get(i).add(this.matrixList.get(i).get(k));
			}
		}
		for (int i = 0; i < this.dCols; i++) {
			for (int k = 0; k < this.dRows; k++) {
				list.get(k).set(i, vastus.get(k));
			}
			Determinant c = new Determinant(list);
			System.out.println("Asendades " + this.tundmatud[i] + "-de veeru vastustega saame " + c.calculate_det() + ", seega on " + this.tundmatud[i] + " väärtus " + (c.calculate_det()/algDet)) ;
			
			for (int k = 0; k < this.dRows; k++) {
				list.get(k).set(i, this.matrixList.get(k).get(i));
			}
		}
		
		
	}
}
