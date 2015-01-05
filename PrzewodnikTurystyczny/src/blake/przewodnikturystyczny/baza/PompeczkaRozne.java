package blake.przewodnikturystyczny.baza;

import java.util.List;

import android.content.Context;
import android.util.Log;
import blake.przewodnikturystyczny.baza.model.TabBranza;
import blake.przewodnikturystyczny.baza.model.TabBudynek;
import blake.przewodnikturystyczny.baza.model.TabOkres;
import blake.przewodnikturystyczny.mapa.PobierzWspolrzedneAsync;
import blake.przewodnikturystyczny.mapa.PobierzWspolrzedneAsync.AsyncWspolrzedneListener;

import com.activeandroid.query.Select;

public class PompeczkaRozne implements AsyncWspolrzedneListener {
	
	public static final String DEBUG_TAG = "Przewodnik";
	
	TabOkres okres;
	TabBranza branza;
	
	TabBudynek rozpatrywanyBudynek;
	
	private Context context;
	
	
	public PompeczkaRozne(Context context) {
		this.context = context;
		int ile;
		
//		pompujBudynek();
		
		ile = znajdzWspolrzedneBudynek();
		if(ile == -1)
			Log.d(DEBUG_TAG, "Nie by³o budynków z brakuj¹cymi wspó³rzêdnymi.");
		else
			Log.d(DEBUG_TAG, "Pobierano wspó³rzêdne dla "+ile+" budynków.");
		
	}
	
	private long pompujBudynek() {	// zwraca -1 jeœli nie uda³o siê dodaæ! a id dodanego jeœli uda³o.
		long dodanyId=0;
		TabBudynek budynek;
		
		TabOkres okres =  new Select().from(TabOkres.class).where("nazwa = ?", "XVII").executeSingle(); // znajdz okres dla budynku
		budynek = new TabBudynek("pa³ac £azienkowski", "ul. Agrykola 1, 00-460 Warszawa", "Dominik Merlini", "1689", "Pa³ac Na Wyspie, pa³ac Na Wodzie, pa³ac £azienkowski, dawniej przed przebudow¹ XVIII w., nazywany: £azienk¹ Lubomirskiego, Hippokrene – g³ówny kompleks architektoniczny w parku £azienkowskim. Zbudowany wed³ug projektu Tylmana z Gameren w 1683-1689 dla marsza³ka wielkiego koronnego Stanis³awa Herakliusza Lubomirskiego, przebudowany w 1788-1793 przez Dominika Merliniego i Jana Chrystiana Kamsetzera dla króla Stanis³awa Augusta Poniatowskiego[1].");
		budynek.setOkres(okres);
		dodanyId = budynek.save(); //zapisz budynek do bazy
		Log.d(DEBUG_TAG, "Budynek dodany ID="+dodanyId);
		
		TabOkres x = new Select().from(TabOkres.class).where("nazwa = ?", "XVII").executeSingle();	// select po Okresie XVII i wypis
		Log.d(DEBUG_TAG, 
				"Iloœæ budynków przypisanych do okresu: "+x.budynki().size()+"\n"+
				"adres pierwszego to: "+x.budynki().get(0).getAdres()+"\n"+
				"okres pierwszego to: "+x.budynki().get(0).getOkres().getNazwa()+"\n"+
				"ID pierwszego to: "+x.budynki().get(0).getId());
		
		return dodanyId;
	}
	
	private int znajdzWspolrzedneBudynek() {
//		List<TabBudynek> budynki = new Select().from(TabBudynek.class).where("latitude = ?", "0").where("longitude = ?", "0").execute();
		List<TabBudynek> budynki = new Select().from(TabBudynek.class).where("latitude = 0 AND longitude = 0").execute(); // oba selecty s¹ poprawne, ten jest bardziej adekwatny, bo sta³e wartoœci s¹ istotne

		if(budynki != null && budynki.size() > 0) {
			for(TabBudynek x: budynki) {
				rozpatrywanyBudynek = x; 											// przechwytuje to potem funkcja jestPozycja i aktualizuje
				new PobierzWspolrzedneAsync(context, this).execute(x.getAdres());
			}
			return budynki.size(); // tyle budynków nie mia³o wspó³rzêdnych - tyle przetwarzano
		}
		else
			return -1; // nie znaleziono budynku z brakuj¹cymi wspó³rzêdnymi
	}

	@Override
	public void jestPozycja(String pozycja) {
		if(Character.isDigit(pozycja.charAt(0))) { // sprawdza czy mamy wspó³rzêdne czy tekst b³êdu, jak b³¹d to nic nie rób
			String[] pozycjaS;
			pozycjaS = pozycja.split(",");
			
			rozpatrywanyBudynek.setLatitude(Double.parseDouble(pozycjaS[0]));
			rozpatrywanyBudynek.setLongitude(Double.parseDouble(pozycjaS[1]));
			rozpatrywanyBudynek.save();
			
			Log.d(DEBUG_TAG, "Budynek nazwa="+rozpatrywanyBudynek.getNazwa()+", nowe wspó³rzêdne to szer.: "+rozpatrywanyBudynek.getLatitude()+", d³.: "+rozpatrywanyBudynek.getLongitude());
		}		
	}
}
