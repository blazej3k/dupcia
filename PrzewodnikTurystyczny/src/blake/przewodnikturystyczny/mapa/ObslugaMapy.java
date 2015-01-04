package blake.przewodnikturystyczny.mapa;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import blake.przewodnikturystyczny.R;
import blake.przewodnikturystyczny.baza.model.IfMarkierable;
import blake.przewodnikturystyczny.baza.model.TabBudynek;
import blake.przewodnikturystyczny.baza.model.TabMiejsce;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ObslugaMapy {

	public static final String DEBUG_TAG = "Przewodnik";
	
	public static final float alphaBudynek = 1f;
	public static final float alphaMiejsce = 0.99f;
	static final LatLng domyslnaPozycja = new LatLng(52.23, 21); // pozycja Warszawy
		
	private final BitmapDescriptor colorBudynekMarker;
	private final BitmapDescriptor colorMiejsceMarker;
	
	
	private GoogleMap map;
		
	public ObslugaMapy(GoogleMap map) {
		this.map = map;
		
		colorBudynekMarker = BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_RED);
		colorMiejsceMarker = BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
	}
	
	public <T extends IfMarkierable> Boolean dodajMarkery(List<T> obiekty) { // pozwala jedn� metod� obs�ugiwa� dwie r�ne generyczne listy, problem robi generyczo��
		Marker tempMarker;
		LatLng pozycjaLL;
		double latitude, longitude = 0;
		Boolean czyBudynek;		// czyBudynek - Budynek; !czyBudynek - Miejsce
		
		if (obiekty != null && obiekty.size() > 0)	{							// sprawdza czy lista jest niepusta
			if (obiekty.get(0).getClass().getName().contains(".TabBudynek"))	// sprawdza jakiego typu s� obiekty na li�cie i oznacza to przy czyBudynek
				czyBudynek = true;
			else if (obiekty.get(0).getClass().getName().contains(".TabMiejsce"))
				czyBudynek = false;
			else {
				Log.d(DEBUG_TAG, "B��dny typ danych w metodzie dodajMarkiery.");
				return false; // b��dny typ danych u�yty												
			}
			
			Log.d(DEBUG_TAG, "Klasa markier�w: "+obiekty.get(0).getClass().getName());
		}
		else {
			Log.d(DEBUG_TAG, "Lista pusta w metodzie dodajMarkiery");
			return false;
		}
			
		for(T x: obiekty) {
			latitude = x.getLatitude();
			longitude = x.getLongitude();

			if (latitude == 0 || longitude == 0)		// je�li w bazie s� nieustawione to domy�lnie maj� warto�� 0, to niech ustawia domy�ln� lokalizacj�
				pozycjaLL = domyslnaPozycja;
			else 
				pozycjaLL = new LatLng(latitude, longitude);

			tempMarker = map.addMarker((new MarkerOptions()
			.position(pozycjaLL)
			.title(x.getNazwa())
			.snippet(x.getAdres())
			.alpha(czyBudynek ? alphaBudynek : alphaMiejsce )				// potrzebne do odr�nienia przy wy�wietlaniu InfoWindow co jest wy�wietlane 
			.icon(czyBudynek ? colorBudynekMarker : colorMiejsceMarker)));	// kolor markera wybierany na podstawie zmiennej czyBudynek
		}																	// szybszy jest ten sposob niz robienie TreeMap i wo�enie Markera z map� z dodatkowymi atrybutami
		
		return true;
	}

	
	public List<TabBudynek> pobierzBudynki() {
		// TODO zrzuci� do Async
		return TabBudynek.listAll(TabBudynek.class);
	}
	
	public List<TabMiejsce> pobierzMiejsca() {
		// TODO zrzuci� do Async
		return TabMiejsce.listAll(TabMiejsce.class);
	}
}
