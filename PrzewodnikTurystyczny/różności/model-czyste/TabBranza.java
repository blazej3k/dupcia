package blake.przewodnikturystyczny.baza.model;

import java.util.ArrayList;

public class TabBranza {
	// pojedyncze pola tabeli
	private String nazwa;
	private String opis;
	
	// relacje 1-wiele
	private ArrayList<TabMiejsce> miejsca;
	private ArrayList<TabWydarzenie> wydarzenia;
	private ArrayList<TabRzecz> rzeczy;
	private ArrayList<TabPostac> postacie;
	
	public TabBranza() { }

	public TabBranza(String nazwa, String opis, ArrayList<TabMiejsce> miejsca,
			ArrayList<TabWydarzenie> wydarzenia, ArrayList<TabRzecz> rzeczy,
			ArrayList<TabPostac> postacie) {
		super();
		this.nazwa = nazwa;
		this.opis = opis;
		this.miejsca = miejsca;
		this.wydarzenia = wydarzenia;
		this.rzeczy = rzeczy;
		this.postacie = postacie;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public ArrayList<TabMiejsce> getMiejsca() {
		return miejsca;
	}

	public void setMiejsca(ArrayList<TabMiejsce> miejsca) {
		this.miejsca = miejsca;
	}

	public ArrayList<TabWydarzenie> getWydarzenia() {
		return wydarzenia;
	}

	public void setWydarzenia(ArrayList<TabWydarzenie> wydarzenia) {
		this.wydarzenia = wydarzenia;
	}

	public ArrayList<TabRzecz> getRzeczy() {
		return rzeczy;
	}

	public void setRzeczy(ArrayList<TabRzecz> rzeczy) {
		this.rzeczy = rzeczy;
	}

	public ArrayList<TabPostac> getPostacie() {
		return postacie;
	}

	public void setPostacie(ArrayList<TabPostac> postacie) {
		this.postacie = postacie;
	}
}