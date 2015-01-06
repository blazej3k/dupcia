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
import blake.przewodnikturystyczny.baza.model.IfLocalizable;

public class PobierzWspolrzedneAsync<T extends IfLocalizable> extends AsyncTask<String, Void, String> {
	/* Zwraca Stringa, wtedy �atwiej jest przekazywa� b��dy na ekran, bez pierdzielenia z przemycaniem w LatLng,
	 * Klasa otrzymuje list� obiekt�w do aktualizacji i automatycznie te obiekty aktualizuje.
	 * Je�li otrzyma !czyObiekt, wtedy brany jest pod uwage String z execute i Stringu wyj�ciowym wraca r�wnie� adres
	 */

	public static final String DEBUG_TAG = "Przewodnik";
	
	private Context context;
	private AsyncWspolrzedneListener listener;
	private List<T> listaObiektow;
	private Boolean czyObiekt;

	
	public PobierzWspolrzedneAsync(Context context, AsyncWspolrzedneListener listener, List<T> listaObiektow, Boolean czyObiekt) {
		this.context = context;
		this.listener = listener;
		this.czyObiekt = czyObiekt;
		
		if (czyObiekt)								// jesli !czyObiekt to listaObiektow jest nullem
			this.listaObiektow = listaObiektow;
		
	}
	
	@Override
	protected String doInBackground(String... adresy) {			// tedy wchodza zapytania z mapy, jako string o konkretny adres
		String adres= "";
		String wynik="";
		
		if(czyObiekt) {													// czyli czytaj z listy obiekt�w
			String[] wspolrzedneSS;
			double latitude, longitude=0;
			
			if (listaObiektow != null && listaObiektow.size() > 0) {	
				for (T obiekt: listaObiektow) {
					adres = obiekt.getAdres(); 							// wczytuje adres z obiektu
					wynik = lokalizujWspolrzedne(adres);				// namierza wspolrzedne

																		// tutaj tez parsowanie i od razu zapis do bazy wtedy tez sie w tle wykona
					if(Character.isDigit(wynik.charAt(0))) { 			// sprawdza czy mamy wsp�rz�dne czy tekst b��du.
						wspolrzedneSS = wynik.split(",");				
						latitude = Double.parseDouble(wspolrzedneSS[0]);
						longitude = Double.parseDouble(wspolrzedneSS[1]);

						obiekt.setLatitude(latitude);					// do obiektu zapis tylko je�li jest sukces
						obiekt.setLongitude(longitude);
						obiekt.save();									// dzieki interfejsowi mozna od razu zapisac do bazy, tutaj, w dodatkowym watku.
					}
					else {
						Log.d(DEBUG_TAG, "B��d pobierania wsp�rz�dnych: "+wynik);
					}
						
				}
			}
		}
		else if (!czyObiekt) {											// czyli jedziesz z tego co weszlo w execute()
			if(adresy != null && adresy.length > 0 && !adresy[0].isEmpty()) {	// jesli tablica istnieje, jest niepusta i adres pierwszy niepusty
				adres = adresy[0];										// tutaj nie robimy parsowania, bo mapa sobie sama parsuje
				wynik = lokalizujWspolrzedne(adres);
			}
		}

		return wynik;
	}
	
	private String lokalizujWspolrzedne(String adres) {
		List<Address> pozycje;									// lista na wyniki, tak musi byc lista Address'�w, bo tak zwraca geokoder
		Geocoder geocoder = new Geocoder(context, Locale.getDefault());
		
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
		} 
		else {
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
		public void jestPozycja(String pozycja);		// czyli daje si� �atwo zwr�ci� wynik do wywo�uj�cej go Activity
	}
}





