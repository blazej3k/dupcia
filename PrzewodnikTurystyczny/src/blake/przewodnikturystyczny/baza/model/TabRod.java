package blake.przewodnikturystyczny.baza.model;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="Rod")
public class TabRod extends Model implements IfSelectable {
	// pojedyncze pola tabeli
	@Column(name="nazwa", unique=true, notNull=true)
	private String nazwa;
	@Column(name="opis")
	private String opis;
	
	// relacje 1-1
	@Column(name="Okres_ID", notNull=true)
	private TabOkres okres;
	
	// relacje 1-wiele
    public List<TabMiejsce> miejsca() {
        return getMany(TabMiejsce.class, "Rod_ID");
    }
    
    public List<TabBudynek> budynek() {
        return getMany(TabBudynek.class, "Rod_ID");
    }
    
    public List<TabWydarzenie> wydarzenia() {
        return getMany(TabWydarzenie.class, "Rod_ID");
    }
    
    public List<TabRzecz> rzeczy() {
        return getMany(TabRzecz.class, "Rod_ID");
    }
    
    public List<TabPostac> postacie() {
        return getMany(TabPostac.class, "Rod_ID");
    }
    
	public TabRod() {
		super();
	}

	public TabRod(String nazwa, String opis, 
			TabOkres okres) {
		
		super();

		this.nazwa = nazwa;
		this.opis = opis;
		this.okres = okres;
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
}
