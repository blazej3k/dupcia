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
	/* Zwraca Stringa, wtedy ³atwiej jest przekazywaæ b³êdy na ekran, bez pierdzielenia z przemycaniem w LatLng,
	 * Klasa otrzymuje listê obiektów do aktualizacji i automatycznie te obiekty aktualizuje.
	 * Jeœli otrzyma !czyObiekt, wtedy brany jest pod uwage String z execute i Stringu wyjœciowym wraca równie¿ adres
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
		
		if(czyObiekt) {													// czyli czytaj z listy obiektów
			String[] wspolrzedneSS;
			double latitude, longitude=0;
			
			if (listaObiektow != null && listaObiektow.size() > 0) {	
				for (T obiekt: listaObiektow) {
					adres = obiekt.getAdres(); 							// wczytuje adres z obiektu
					wynik = lokalizujWspolrzedne(adres);				// namierza wspolrzedne

																		// tutaj tez parsowanie i od razu zapis do bazy wtedy tez sie w tle wykona
					if(Character.isDigit(wynik.charAt(0))) { 			// sprawdza czy mamy wspó³rzêdne czy tekst b³êdu.
						wspolrzedneSS = wynik.split(",");				
						latitude = Double.parseDouble(wspolrzedneSS[0]);
						longitude = Double.parseDouble(wspolrzedneSS[1]);

						obiekt.setLatitude(latitude);					// do obiektu zapis tylko jeœli jest sukces
						obiekt.setLongitude(longitude);
						obiekt.save();									// dzieki interfejsowi mozna od razu zapisac do bazy, tutaj, w dodatkowym watku.
					}
					else {
						Log.d(DEBUG_TAG, "B³¹d pobierania wspó³rzêdnych: "+wynik);
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
		List<Address> pozycje;									// lista na wyniki, tak musi byc lista Address'ów, bo tak zwraca geokoder
		Geocoder geocoder = new Geocoder(context, Locale.getDefault());
		
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
		} 
		else {
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
		public void jestPozycja(String pozycja);		// czyli daje siê ³atwo zwróciæ wynik do wywo³uj¹cej go Activity
	}
}





