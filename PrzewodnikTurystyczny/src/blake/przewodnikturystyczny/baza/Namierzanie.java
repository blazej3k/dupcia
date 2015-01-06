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
		//		List<TabBudynek> budynki = new Select().from(TabBudynek.class).where("latitude = ?", "0").where("longitude = ?", "0").execute();
		List<TabBudynek> budynki = new Select().from(TabBudynek.class).where("latitude = 0 AND longitude = 0").execute(); // oba selecty s¹ poprawne, ten jest bardziej adekwatny, bo sta³e wartoœci s¹ istotne


		if(budynki != null && budynki.size() > 0) {
			new PobierzWspolrzedneAsync<TabBudynek>(context, this, budynki, true).execute(new String());		// new String, jako pusty argument
			return budynki.size(); // tyle budynków nie mia³o wspó³rzêdnych - tyle przetwarzano
		}
		else
			return -1; // nie znaleziono budynku z brakuj¹cymi wspó³rzêdnymi
	}

	private int znajdzWspolrzedneMiejsce() {
		List<TabMiejsce> miejsca = new Select().from(TabMiejsce.class).where("latitude = 0 AND longitude = 0").execute();

		if(miejsca != null && miejsca.size() > 0) {
			new PobierzWspolrzedneAsync<TabMiejsce>(context, this, miejsca, true).execute(new String());
			return miejsca.size(); // tyle miejsc nie mia³o wspó³rzêdnych - tyle przetwarzano
		}
		else 	
			return -1; // nie znaleziono miejsca z brakuj¹cymi wspó³rzêdnymi
	}

	@Override
	public void jestPozycja(String wynik) {
		Log.d(DEBUG_TAG, "Namierzanie, wynik: " + wynik);
	}
}
