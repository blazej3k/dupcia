package blake.przewodnikturystyczny.baza;

import java.util.List;

import android.util.Log;
import blake.przewodnikturystyczny.activity.MainActivity;
import blake.przewodnikturystyczny.baza.model.TabBranza;
import blake.przewodnikturystyczny.baza.model.TabBudynek;
import blake.przewodnikturystyczny.baza.model.TabOkres;

public class PompeczkaRozne {
	TabOkres okres;
	TabBranza branza;
	
	TabBudynek budynek;
	
	
	public PompeczkaRozne() {
		List<TabOkres> okresy =  TabOkres.find(TabOkres.class, "nazwa = ?", "XVII");
		
		budynek = new TabBudynek("pa³ac £azienkowski", "warszawa", "Dominik Merlini", "1689", "Pa³ac Na Wyspie, pa³ac Na Wodzie, pa³ac £azienkowski, dawniej przed przebudow¹ XVIII w., nazywany: £azienk¹ Lubomirskiego, Hippokrene – g³ówny kompleks architektoniczny w parku £azienkowskim. Zbudowany wed³ug projektu Tylmana z Gameren w 1683-1689 dla marsza³ka wielkiego koronnego Stanis³awa Herakliusza Lubomirskiego, przebudowany w 1788-1793 przez Dominika Merliniego i Jana Chrystiana Kamsetzera dla króla Stanis³awa Augusta Poniatowskiego[1].",
				okresy.get(0), null, null, null, null);
		
		budynek.save();
		Log.d(MainActivity.DEBUG_TAG, "Okres: "+okresy.get(0).getNazwa());
	}
}
