package blake.przewodnikturystyczny.baza;

import android.util.Log;
import blake.przewodnikturystyczny.activity.MainActivity;
import blake.przewodnikturystyczny.baza.model.TabBranza;
import blake.przewodnikturystyczny.baza.model.TabBudynek;
import blake.przewodnikturystyczny.baza.model.TabOkres;

import com.activeandroid.query.Select;

public class PompeczkaRozne {
	TabOkres okres;
	TabBranza branza;
	
	TabBudynek budynek;
	
	
	public PompeczkaRozne() {
		TabOkres okres =  new Select().from(TabOkres.class).where("nazwa = ?", "XVII").executeSingle();
		
		budynek = new TabBudynek("pa�ac �azienkowski", "warszawa", "Dominik Merlini", "1689", "Pa�ac Na Wyspie, pa�ac Na Wodzie, pa�ac �azienkowski, dawniej przed przebudow� XVIII w., nazywany: �azienk� Lubomirskiego, Hippokrene � g��wny kompleks architektoniczny w parku �azienkowskim. Zbudowany wed�ug projektu Tylmana z Gameren w 1683-1689 dla marsza�ka wielkiego koronnego Stanis�awa Herakliusza Lubomirskiego, przebudowany w 1788-1793 przez Dominika Merliniego i Jana Chrystiana Kamsetzera dla kr�la Stanis�awa Augusta Poniatowskiego[1].");
		budynek.setOkres(okres);
		
		budynek.save();
		
		TabBudynek nowy = new Select().from(TabBudynek.class).where("id = ?", "1").executeSingle();
		
		Log.d(MainActivity.DEBUG_TAG, "Okres: "+nowy.getOkres().getNazwa());
		TabOkres x = new Select().from(TabOkres.class).where("nazwa = ?", "XVII").executeSingle();
		Log.d(MainActivity.DEBUG_TAG, "Ilo�� budynk�w przypisanych do okresu: "+x.budynki().size()+"adres pierwszego to: "+x.budynki().get(0).getAdres());
	}
}
