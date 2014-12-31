package blake.przewodnikturystyczny.baza.model;

import java.util.ArrayList;

import com.orm.SugarRecord;
import com.orm.dsl.NotNull;
import com.orm.dsl.Unique;

public class TabBudynek extends SugarRecord {
	// pojedyncze pola tabeli
	@Unique@NotNull
	private String nazwa;
	@Unique@NotNull
	private String adres;
	private String projektant;
	private String dataPowstania;
	private String opis;

	// relacje 1-1
	@NotNull
	private TabOkres okres;
	private TabMiejsce miejsce;

	// relacje 1-wiele
	private ArrayList<TabRzecz> rzeczy;
	private ArrayList<TabPostac> postacie;
	private ArrayList<TabRod> rody;

	public TabBudynek() { }
	
	public TabBudynek(String nazwa, String projektant, String dataPowstania, String opis, TabOkres okres, TabMiejsce miejsce, ArrayList<TabRzecz> rzeczy, 
			ArrayList<TabPostac> postacie, ArrayList<TabRod> rody) {

		this.nazwa = nazwa;
		this.projektant = projektant;
		this.dataPowstania = dataPowstania;
		this.opis = opis;
		this.okres = okres;
		this.miejsce = miejsce;
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

	public String getProjektant() {
		return projektant;
	}

	public void setProjektant(String projektant) {
		this.projektant = projektant;
	}

	public String getDataPowstania() {
		return dataPowstania;
	}

	public void setDataPowstania(String dataPowstania) {
		this.dataPowstania = dataPowstania;
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

	public TabMiejsce getMiejsce() {
		return miejsce;
	}

	public void setMiejsce(TabMiejsce miejsce) {
		this.miejsce = miejsce;
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

	public String getAdres() {
		return adres;
	}

	public void setAdres(String adres) {
		this.adres = adres;
	}

}
