package blake.przewodnikturystyczny.baza.model;

import java.util.List;

import blake.przewodnikturystyczny.baza.model.pomocniczy.TabMiejsceWydarzenie;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="Wydarzenie")
public class TabWydarzenie extends Model implements IfSelectable {
	// pojedyncze pola tabeli
	@Column(name="nazwa", unique=true, notNull=true)
	private String nazwa;
	@Column(name="dataPoczatek", notNull=true)
	private String dataPoczatek;
	@Column(name="dataKoniec")
	private String dataKoniec;
	@Column(name="opis")
	private String opis;
	
	// relacje 1-1
	@Column(name="Okres_ID", notNull=true)
	private TabOkres okres;
	@Column(name="Branza_ID", notNull=true)
	private TabBranza branza;
	
	// relacje 1-wiele


	public List<TabRod> rody() {
		return getMany(TabRod.class, "Wydarzenie_ID");
	}
	
	public List<TabPostac> postacie() {
		return getMany(TabPostac.class, "Wydarzenie_ID");
	}
	
	public List<TabRzecz> getRzeczy() {
		return getMany(TabRzecz.class, "Wydarzenie_ID");
	}

	// relacje wiele-wiele
	public List<TabMiejsce> getMiejsca() {
		return getRelacje(TabMiejsce.class, TabMiejsceWydarzenie.class, "Miejsce_ID", "Wydarzenie_ID");
	}
	
	public TabWydarzenie() {
		super();
	}

	public TabWydarzenie(String nazwa, String dataPoczatek, String dataKoniec,
			String opis, TabOkres okres, TabBranza branza) {

		super();
		
		this.nazwa = nazwa;
		this.dataPoczatek = dataPoczatek;
		this.dataKoniec = dataKoniec;
		this.opis = opis;
		this.okres = okres;
		this.branza = branza;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public String getDataPoczatek() {
		return dataPoczatek;
	}

	public void setDataPoczatek(String dataPoczatek) {
		this.dataPoczatek = dataPoczatek;
	}

	public String getDataKoniec() {
		return dataKoniec;
	}

	public void setDataKoniec(String dataKoniec) {
		this.dataKoniec = dataKoniec;
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
}
