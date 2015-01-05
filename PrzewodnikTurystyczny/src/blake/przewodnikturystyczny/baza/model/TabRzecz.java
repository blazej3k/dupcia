package blake.przewodnikturystyczny.baza.model;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="Rzecz")
public class TabRzecz extends Model {
	// pojedyncze pola tabeli
	@Column(name="nazwa", unique=true, notNull=true)
	private String nazwa;
	@Column(name="dataPowstania")
	private String dataPowstania;
	@Column(name="rodzaj")
	private String rodzaj;
	@Column(name="opis")
	private String opis;
	
	// relacje 1-1
	@Column(name="Budynek_ID")
	private TabBudynek budynek;
	@Column(name="Branza_ID", notNull=true)
	private TabBranza branza;
	@Column(name="Okres_ID", notNull=true)
	private TabOkres okres; // tej relacji brak w modelu, cza uzupelnic
	
	// relacje 1-wiele
    public List<TabWydarzenie> wydarzenia() { 			// wg modelu tu powinno byc 1-1, ale uwazam ze tak jest lepiej
        return getMany(TabWydarzenie.class, "Rzecz_ID");
    }
    
    public List<TabMiejsce> miejsca() {
        return getMany(TabMiejsce.class, "Rzecz_ID");
    }
    
    public List<TabRod> rody() {						// tu tez niby 1-1
        return getMany(TabRod.class, "Rzecz_ID");
    }
    
    public List<TabPostac> postacie() {					// tu rowniez, dziwne.
        return getMany(TabPostac.class, "Rzecz_ID");
    }
	
	public TabRzecz() {
		super();
	}

	public TabRzecz(String nazwa, String dataPowstania, String rodzaj,
			String opis, TabBudynek budynek, TabBranza branza, TabOkres okres) {

		super();
		
		this.nazwa = nazwa;
		this.dataPowstania = dataPowstania;
		this.rodzaj = rodzaj;
		this.opis = opis;
		this.budynek = budynek;
		this.branza = branza;
		this.okres = okres;
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
}
