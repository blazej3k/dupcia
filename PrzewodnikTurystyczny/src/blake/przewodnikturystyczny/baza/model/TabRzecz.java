package blake.przewodnikturystyczny.baza.model;

import java.util.ArrayList;

import com.orm.SugarRecord;
import com.orm.dsl.NotNull;
import com.orm.dsl.Unique;

public class TabRzecz extends SugarRecord {
	// pojedyncze pola tabeli
	@Unique@NotNull
	private String nazwa;
	private String dataPowstania;
	private String rodzaj;
	private String opis;
	
	// relacje 1-1
	private TabBudynek budynek;
	@NotNull
	private TabBranza branza;
	@NotNull
	private TabOkres okres; // tej relacji brak w modelu, cza uzupelnic
	
	// relacje 1-wiele
	private ArrayList<TabWydarzenie> wydarzenia; // wg modelu tu powinno byc 1-1, ale uwazam ze tak jest lepiej
	private ArrayList<TabMiejsce> miejsca;
	private ArrayList<TabRod> rody; // tu tez niby 1-1
	private ArrayList<TabPostac> postacie; // tu rowniez, dziwne.
	
	public TabRzecz() { }

	public TabRzecz(String nazwa, String dataPowstania, String rodzaj,
			String opis, TabBudynek budynek, TabBranza branza, TabOkres okres,
			ArrayList<TabWydarzenie> wydarzenia, ArrayList<TabMiejsce> miejsca,
			ArrayList<TabRod> rody, ArrayList<TabPostac> postacie) {

		this.nazwa = nazwa;
		this.dataPowstania = dataPowstania;
		this.rodzaj = rodzaj;
		this.opis = opis;
		this.budynek = budynek;
		this.branza = branza;
		this.okres = okres;
		this.wydarzenia = wydarzenia;
		this.miejsca = miejsca;
		this.rody = rody;
		this.postacie = postacie;
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

	public String getRodzaj() {
		return rodzaj;
	}

	public void setRodzaj(String rodzaj) {
		this.rodzaj = rodzaj;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
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

	public TabOkres getOkres() {
		return okres;
	}

	public void setOkres(TabOkres okres) {
		this.okres = okres;
	}

	public ArrayList<TabWydarzenie> getWydarzenia() {
		return wydarzenia;
	}

	public void setWydarzenia(ArrayList<TabWydarzenie> wydarzenia) {
		this.wydarzenia = wydarzenia;
	}

	public ArrayList<TabMiejsce> getMiejsca() {
		return miejsca;
	}

	public void setMiejsca(ArrayList<TabMiejsce> miejsca) {
		this.miejsca = miejsca;
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
}
