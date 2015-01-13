package blake.przewodnikturystyczny.baza.model;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Column.ConflictAction;
import com.activeandroid.annotation.Table;

@Table(name = "Budynek")
public class TabBudynek extends Model implements IfMarkierable, IfLocalizable, IfSelectable {
	// pojedyncze pola tabeli
	@Column(name = "nazwa", unique=true, notNull=true)
	private String nazwa;
	@Column(name = "adres", unique=true, notNull=true)
	private String adres;
	@Column(name = "latitude", notNull=true, onNullConflict=ConflictAction.REPLACE)
	private double latitude;
	@Column(name = "longitude", notNull=true)
	private double longitude;
	@Column(name = "projektant")
	private String projektant;
	@Column(name = "dataPowstania")
	private String dataPowstania;
	@Column(name = "opis")
	private String opis;

	// relacje 1-1
	@Column(name = "Okres_ID", notNull=true)
	private TabOkres okres;

	// relacje 1-wiele
	public List<TabMiejsce> getMiejsca() {
		return getMany(TabMiejsce.class, "Budynek_ID");
	}
	
    public List<TabRzecz> getRzeczy() {
        return getMany(TabRzecz.class, "Budynek_ID");
    }
    
    public List<TabPostac> getPostacie() {
        return getMany(TabPostac.class, "Budynek_ID");
    }
    
    public List<TabRod> getRody() {
        return getMany(TabRod.class, "Budynek_ID");
    }

	public TabBudynek() {
		super();
	}
	
	public TabBudynek(String nazwa, String adres, String projektant, String dataPowstania, String opis) {
		super();
		
		this.nazwa = nazwa;
		this.adres = adres; 
		this.projektant = projektant;
		this.dataPowstania = dataPowstania;
		this.opis = opis;
		
		this.latitude = 0;
		this.longitude = 0;
	}
	
	/* konstruktor w miejscu wspó³rzêdnych zawsze wstawia wartoœæ 0 - jakos symbol pustej, domyœlnej 
	 * w³aœciwe dodawane s¹ oddzielnie */
	


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

	public String getAdres() {
		return adres;
	}

	public void setAdres(String adres) {
		this.adres = adres;
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
