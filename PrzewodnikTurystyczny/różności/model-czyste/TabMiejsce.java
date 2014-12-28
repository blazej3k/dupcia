package blake.przewodnikturystyczny.baza.model;

import java.util.ArrayList;

public class TabMiejsce {
	// pojedyncze pola tabeli
	private String nazwa;
	private String dataPowstania;
	private String adres;
	private Boolean czyZespol;
	
	// relacje 1-1
	private TabBudynek budynek;
	private TabBranza branza;
	
	// relacje 1-wiele
	private ArrayList<TabWydarzenie> wydarzenia;
	private ArrayList<TabRzecz> rzeczy;
	private ArrayList<TabPostac> postacie;
	private ArrayList<TabRod> rody;
	
	public TabMiejsce() { }
	
	public TabMiejsce(String nazwa, String dataPowstania, String adres,
			Boolean czyZespol, TabBudynek budynek, TabBranza branza,
			ArrayList<TabWydarzenie> wydarzenia, ArrayList<TabRzecz> rzeczy,
			ArrayList<TabPostac> postacie, ArrayList<TabRod> rody) {

		this.nazwa = nazwa;
		this.dataPowstania = dataPowstania;
		this.adres = adres;
		this.czyZespol = czyZespol;
		this.budynek = budynek;
		this.branza = branza;
		this.wydarzenia = wydarzenia;
		this.rzeczy = rzeczy;
		this.postacie = postacie;
		this.rody = rody;
	}
	
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	public String getDataPowstania() {
		return dataPowstania;
	}
	public void setDataPowstania(String dataPowstania) {
		this.dataPowstania = dataPowstania;
	}
	public String getAdres() {
		return adres;
	}
	public void setAdres(String adres) {
		this.adres = adres;
	}
	public Boolean getCzyZespol() {
		return czyZespol;
	}
	public void setCzyZespol(Boolean czyZespol) {
		this.czyZespol = czyZespol;
	}
	public TabBudynek getBudynek() {
		return budynek;
	}
	public void setBudynek(TabBudynek budynek) {
		this.budynek = budynek;
	}
	public TabBranza getBranza() {
		return branza;
	}
	public void setBranza(TabBranza branza) {
		this.branza = branza;
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
	public ArrayList<TabRod> getRody() {
		return rody;
	}
	public void setRody(ArrayList<TabRod> rody) {
		this.rody = rody;
	}
	
}
