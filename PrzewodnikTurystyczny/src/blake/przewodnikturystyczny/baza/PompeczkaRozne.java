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
			Log.d(DEBUG_TAG, "Nie by�o budynk�w z brakuj�cymi wsp�rz�dnymi.");
		else
			Log.d(DEBUG_TAG, "Pobierano wsp�rz�dne dla "+ile+" budynk�w.");
		
	}
	
	private long pompujBudynek() {	// zwraca -1 je�li nie uda�o si� doda�! a id dodanego je�li uda�o.
		long dodanyId=0;
		TabBudynek budynek;
		
		TabOkres okres =  new Select().from(TabOkres.class).where("nazwa = ?", "XVII").executeSingle(); // znajdz okres dla budynku
		budynek = new TabBudynek("pa�ac �azienkowski", "ul. Agrykola 1, 00-460 Warszawa", "Dominik Merlini", "1689", "Pa�ac Na Wyspie, pa�ac Na Wodzie, pa�ac �azienkowski, dawniej przed przebudow� XVIII w., nazywany: �azienk� Lubomirskiego, Hippokrene � g��wny kompleks architektoniczny w parku �azienkowskim. Zbudowany wed�ug projektu Tylmana z Gameren w 1683-1689 dla marsza�ka wielkiego koronnego Stanis�awa Herakliusza Lubomirskiego, przebudowany w 1788-1793 przez Dominika Merliniego i Jana Chrystiana Kamsetzera dla kr�la Stanis�awa Augusta Poniatowskiego[1].");
		budynek.setOkres(okres);
		dodanyId = budynek.save(); //zapisz budynek do bazy
		Log.d(DEBUG_TAG, "Budynek dodany ID="+dodanyId);
		
		TabOkres x = new Select().from(TabOkres.class).where("nazwa = ?", "XVII").executeSingle();	// select po Okresie XVII i wypis
		Log.d(DEBUG_TAG, 
				"Ilo�� budynk�w przypisanych do okresu: "+x.budynki().size()+"\n"+
				"adres pierwszego to: "+x.budynki().get(0).getAdres()+"\n"+
				"okres pierwszego to: "+x.budynki().get(0).getOkres().getNazwa()+"\n"+
				"ID pierwszego to: "+x.budynki().get(0).getId());
		
		return dodanyId;
	}
	
	private int znajdzWspolrzedneBudynek() {
//		List<TabBudynek> budynki = new Select().from(TabBudynek.class).where("latitude = ?", "0").where("longitude = ?", "0").execute();
		List<TabBudynek> budynki = new Select().from(TabBudynek.class).where("latitude = 0 AND longitude = 0").execute(); // oba selecty s� poprawne, ten jest bardziej adekwatny, bo sta�e warto�ci s� istotne

		if(budynki != null && budynki.size() > 0) {
			for(TabBudynek x: budynki) {
				rozpatrywanyBudynek = x; 											// przechwytuje to potem funkcja jestPozycja i aktualizuje
				new PobierzWspolrzedneAsync(context, this).execute(x.getAdres());
			}
			return budynki.size(); // tyle budynk�w nie mia�o wsp�rz�dnych - tyle przetwarzano
		}
		else
			return -1; // nie znaleziono budynku z brakuj�cymi wsp�rz�dnymi
	}

	@Override
	public void jestPozycja(String pozycja) {
		if(Character.isDigit(pozycja.charAt(0))) { // sprawdza czy mamy wsp�rz�dne czy tekst b��du, jak b��d to nic nie r�b
			String[] pozycjaS;
			pozycjaS = pozycja.split(",");
			
			rozpatrywanyBudynek.setLatitude(Double.parseDouble(pozycjaS[0]));
			rozpatrywanyBudynek.setLongitude(Double.parseDouble(pozycjaS[1]));
			rozpatrywanyBudynek.save();
			
			Log.d(DEBUG_TAG, "Budynek nazwa="+rozpatrywanyBudynek.getNazwa()+", nowe wsp�rz�dne to szer.: "+rozpatrywanyBudynek.getLatitude()+", d�.: "+rozpatrywanyBudynek.getLongitude());
		}		
	}
}
