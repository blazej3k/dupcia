package blake.przewodnikturystyczny.baza.model;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Column.ForeignKeyAction;
import com.activeandroid.annotation.Table;

@Table(name = "Okres")
public class TabOkres extends Model {
	// pojedyncze pola tabeli
	@Column(name = "nazwa", unique=true, notNull=true, onDelete=ForeignKeyAction.RESTRICT)
	private String nazwa;
	@Column(name = "rokPoczatek", unique=true, notNull=true)
	private String rokPoczatek;
	@Column(name = "rokKoniec", unique=true, notNull=true)
	private String rokKoniec;
	@Column(name = "opis")
	private String opis;
	
	// relacje 1-wiele
    public List<TabBudynek> budynki() {
        return getMany(TabBudynek.class, "Okres_ID");
    }
    
    public List<TabRod> rody() {
        return getMany(TabRod.class, "Okres_ID");
    }
    
    public List<TabPostac> postacie() {
        return getMany(TabPostac.class, "Okres_ID");
    }
    
    public List<TabWydarzenie> wydarzenia() {
        return getMany(TabWydarzenie.class, "Okres_ID");
    }
    
	public TabOkres() {
		super();
	}
	
	public TabOkres(String nazwa, String rokPoczatek, String rokKoniec,	String opis) {
		super();
		this.nazwa = nazwa;
		this.rokPoczatek = rokPoczatek;
		this.rokKoniec = rokKoniec;
		this.opis = opis;
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
}
