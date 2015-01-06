package blake.przewodnikturystyczny.baza;

import android.util.Log;
import blake.przewodnikturystyczny.baza.model.TabBranza;
import blake.przewodnikturystyczny.baza.model.TabBudynek;
import blake.przewodnikturystyczny.baza.model.TabOkres;

import com.activeandroid.query.Select;

public class PompeczkaRozne {
	
	public static final String DEBUG_TAG = "Przewodnik";
	
	TabOkres okres;
	TabBranza branza;
	
	public PompeczkaRozne() {
				
		pompujBudynek();
	}
	
	private long pompujBudynek() {	// zwraca -1 je�li nie uda�o si� doda�! a id dodanego je�li uda�o.
		long dodanyId=0;
		TabBudynek budynek;
		TabOkres okres;
		
		okres =  new Select().from(TabOkres.class).where("nazwa = ?", "XVII").executeSingle(); // znajdz okres dla budynku
		budynek = new TabBudynek("pa�ac �azienkowski", "ul. Agrykola 1, 00-460 Warszawa", "Dominik Merlini", "1689", "Pa�ac Na Wyspie, pa�ac Na Wodzie, pa�ac �azienkowski, dawniej przed przebudow� XVIII w., nazywany: �azienk� Lubomirskiego, Hippokrene � g��wny kompleks architektoniczny w parku �azienkowskim. Zbudowany wed�ug projektu Tylmana z Gameren w 1683-1689 dla marsza�ka wielkiego koronnego Stanis�awa Herakliusza Lubomirskiego, przebudowany w 1788-1793 przez Dominika Merliniego i Jana Chrystiana Kamsetzera dla kr�la Stanis�awa Augusta Poniatowskiego[1].");
		budynek.setOkres(okres);
		dodanyId = budynek.save(); 							// zapisz budynek do bazy
		Log.d(DEBUG_TAG, "Budynek dodany ID="+dodanyId);	// zwraca -1 je�li nie uda�o si� doda�! a id dodanego je�li uda�o.
		
		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1940").executeSingle(); 
		budynek = new TabBudynek("DS Ustronie", "Ksi�cia Janusza 39, 01-452 Warszawa", "Pijany Student", "1945", "Najlepszy akademik w Warszawie, du�o imprez, cyck�w i w�dki.");
		budynek.setOkres(okres);
		dodanyId = budynek.save(); 							
		Log.d(DEBUG_TAG, "Budynek dodany ID="+dodanyId);	
		
		
		TabOkres x = new Select().from(TabOkres.class).where("nazwa = ?", "XVII").executeSingle();	// select po Okresie XVII i wypis
		Log.d(DEBUG_TAG, 
				"Ilo�� budynk�w przypisanych do okresu: "+x.budynki().size()+"\n"+
				"adres pierwszego to: "+x.budynki().get(0).getAdres()+"\n"+
				"okres pierwszego to: "+x.budynki().get(0).getOkres().getNazwa()+"\n"+
				"ID pierwszego to: "+x.budynki().get(0).getId());
		
		return dodanyId;
	}
}
