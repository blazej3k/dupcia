package blake.przewodnikturystyczny.baza.model;

import java.util.ArrayList;

import com.orm.SugarRecord;
import com.orm.dsl.NotNull;
import com.orm.dsl.Unique;

public class TabOkres extends SugarRecord {
	// pojedyncze pola tabeli
	@Unique@NotNull
	private String nazwa;
	@Unique@NotNull
	private String rokPoczatek;
	@Unique@NotNull
	private String rokKoniec;
	private String opis;
	
	// relacje 1-wiele
	private ArrayList<TabBudynek> budynki;
	private ArrayList<TabRod> rody;
	private ArrayList<TabPostac> postacie;
	private ArrayList<TabWydarzenie> wydarzenia;
	
	public TabOkres() { }
	
	public TabOkres(String nazwa, String rokPoczatek, String rokKoniec,
			String opis, ArrayList<TabBudynek> budynki, ArrayList<TabRod> rody,
			ArrayList<TabPostac> postacie, ArrayList<TabWydarzenie> wydarzenia) {

		this.nazwa = nazwa;
		this.rokPoczatek = rokPoczatek;
		this.rokKoniec = rokKoniec;
		this.opis = opis;
		this.budynki = budynki;
		this.rody = rody;
		this.postacie = postacie;
		this.wydarzenia = wydarzenia;
	}
	
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	public String getRokPoczatek() {
		return rokPoczatek;
	}
	public void setRokPoczatek(String rokPoczatek) {
		this.rokPoczatek = rokPoczatek;
	}
	public String getRokKoniec() {
		return rokKoniec;
	}
	public void setRokKoniec(String rokKoniec) {
		this.rokKoniec = rokKoniec;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public ArrayList<TabBudynek> getBudynki() {
		return budynki;
	}
	public void setBudynki(ArrayList<TabBudynek> budynki) {
		this.budynki = budynki;
	}
	public ArrayList<TabRod> getRody() {
		return rody;
	}
	public void setRody(ArrayList<TabRod> rody) {
		this.rody = rody;
	}
	public ArrayList<TabPostac> getPostacie() {
		return postacie;
	}
	public void setPostacie(ArrayList<TabPostac> postacie) {
		this.postacie = postacie;
	}
	public ArrayList<TabWydarzenie> getWydarzenia() {
		return wydarzenia;
	}
	public void setWydarzenia(ArrayList<TabWydarzenie> wydarzenia) {
		this.wydarzenia = wydarzenia;
	}
}
