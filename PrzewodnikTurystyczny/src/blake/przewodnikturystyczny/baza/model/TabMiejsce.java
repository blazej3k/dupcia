package blake.przewodnikturystyczny.baza.model;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name="Miejsce")
public class TabMiejsce extends Model implements IfMarkierable {
	// pojedyncze pola tabeli
	@Column(name="nazwa", unique=true, notNull=true)
	private String nazwa;
	@Column(name="adres", unique=true, notNull=true)
	private String adres;
	@Column(name="dataPowstania")
	private String dataPowstania;
	@Column(name="projektant")
	private String projektant;
	@Column(name="opis")
	private String opis;
	@Column(name="latitude", notNull=true)
	private double latitude;
	@Column(name="longitude", notNull=true)
	private double longitude;
	@Column(name="czyZespol", notNull=true)
	private Boolean czyZespol;
	
	// relacje 1-1
	@Column(name="Okres_ID")
	private TabOkres okres;
	@Column(name="Budynek_ID")
	private TabBudynek budynek;
	@Column(name="Branza_ID", notNull=true)
	private TabBranza branza;
	
	// relacje 1-wiele
    public List<TabWydarzenie> wydarzenia() {
        return getMany(TabWydarzenie.class, "Miejsce_ID");
    }
    
    public List<TabRzecz> rzeczy() {
        return getMany(TabRzecz.class, "Miejsce_ID");
    }
    
    public List<TabPostac> postacie() {
        return getMany(TabPostac.class, "Miejsce_ID");
    }
    
    public List<TabRod> rody() {
        return getMany(TabRod.class, "Miejsce_ID");
    }
	
	public TabMiejsce() {
		super();
	}
	
	public TabMiejsce(String nazwa, String adres, Boolean czyZespol, String dataPowstania, String projektant, String opis) {
		this.nazwa = nazwa;
		this.adres = adres;
		this.czyZespol = czyZespol;
		this.dataPowstania = dataPowstania;
		this.projektant = projektant;
		this.opis = opis;
		
		this.latitude = 0;
		this.longitude = 0;
	}
	
	/* konstruktor w miejscu wspó³rzêdnych zawsze wstawia wartoœæ 0 - jakos symbol pustej, domyœlnej 
	 * w³aœciwe dodawane s¹ oddzielnie */
	public TabMiejsce(String nazwa, String adres, Boolean czyZespol, String dataPowstania, String projektant, String opis, 
			TabOkres okres, TabBudynek budynek, TabBranza branza) {

		super();
		
		this.nazwa = nazwa;
		this.adres = adres;
		this.czyZespol = czyZespol;
		this.dataPowstania = dataPowstania;
		this.projektant = projektant;
		this.opis = opis;
		this.okres = okres;
		this.budynek = budynek;
		this.branza = branza;
		
		this.latitude = 0;
		this.longitude = 0;
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
	public TabOkres getOkres() {
		return okres;
	}
	public void setOkres(TabOkres okres) {
		this.okres = okres;
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

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
}
