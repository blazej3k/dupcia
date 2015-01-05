package blake.przewodnikturystyczny.baza.model;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="Postac")
public class TabPostac extends Model {
	// pojedyncze pola tabeli
	@Column(name="imie", unique=true, notNull=true)
	private String imie;
	@Column(name="nazwisko", unique=true, notNull=true)
	private String nazwisko;
	@Column(name="rokUrodzenia")
	private String rokUrodzenia;
	@Column(name="rokSmierci")
	private String rokSmierci;
	@Column(name="opis")
	private String opis;
	
	// relacje 1-1
	@Column(name="Okres_ID", notNull=true)
	private TabOkres okres;
	@Column(name="Branza_ID", notNull=true)
	private TabBranza branza;
	@Column(name="Rod_ID")
	private TabRod rod;
	
	// relacje 1-wiele
    public List<TabRzecz> rzeczy() {
        return getMany(TabRzecz.class, "Postac_ID");
    }
    
    public List<TabWydarzenie> wydarzenia() {
        return getMany(TabWydarzenie.class, "Postac_ID");
    }
    
    public List<TabBudynek> budynki() {
        return getMany(TabBudynek.class, "Postac_ID");
    }
    
    public List<TabMiejsce> miejsca() {
        return getMany(TabMiejsce.class, "Postac_ID");
    }
	
	public TabPostac() {
		super();
	}

	public TabPostac(String imie, String nazwisko, String rokUrodzenia,
			String rokSmierci, String opis, 
			TabOkres okres, TabBranza branza, TabRod rod)  {

		super();
		
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.rokUrodzenia = rokUrodzenia;
		this.rokSmierci = rokSmierci;
		this.opis = opis;
		this.okres = okres;
		this.branza = branza;
		this.rod = rod;
	}

	public String getImie() {
		return imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}

	public String getNazwisko() {
		return nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

	public String getRokUrodzenia() {
		return rokUrodzenia;
	}

	public void setRokUrodzenia(String rokUrodzenia) {
		this.rokUrodzenia = rokUrodzenia;
	}

	public String getRokSmierci() {
		return rokSmierci;
	}

	public void setRokSmierci(String rokSmierci) {
		this.rokSmierci = rokSmierci;
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

	public TabRod getRod() {
		return rod;
	}

	public void setRod(TabRod rod) {
		this.rod = rod;
	}
}
