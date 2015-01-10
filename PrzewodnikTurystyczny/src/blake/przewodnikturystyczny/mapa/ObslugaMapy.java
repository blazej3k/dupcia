package blake.przewodnikturystyczny.mapa;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import android.util.Log;
import blake.przewodnikturystyczny.baza.model.IfMarkierable;
import blake.przewodnikturystyczny.baza.model.TabBudynek;
import blake.przewodnikturystyczny.baza.model.TabMiejsce;

import com.activeandroid.query.Select;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ObslugaMapy {
	
	private class MarkerComparator implements Comparator<Marker> {
		@Override
		public int compare(Marker m1, Marker m2) {
			String title1 = m1.getTitle();
			String title2 = m2.getTitle();
			
			return title1.compareTo(title2);		// tu u�ycie standardowego komparatora do String�w, jest bardzo dobry i skuteczny
		}
	}
	

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
	
	@SuppressWarnings("unchecked")
	public <T extends IfMarkierable> TreeMap<Marker, List<String>> dodajMarkery(List<T> obiekty) { // pozwala jedn� metod� obs�ugiwa� dwie r�ne generyczne listy, problem robi generyczo�� list - znaczy juz jest git
		List<Marker> listaMarker= new LinkedList<Marker>();
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
				return null; // b��dny typ danych u�yty												
			}
			
			Log.d(DEBUG_TAG, "Klasa markier�w: "+obiekty.get(0).getClass().getName());
		}
		else {
			Log.d(DEBUG_TAG, "Lista pusta w metodzie dodajMarkiery");
			return null;
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
			.alpha(czyBudynek ? alphaBudynek : alphaMiejsce )				// potrzebne do odr�nienia przy wy�wietlaniu InfoWindow co jest wy�wietlane 
			.icon(czyBudynek ? colorBudynekMarker : colorMiejsceMarker)));	// kolor markera wybierany na podstawie zmiennej czyBudynek
																			// szybszy jest ten sposob niz robienie TreeMap i wo�enie Markera z map� z dodatkowymi atrybutami
			listaMarker.add(tempMarker);
		}																	
		
		if(czyBudynek) {
			Log.d(DEBUG_TAG, "Wysy�am: "+listaMarker.size()+" marker�w i "+obiekty.size()+" budynk�w.");
			return generujOpakowanieOpisBudynek(listaMarker, (List<TabBudynek>) obiekty);
		}
		else if(!czyBudynek) {
			return generujOpakowanieOpisMiejsce(listaMarker, (List<TabMiejsce>) obiekty);
		}
		
		return null;
	}

	public TreeMap<Marker, List<String>> generujOpakowanieOpisBudynek(List<Marker> markery, List<TabBudynek> budynki) {
		TreeMap<Marker, List<String>> opakowanie = new TreeMap<Marker, List<String>>(new MarkerComparator());
		List<String> dodatek;
		
			for(TabBudynek x: budynki) {
				int indeks = budynki.indexOf(x);
				Marker marker = markery.get(indeks);

				dodatek = new LinkedList<String>();
				dodatek.add(x.getAdres());
//				dodatek.add(x.getDataPowstania());
//				dodatek.add(x.getProjektant());
//				dodatek.add(x.getOpis());
				
				opakowanie.put(marker, dodatek);
			}
			
			return opakowanie;
	}

	public TreeMap<Marker, List<String>> generujOpakowanieOpisMiejsce(List<Marker> markery, List<TabMiejsce> miejsca) {
		TreeMap<Marker, List<String>> opakowanie = new TreeMap<Marker, List<String>>(new MarkerComparator());
		List<String> dodatek;
			for (TabMiejsce x: miejsca) {
				int indeks = miejsca.indexOf(x);
				Marker marker = markery.get(indeks);
				
				dodatek = new LinkedList<String>();
				dodatek.add(x.getAdres());
//				dodatek.add(x.getDataPowstania());
//				dodatek.add(x.getProjektant());
//				dodatek.add(x.getOpis());

				opakowanie.put(marker, dodatek);
			}		
		return opakowanie;
	}
	
	public List<TabBudynek> pobierzBudynki() {
		// TODO zrzuci� do Async
		return new Select().all().from(TabBudynek.class).execute(); 
	}
	
	public List<TabMiejsce> pobierzMiejsca() {
		// TODO zrzuci� do Async
		return new Select().all().from(TabMiejsce.class).execute(); 
	}
}
