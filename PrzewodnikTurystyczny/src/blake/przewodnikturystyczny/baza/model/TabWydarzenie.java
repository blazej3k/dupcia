package blake.przewodnikturystyczny.baza.model;

import java.util.ArrayList;

import com.orm.SugarRecord;
import com.orm.dsl.NotNull;
import com.orm.dsl.Unique;

public class TabWydarzenie extends SugarRecord {
	// pojedyncze pola tabeli
	@Unique@NotNull
	private String nazwa;
	@NotNull
	private String dataPoczatek;
	private String dataKoniec;
	private String opis;
	
	// relacje 1-1
	@NotNull
	private TabOkres okres;
	@NotNull
	private TabBranza branza;
	
	// relacje 1-wiele
	private ArrayList<TabMiejsce> miejsca;
	private ArrayList<TabRod> rody;
	private ArrayList<TabPostac> postacie;
	private ArrayList<TabRzecz> rzeczy;
	
	public TabWydarzenie() { }

	public TabWydarzenie(String nazwa, String dataPoczatek, String dataKoniec,
			String opis, TabOkres okres, TabBranza branza,
			ArrayList<TabMiejsce> miejsca, ArrayList<TabRod> rody,
			ArrayList<TabPostac> postacie, ArrayList<TabRzecz> rzeczy) {

		this.nazwa = nazwa;
		this.dataPoczatek = dataPoczatek;
		this.dataKoniec = dataKoniec;
		this.opis = opis;
		this.okres = okres;
		this.branza = branza;
		this.miejsca = miejsca;
		this.rody = rody;
		this.postacie = postacie;
		this.rzeczy = rzeczy;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public String getDataPoczatek() {
		return dataPoczatek;
	}

	public void setDataPoczatek(String dataPoczatek) {
		this.dataPoczatek = dataPoczatek;
	}

	public String getDataKoniec() {
		return dataKoniec;
	}

	public void setDataKoniec(String dataKoniec) {
		this.dataKoniec = dataKoniec;
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

	public ArrayList<TabRzecz> getRzeczy() {
		return rzeczy;
	}

	public void setRzeczy(ArrayList<TabRzecz> rzeczy) {
		this.rzeczy = rzeczy;
	}
}
