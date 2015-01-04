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
	/* Zwraca Stringa, wtedy �atwiej jest przekazywa� b��dy na ekran, bez pierdzielenia z przemycaniem w LatLng,
	 * �atwiej p�niej przerobi� String na LatLng
	 */

	private Context context;
	private AsyncWspolrzedneListener listener;
	
	public PobierzWspolrzedneAsync(Context context, AsyncWspolrzedneListener listener) {
		this.context = context;
		this.listener = listener;
	}
	
	@Override
	protected String doInBackground(String... adresy) {			// tu przez execute(pozycje) wchodzi lista adres�w, metoda jest napisana, �e zawsze wchodzi 1 adres
		Geocoder geocoder = new Geocoder(context, Locale.getDefault());
		String adres = adresy[0]; 								// wczytuje z parametru adres
		List<Address> pozycje = null;   						// lista na wyniki, tak musi byc lista Address'�w
		
		try { 	// jedna pozycja
			Log.d(Mapa.DEBUG_TAG, "Geocoder dosta� adres: "+adres);
			
			pozycje = geocoder.getFromLocationName(adres, 1); // wspolrzedne i max. ilosc zwroconych wsp�rz�dnych (wok� adresu, mo�na ew. ograniczy� jeszcze prostok�t poszukiwa�)
			
		} 
		catch (IOException e) {
			Log.e(MainActivity.DEBUG_TAG, "IO Exception in getFromLocationName() - masz neta?");
			e.printStackTrace();
			
			return ("IO Exception: czy masz po��czenie z Internetem?");
		} 
		catch (IllegalArgumentException e2) {
			String errorString = "Illegal arguments " +
					adres+
					" passed to address service";

			Log.e(MainActivity.DEBUG_TAG, errorString);
			e2.printStackTrace();
			return errorString;
		}

		if (pozycje != null && pozycje.size() > 0) { 	// jak znalazl pozycj� to j� sformatuj i zwroc
			Address pozycjaA = pozycje.get(0);			// obs�uga 1 zwracanej pozycji - w formie obiektu Address

			String addressText = String.format( "%s,%s",				
					pozycjaA.getLatitude(),	
					pozycjaA.getLongitude());

			return addressText;
		} else {
			return "Wsp�rz�dne nieodnalezione.";
		}
	}
	
	
	/* Zwraca Stringa, wtedy �atwiej jest przekazywa� b��dy na ekran, bez pierdzielenia z przemycaniem w LatLng,
	 * �atwiej p�niej przerobi� String na LatLng
	 */
	@Override
	protected void onPostExecute(String pozycja) {
		super.onPostExecute(pozycja);
		listener.jestPozycja(pozycja);
	}
	
	public interface AsyncWspolrzedneListener {			// interfejs, implementuj�c go w Activity mo�na wygodnie s�ucha�, jak zadanie z tej klasy sko�czy si� wykonywa�
		public void jestPozycja(String pozycja);	// czyli daje si� �atwo zwr�ci� wynik do wywo�uj�cej go Activity
	}
}





