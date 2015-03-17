package projekt;

import java.util.ArrayList;
import java.util.Scanner;

class Cramer {
	private StringBuilder[] tundmatud;
	private ArrayList<Double> vastus;
	private ArrayList<ArrayList<Double>> cramerList;
	private int dRows, dCols;

	public Cramer(Scanner sc) {
		System.out
				.println("Sisesta võrrandid ükshaaval, tühik eraldajaks: (Lõpetamiseks ..)");
		this.cramerList = new ArrayList<ArrayList<Double>>();
		int rowCount = 0;
		String pp = sc.nextLine();
		this.vastus = new ArrayList<Double>();
		while (!pp.equals("")) {
			String[] row = pp.split(" ");
			this.vastus.add(Double.parseDouble(row[row.length - 1]));
			this.tundmatud = new StringBuilder[row.length - 1]; // siin
																// massiivis
																// hoiame kõiki
																// võrrandite
																// tundmatuid
			this.tundmatud = tundmatuteLeidja(row);
			if (kontrolli_pikkust(row.length - 1) == false) {
				System.out.println("Read peavad olema yhepikkused!");
				pp = sc.nextLine();
				continue; // alustame otsast
			}
			this.dCols = row.length - 1; // -1 sest, viimast veergu me ei taha
			cramerList.add(new ArrayList<Double>());
			for (int i = 0; i < dCols; i++) {
				try {
					double s = digifier(row)[i];
					cramerList.get(rowCount).add(s);
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

	private StringBuilder[] tundmatuteLeidja(String[] rida) {
		StringBuilder[] tundmatuteList = new StringBuilder[rida.length - 1];
		for (int i = 0; i < rida.length; i++) {
			StringBuilder tundmatus = new StringBuilder();
			for (int k = 0; k < rida[i].length(); k++) {
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

	private double[] digifier(String[] s) {
		double[] uusRida = new double[s.length]; // siin massiivis hoiame kõiki
													// võrrandite kordajaid

		for (int k = 0; k < s.length; k++) {
			StringBuilder uus = new StringBuilder(s[k]); // s[k] on mingi veeru
															// üks element
															// näiteks võrrandi
															// 3x2 + 2x5 + 3x7
															// puhul võib see
															// olla näiteks 3x2,
															// oleneb k-st (kui
															// k = 1, siis s[1]
															// = 3x2).

			for (int i = 0; i < uus.length(); i++) { // tsükkel, mis käib läbi
														// stringi kõik
														// elemendid(charid)
				if (uus.indexOf("-") != i) { // kui leima miinuse, siis peame
												// teistmoodi otsima
					if (Character.isDigit(uus.charAt(i)) == false) { // kontrollime,
																		// kas
																		// element
																		// kohal
																		// i on
																		// number
						uus.delete(i, uus.length()); // eemaldame tundmatud
						break;
					}
				} else { // kui kordaja on negatiivne
					char[] jarjend = new char[s.length - i];
					StringBuilder miinus = new StringBuilder("-"); // Kuna - on
																	// String,
																	// siis peab
																	// selle
																	// tegema
																	// StringBuilderiks,
																	// ei saa
																	// panna
																	// char []
																	// jarjendisse.
					for (int r = 1; r < s.length - i; r++) {
						if (Character.isDigit(uus.charAt(r + i)) == true) { // nii
																			// kaua,
																			// kui
																			// element
																			// pole
																			// täht
																			// võtame
																			// numbreid
							jarjend[r - 1] = uus.charAt(r + i);
						} else {
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
			} catch (Exception e) {
				try {
					if (uus.indexOf("-") == 0) {
						uusRida[k] = -1;
					} else {
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
		for (ArrayList<Double> rida : this.cramerList) {
			if (rida.size() != x) {
				return false;
			}
		}
		return true;
	}

	public void value() {
		double algDet = new Determinant(this.cramerList).calculate_det();
		ArrayList<ArrayList<Double>> list = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < this.dRows; i++) {
			list.add(new ArrayList<Double>());
			for (int k = 0; k < this.dCols; k++) {
				list.get(i).add(this.cramerList.get(i).get(k));
			}
		}
		for (int i = 0; i < this.dCols; i++) {
			for (int k = 0; k < this.dRows; k++) {
				list.get(k).set(i, vastus.get(k));
			}
			Determinant c = new Determinant(list);
			System.out.println("Asendades " + this.tundmatud[i]
					+ "-de veeru vastustega saame " + c.calculate_det()
					+ ", seega on " + this.tundmatud[i] + " väärtus "
					+ (c.calculate_det() / algDet));

			for (int k = 0; k < this.dRows; k++) {
				list.get(k).set(i, this.cramerList.get(k).get(i));
			}
		}

	}
}
