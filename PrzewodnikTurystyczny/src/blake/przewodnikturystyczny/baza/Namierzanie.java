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
	
	private Boolean czyBudynekNamierzam;	// zmienna automatycznie ustawiana przez funkcj�, kt�ra z niej korzysta
	
	public Namierzanie(Context context, Boolean czyBudynek) {
		this.context = context;
		int ile;
		
		if(czyBudynek) {
			ile = znajdzWspolrzedneBudynek();
			if(ile == -1)
				Log.d(DEBUG_TAG, "Nie by�o budynk�w z brakuj�cymi wsp�rz�dnymi.");
			else
				Log.d(DEBUG_TAG, "Pobierano wsp�rz�dne dla "+ile+" budynk�w.");
		}
		else {
			ile = znajdzWspolrzedneMiejsce();	// ponowne u�ycie zmiennej
			if(ile == -1)
				Log.d(DEBUG_TAG, "Nie by�o miejsc z brakuj�cymi wsp�rz�dnymi.");
			else
				Log.d(DEBUG_TAG, "Pobierano wsp�rz�dne dla "+ile+" miejsc.");
		}
	}
	
	private int znajdzWspolrzedneBudynek() {
		czyBudynekNamierzam = true;				// ustawienie, �e Budynek, sygna� dla jestPozycja jaki obiekt ma updateo'wa�.
		
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
	
	private int znajdzWspolrzedneMiejsce() {
		czyBudynekNamierzam = false;				// ustawienie, �e Miejsce, sygna� dla jestPozycja jaki obiekt ma updateo'wa�.
		
		List<TabMiejsce> miejsca = new Select().from(TabMiejsce.class).where("latitude = 0 AND longitude = 0").execute();

		if(miejsca != null && miejsca.size() > 0) {
			for(TabMiejsce x: miejsca) {
				rozpatrywaneMiejsce = x; 											// przechwytuje to potem funkcja jestPozycja i aktualizuje
				new PobierzWspolrzedneAsync(context, this).execute(x.getAdres());
			}
			return miejsca.size(); // tyle miejsc nie mia�o wsp�rz�dnych - tyle przetwarzano
		}
		else
			return -1; // nie znaleziono budynku z brakuj�cymi wsp�rz�dnymi
	}

	@Override
	public void jestPozycja(String pozycja) {
		if(Character.isDigit(pozycja.charAt(0))) { 	// sprawdza czy mamy wsp�rz�dne czy tekst b��du, jak b��d to nic nie r�b
			String[] pozycjaS;
			pozycjaS = pozycja.split(",");
			
			if(czyBudynekNamierzam) {
				rozpatrywanyBudynek.setLatitude(Double.parseDouble(pozycjaS[0]));
				rozpatrywanyBudynek.setLongitude(Double.parseDouble(pozycjaS[1]));
				rozpatrywanyBudynek.save();
				
				Log.d(DEBUG_TAG, "Budynek nazwa="+rozpatrywanyBudynek.getNazwa()+", nowe wsp�rz�dne to szer.: "+rozpatrywanyBudynek.getLatitude()+", d�.: "+rozpatrywanyBudynek.getLongitude());
			}
			else if (!czyBudynekNamierzam) {
				rozpatrywaneMiejsce.setLatitude(Double.parseDouble(pozycjaS[0]));
				rozpatrywaneMiejsce.setLongitude(Double.parseDouble(pozycjaS[1]));
				rozpatrywaneMiejsce.save();
				
				Log.d(DEBUG_TAG, "Miejsce nazwa="+rozpatrywaneMiejsce.getNazwa()+", nowe wsp�rz�dne to szer.: "+rozpatrywaneMiejsce.getLatitude()+", d�.: "+rozpatrywaneMiejsce.getLongitude());
			}
		}		
	}
}
