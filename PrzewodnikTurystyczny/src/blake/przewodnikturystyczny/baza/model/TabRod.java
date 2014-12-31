package blake.przewodnikturystyczny.baza.model;

import java.util.ArrayList;

import com.orm.SugarRecord;
import com.orm.dsl.NotNull;
import com.orm.dsl.Unique;

public class TabRod extends SugarRecord {
	// pojedyncze pola tabeli
	@Unique@NotNull
	private String nazwa;
	private String opis;
	
	// relacje 1-1
	@NotNull
	private TabOkres okres;
	
	// relacje 1-wiele
	private ArrayList<TabMiejsce> miejsca;
	private ArrayList<TabBudynek> budynek;
	private ArrayList<TabWydarzenie> wydarzenia;
	private ArrayList<TabRzecz> rzeczy;
	private ArrayList<TabPostac> postacie;
	
	public TabRod() { }

	public TabRod(String nazwa, String opis, TabOkres okres,
			ArrayList<TabMiejsce> miejsca, ArrayList<TabBudynek> budynek,
			ArrayList<TabWydarzenie> wydarzenia, ArrayList<TabRzecz> rzeczy,
			ArrayList<TabPostac> postacie) {

		this.nazwa = nazwa;
		this.opis = opis;
		this.okres = okres;
		this.miejsca = miejsca;
		this.budynek = budynek;
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

	public TabOkres getOkres() {
		return okres;
	}

	public void setOkres(TabOkres okres) {
		this.okres = okres;
	}

	public ArrayList<TabMiejsce> getMiejsca() {
		return miejsca;
	}

	public void setMiejsca(ArrayList<TabMiejsce> miejsca) {
		this.miejsca = miejsca;
	}

	public ArrayList<TabBudynek> getBudynek() {
		return budynek;
	}

	public void setBudynek(ArrayList<TabBudynek> budynek) {
		this.budynek = budynek;
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
