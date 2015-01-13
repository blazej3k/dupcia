package blake.przewodnikturystyczny.baza.model;

import java.util.List;

import blake.przewodnikturystyczny.baza.model.pomocniczy.TabMiejsceRzecz;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="Rzecz")
public class TabRzecz extends Model implements IfSelectable {
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
	@Column(name="Miejsce_ID")
	private TabMiejsce miejsce;
	@Column(name="Branza_ID", notNull=true)
	private TabBranza branza;
	@Column(name="Okres_ID", notNull=true)
	private TabOkres okres; // tej relacji brak w modelu, cza uzupelnic
	
	// relacje 1-wiele
    public List<TabWydarzenie> wydarzenia() { 			
        return getMany(TabWydarzenie.class, "Rzecz_ID");
    }
    
    public List<TabMiejsce> getMiejsca() {
    	return getRelacje(TabMiejsce.class, TabMiejsceRzecz.class, "Rzecz_ID", "Miejsce_ID");
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
			String opis, TabBranza branza, TabOkres okres) {

		super();
		
		this.nazwa = nazwa;
		this.dataPowstania = dataPowstania;
		this.rodzaj = rodzaj;
		this.opis = opis;
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

	public TabMiejsce getMiejsce() {
		return miejsce;
	}

	public void setMiejsce(TabMiejsce miejsce) {
		this.miejsce = miejsce;
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
