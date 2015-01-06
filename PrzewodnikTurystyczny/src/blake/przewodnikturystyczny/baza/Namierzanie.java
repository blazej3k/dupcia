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

	Context context;

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
		//		List<TabBudynek> budynki = new Select().from(TabBudynek.class).where("latitude = ?", "0").where("longitude = ?", "0").execute();
		List<TabBudynek> budynki = new Select().from(TabBudynek.class).where("latitude = 0 AND longitude = 0").execute(); // oba selecty s� poprawne, ten jest bardziej adekwatny, bo sta�e warto�ci s� istotne


		if(budynki != null && budynki.size() > 0) {
			new PobierzWspolrzedneAsync<TabBudynek>(context, this, budynki, true).execute(new String());		// new String, jako pusty argument
			return budynki.size(); // tyle budynk�w nie mia�o wsp�rz�dnych - tyle przetwarzano
		}
		else
			return -1; // nie znaleziono budynku z brakuj�cymi wsp�rz�dnymi
	}

	private int znajdzWspolrzedneMiejsce() {
		List<TabMiejsce> miejsca = new Select().from(TabMiejsce.class).where("latitude = 0 AND longitude = 0").execute();

		if(miejsca != null && miejsca.size() > 0) {
			new PobierzWspolrzedneAsync<TabMiejsce>(context, this, miejsca, true).execute(new String());
			return miejsca.size(); // tyle miejsc nie mia�o wsp�rz�dnych - tyle przetwarzano
		}
		else 	
			return -1; // nie znaleziono miejsca z brakuj�cymi wsp�rz�dnymi
	}

	@Override
	public void jestPozycja(String wynik) {
		Log.d(DEBUG_TAG, "Namierzanie, wynik: " + wynik);
	}
}
