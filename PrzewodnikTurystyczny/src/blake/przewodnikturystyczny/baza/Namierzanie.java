package blake.przewodnikturystyczny.baza;

import java.util.List;

import com.activeandroid.query.Select;

import android.content.Context;
import android.util.Log;
import blake.przewodnikturystyczny.baza.model.TabBudynek;
import blake.przewodnikturystyczny.baza.model.TabMiejsce;
import blake.przewodnikturystyczny.mapa.PobierzWspolrzedneAsync;
import blake.przewodnikturystyczny.mapa.PobierzWspolrzedneAsync.AsyncWspolrzedneListener;

public class Namierzanie implements AsyncWspolrzedneListener {

	public static final String DEBUG_TAG = "Przewodnik";
	
	TabBudynek rozpatrywanyBudynek;
	TabMiejsce rozpatrywaneMiejsce;
	
	Context context;
	
	private Boolean czyBudynekNamierzam;	// zmienna automatycznie ustawiana przez funkcjê, która z niej korzysta
	
	public Namierzanie(Context context, Boolean czyBudynek) {
		this.context = context;
		int ile;
		
		if(czyBudynek) {
			ile = znajdzWspolrzedneBudynek();
			if(ile == -1)
				Log.d(DEBUG_TAG, "Nie by³o budynków z brakuj¹cymi wspó³rzêdnymi.");
			else
				Log.d(DEBUG_TAG, "Pobierano wspó³rzêdne dla "+ile+" budynków.");
		}
		else {
			ile = znajdzWspolrzedneMiejsce();	// ponowne u¿ycie zmiennej
			if(ile == -1)
				Log.d(DEBUG_TAG, "Nie by³o miejsc z brakuj¹cymi wspó³rzêdnymi.");
			else
				Log.d(DEBUG_TAG, "Pobierano wspó³rzêdne dla "+ile+" miejsc.");
		}
	}
	
	private int znajdzWspolrzedneBudynek() {
		czyBudynekNamierzam = true;				// ustawienie, ¿e Budynek, sygna³ dla jestPozycja jaki obiekt ma updateo'waæ.
		
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
	
	private int znajdzWspolrzedneMiejsce() {
		czyBudynekNamierzam = false;				// ustawienie, ¿e Miejsce, sygna³ dla jestPozycja jaki obiekt ma updateo'waæ.
		
		List<TabMiejsce> miejsca = new Select().from(TabMiejsce.class).where("latitude = 0 AND longitude = 0").execute();

		if(miejsca != null && miejsca.size() > 0) {
			for(TabMiejsce x: miejsca) {
				rozpatrywaneMiejsce = x; 											// przechwytuje to potem funkcja jestPozycja i aktualizuje
				new PobierzWspolrzedneAsync(context, this).execute(x.getAdres());
			}
			return miejsca.size(); // tyle miejsc nie mia³o wspó³rzêdnych - tyle przetwarzano
		}
		else
			return -1; // nie znaleziono budynku z brakuj¹cymi wspó³rzêdnymi
	}

	@Override
	public void jestPozycja(String pozycja) {
		if(Character.isDigit(pozycja.charAt(0))) { 	// sprawdza czy mamy wspó³rzêdne czy tekst b³êdu, jak b³¹d to nic nie rób
			String[] pozycjaS;
			pozycjaS = pozycja.split(",");
			
			if(czyBudynekNamierzam) {
				rozpatrywanyBudynek.setLatitude(Double.parseDouble(pozycjaS[0]));
				rozpatrywanyBudynek.setLongitude(Double.parseDouble(pozycjaS[1]));
				rozpatrywanyBudynek.save();
				
				Log.d(DEBUG_TAG, "Budynek nazwa="+rozpatrywanyBudynek.getNazwa()+", nowe wspó³rzêdne to szer.: "+rozpatrywanyBudynek.getLatitude()+", d³.: "+rozpatrywanyBudynek.getLongitude());
			}
			else if (!czyBudynekNamierzam) {
				rozpatrywaneMiejsce.setLatitude(Double.parseDouble(pozycjaS[0]));
				rozpatrywaneMiejsce.setLongitude(Double.parseDouble(pozycjaS[1]));
				rozpatrywaneMiejsce.save();
				
				Log.d(DEBUG_TAG, "Miejsce nazwa="+rozpatrywaneMiejsce.getNazwa()+", nowe wspó³rzêdne to szer.: "+rozpatrywaneMiejsce.getLatitude()+", d³.: "+rozpatrywaneMiejsce.getLongitude());
			}
		}		
	}
}
