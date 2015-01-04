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
	
	public <T extends IfMarkierable> Boolean dodajMarkery(List<T> obiekty) { // pozwala jedn¹ metod¹ obs³ugiwaæ dwie ró¿ne generyczne listy, problem robi generyczoœæ
		Marker tempMarker;
		LatLng pozycjaLL;
		double latitude, longitude = 0;
		Boolean czyBudynek;		// czyBudynek - Budynek; !czyBudynek - Miejsce
		
		if (obiekty != null && obiekty.size() > 0)	{							// sprawdza czy lista jest niepusta
			if (obiekty.get(0).getClass().getName().contains(".TabBudynek"))	// sprawdza jakiego typu s¹ obiekty na liœcie i oznacza to przy czyBudynek
				czyBudynek = true;
			else if (obiekty.get(0).getClass().getName().contains(".TabMiejsce"))
				czyBudynek = false;
			else {
				Log.d(DEBUG_TAG, "B³êdny typ danych w metodzie dodajMarkiery.");
				return false; // b³êdny typ danych u¿yty												
			}
			
			Log.d(DEBUG_TAG, "Klasa markierów: "+obiekty.get(0).getClass().getName());
		}
		else {
			Log.d(DEBUG_TAG, "Lista pusta w metodzie dodajMarkiery");
			return false;
		}
			
		for(T x: obiekty) {
			latitude = x.getLatitude();
			longitude = x.getLongitude();

			if (latitude == 0 || longitude == 0)		// jeœli w bazie s¹ nieustawione to domyœlnie maj¹ wartoœæ 0, to niech ustawia domyœln¹ lokalizacjê
				pozycjaLL = domyslnaPozycja;
			else 
				pozycjaLL = new LatLng(latitude, longitude);

			tempMarker = map.addMarker((new MarkerOptions()
			.position(pozycjaLL)
			.title(x.getNazwa())
			.snippet(x.getAdres())
			.alpha(czyBudynek ? alphaBudynek : alphaMiejsce )				// potrzebne do odró¿nienia przy wyœwietlaniu InfoWindow co jest wyœwietlane 
			.icon(czyBudynek ? colorBudynekMarker : colorMiejsceMarker)));	// kolor markera wybierany na podstawie zmiennej czyBudynek
		}																	// szybszy jest ten sposob niz robienie TreeMap i wo¿enie Markera z map¹ z dodatkowymi atrybutami
		
		return true;
	}

	
	public List<TabBudynek> pobierzBudynki() {
		// TODO zrzuciæ do Async
		return TabBudynek.listAll(TabBudynek.class);
	}
	
	public List<TabMiejsce> pobierzMiejsca() {
		// TODO zrzuciæ do Async
		return TabMiejsce.listAll(TabMiejsce.class);
	}
}
