package blake.przewodnikturystyczny.baza.model;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="Branza")
public class TabBranza extends Model implements IfSelectable {
	// pojedyncze pola tabeli
	
	@Column(name="nazwa", unique=true, notNull=true)
	private String nazwa;
	@Column(name="opis")
	private String opis;
	
	// relacje 1-wiele
    public List<TabMiejsce> miejsca() {
        return getMany(TabMiejsce.class, "Branza_ID");
    }
    
    public List<TabWydarzenie> wydarzenia() {
        return getMany(TabWydarzenie.class, "Branza_ID");
    }
    
    public List<TabRzecz> rzeczy() {
        return getMany(TabRzecz.class, "Branza_ID");
    }
    
    public List<TabPostac> postacie() {
        return getMany(TabPostac.class, "Branza_ID");
    }
	
	public TabBranza() {
		super();
	}

	public TabBranza(String nazwa, String opis) {
		super();
		
		this.nazwa = nazwa;
		this.opis = opis;
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
}