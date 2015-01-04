package blake.przewodnikturystyczny.mapa;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;
import blake.przewodnikturystyczny.activity.MainActivity;
import blake.przewodnikturystyczny.activity.Mapa;

public class PobierzWspolrzedneAsync extends AsyncTask<String, Void, String> {
	/* Zwraca Stringa, wtedy ³atwiej jest przekazywaæ b³êdy na ekran, bez pierdzielenia z przemycaniem w LatLng,
	 * ³atwiej póŸniej przerobiæ String na LatLng
	 */

	private Context context;
	private AsyncWspolrzedneListener listener;
	
	public PobierzWspolrzedneAsync(Context context, AsyncWspolrzedneListener listener) {
		this.context = context;
		this.listener = listener;
	}
	
	@Override
	protected String doInBackground(String... adresy) {			// tu przez execute(pozycje) wchodzi lista adresów, metoda jest napisana, ¿e zawsze wchodzi 1 adres
		Geocoder geocoder = new Geocoder(context, Locale.getDefault());
		String adres = adresy[0]; 								// wczytuje z parametru adres
		List<Address> pozycje = null;   						// lista na wyniki, tak musi byc lista Address'ów
		
		try { 	// jedna pozycja
			Log.d(Mapa.DEBUG_TAG, "Geocoder dosta³ adres: "+adres);
			
			pozycje = geocoder.getFromLocationName(adres, 1); // wspolrzedne i max. ilosc zwroconych wspó³rzêdnych (wokó³ adresu, mo¿na ew. ograniczyæ jeszcze prostok¹t poszukiwañ)
			
		} 
		catch (IOException e) {
			Log.e(MainActivity.DEBUG_TAG, "IO Exception in getFromLocationName() - masz neta?");
			e.printStackTrace();
			
			return ("IO Exception: czy masz po³¹czenie z Internetem?");
		} 
		catch (IllegalArgumentException e2) {
			String errorString = "Illegal arguments " +
					adres+
					" passed to address service";

			Log.e(MainActivity.DEBUG_TAG, errorString);
			e2.printStackTrace();
			return errorString;
		}

		if (pozycje != null && pozycje.size() > 0) { 	// jak znalazl pozycjê to j¹ sformatuj i zwroc
			Address pozycjaA = pozycje.get(0);			// obs³uga 1 zwracanej pozycji - w formie obiektu Address

			String addressText = String.format( "%s,%s",				
					pozycjaA.getLatitude(),	
					pozycjaA.getLongitude());

			return addressText;
		} else {
			return "Wspó³rzêdne nieodnalezione.";
		}
	}
	
	
	/* Zwraca Stringa, wtedy ³atwiej jest przekazywaæ b³êdy na ekran, bez pierdzielenia z przemycaniem w LatLng,
	 * ³atwiej póŸniej przerobiæ String na LatLng
	 */
	@Override
	protected void onPostExecute(String pozycja) {
		super.onPostExecute(pozycja);
		listener.jestPozycja(pozycja);
	}
	
	public interface AsyncWspolrzedneListener {			// interfejs, implementuj¹c go w Activity mo¿na wygodnie s³uchaæ, jak zadanie z tej klasy skoñczy siê wykonywaæ
		public void jestPozycja(String pozycja);	// czyli daje siê ³atwo zwróciæ wynik do wywo³uj¹cej go Activity
	}
}





