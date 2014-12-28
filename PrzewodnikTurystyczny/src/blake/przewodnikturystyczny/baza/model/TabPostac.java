package blake.przewodnikturystyczny.baza.model;

import java.util.ArrayList;

public class TabPostac {
	// pojedyncze pola tabeli
	private String imie;
	private String nazwisko;
	private String rokUrodzenia;
	private String rokSmierci;
	private String opis;
	
	// relacje 1-1
	private TabOkres okres;
	private TabBranza branza;
	private TabRod rod;
	
	// relacje 1-wiele
	private ArrayList<TabRzecz> rzeczy;
	private ArrayList<TabWydarzenie> wydarzenia;
	private ArrayList<TabBudynek> budynki;
	private ArrayList<TabMiejsce> miejsca;
	
	public TabPostac() { }

	public TabPostac(String imie, String nazwisko, String rokUrodzenia,
			String rokSmierci, String opis, TabOkres okres, TabBranza branza,
			TabRod rod, ArrayList<TabRzecz> rzeczy,
			ArrayList<TabWydarzenie> wydarzenia, ArrayList<TabBudynek> budynki,
			ArrayList<TabMiejsce> miejsca) {

		this.imie = imie;
		this.nazwisko = nazwisko;
		this.rokUrodzenia = rokUrodzenia;
		this.rokSmierci = rokSmierci;
		this.opis = opis;
		this.okres = okres;
		this.branza = branza;
		this.rod = rod;
		this.rzeczy = rzeczy;
		this.wydarzenia = wydarzenia;
		this.budynki = budynki;
		this.miejsca = miejsca;
	}

	public String getImie() {
		return imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}

	public String getNazwisko() {
		return nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

	public String getRokUrodzenia() {
		return rokUrodzenia;
	}

	public void setRokUrodzenia(String rokUrodzenia) {
		this.rokUrodzenia = rokUrodzenia;
	}

	public String getRokSmierci() {
		return rokSmierci;
	}

	public void setRokSmierci(String rokSmierci) {
		this.rokSmierci = rokSmierci;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public TabOkres getOkres() {
		return okres;
	}

	public void setOkres(TabOkres okres) {
		this.okres = okres;
	}

	public TabBranza getBranza() {
		return branza;
	}

	public void setBranza(TabBranza branza) {
		this.branza = branza;
	}

	public TabRod getRod() {
		return rod;
	}

	public void setRod(TabRod rod) {
		this.rod = rod;
	}

	public ArrayList<TabRzecz> getRzeczy() {
		return rzeczy;
	}

	public void setRzeczy(ArrayList<TabRzecz> rzeczy) {
		this.rzeczy = rzeczy;
	}

	public ArrayList<TabWydarzenie> getWydarzenia() {
		return wydarzenia;
	}

	public void setWydarzenia(ArrayList<TabWydarzenie> wydarzenia) {
		this.wydarzenia = wydarzenia;
	}

	public ArrayList<TabBudynek> getBudynki() {
		return budynki;
	}

	public void setBudynki(ArrayList<TabBudynek> budynki) {
		this.budynki = budynki;
	}

	public ArrayList<TabMiejsce> getMiejsca() {
		return miejsca;
	}

	public void setMiejsca(ArrayList<TabMiejsce> miejsca) {
		this.miejsca = miejsca;
	}
}
