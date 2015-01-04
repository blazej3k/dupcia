package blake.przewodnikturystyczny.mapa;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import blake.przewodnikturystyczny.activity.MainActivity;

import com.google.android.gms.maps.model.LatLng;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;

public class PobierzAdresAsync extends AsyncTask<LatLng, Void, String> {

	private Context context;
	private AsyncAdresListener listener;
	
	public PobierzAdresAsync(Context context, AsyncAdresListener listener) {
		this.context = context;
		this.listener = listener;
	}
	
	@Override
	protected String doInBackground(LatLng... pozycje) {			// tu przez execute(pozycje) wchodzi lista pozycji, metoda jest napisana, �e zawsze wchodzi 1 pozycja
		Geocoder geocoder = new Geocoder(context, Locale.getDefault());
		LatLng pozycja = pozycje[0]; 								// wczytuje z parametru pozycj�
		List<Address> adresy = null;   								// lista na wyniki
		
		try { // jeden adres
			adresy = geocoder.getFromLocation(pozycja.latitude, pozycja.longitude, 1); // wspolrzedne i max. ilosc zwroconych adres�w. (chyba o dok�adno�� chodzi)
			
		} 
		catch (IOException e) {
			Log.e(MainActivity.DEBUG_TAG, "IO Exception in getFromLocation() - masz neta?");
			e.printStackTrace();
			
			return ("IO Exception: czy masz po��czenie z Internetem?");
		} 
		catch (IllegalArgumentException e2) {
			String errorString = "Illegal arguments " +
					Double.toString(pozycja.latitude) +
					" , " +
					Double.toString(pozycja.longitude) +
					" passed to address service";

			Log.e(MainActivity.DEBUG_TAG, errorString);
			e2.printStackTrace();
			return errorString;
		}

		if (adresy != null && adresy.size() > 0) { 	// jak znalazl adres to go sformatuj i zwroc
			Address adres = adresy.get(0);			// obs�uga 1 zwracanego adresu

			String addressText = String.format( "%s, %s",				
					adres.getMaxAddressLineIndex() > 0 ? adres.getAddressLine(0) : "",	// If there's a street address, add it
							adres.getLocality()); 										// Locality is usually a city
			//				adres.getCountryName());									// The country of the address

			return addressText;
		} else {
			return "Adres nieodnaleziony.";
		}
	}
	
	@Override
	protected void onPostExecute(String adres) {
		super.onPostExecute(adres);
		listener.jestAdres(adres);
	}
	
	public interface AsyncAdresListener {		// interfejs, implementuj�c go w Activity mo�na wygodnie s�ucha�, jak zadanie z tej klasy sko�czy si� wykonywa�
		public void jestAdres(String adres);	// czyli daje si� �atwo zwr�ci� wynik do wywo�uj�cej go Activity

	}
}





