package blake.przewodnikturystyczny.mapa;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import android.util.Log;
import blake.przewodnikturystyczny.baza.model.IfMarkierable;
import blake.przewodnikturystyczny.baza.model.TabBudynek;
import blake.przewodnikturystyczny.baza.model.TabMiejsce;
import blake.przewodnikturystyczny.baza.model.TabRzecz;
import blake.przewodnikturystyczny.baza.model.TabWydarzenie;

import com.activeandroid.query.Select;
import com.google.android.gms.maps.CameraUpdateFactory;
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
			
			return title1.compareTo(title2);		// tu u¿ycie standardowego komparatora do Stringów, jest bardzo dobry i skuteczny
		}
	}
	
	private class IfMarkierableComparator implements Comparator<IfMarkierable> {
		@Override
		public int compare(IfMarkierable lhs, IfMarkierable rhs) {
			String nazwa1 = lhs.getNazwa();
			String nazwa2 = rhs.getNazwa();
			
			return nazwa1.compareTo(nazwa2);
		}
	}

	public static final String DEBUG_TAG = "Przewodnik";
	static final int domyslnyZoom = 12;
	
//	public static final float alphaBudynek = 1f;
//	public static final float alphaMiejsce = 0.99f;
	static final LatLng domyslnaPozycja = new LatLng(52.23, 21); // pozycja Warszawy
//	static final int domyslnyZoom = 12;
		
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
	public <T extends IfMarkierable> TreeMap<Marker, List<String>> dodajMarkery(List<T> obiekty) { // pozwala jedn¹ metod¹ obs³ugiwaæ dwie ró¿ne generyczne listy, problem robi generyczoœæ list - znaczy juz jest git
		List<Marker> listaMarker= new LinkedList<Marker>();
		Marker tempMarker;
		LatLng pozycjaLL;
		double latitude, longitude = 0;
		Boolean czyBudynek;		// czyBudynek - Budynek; !czyBudynek - Miejsce
		
		if (obiekty != null && obiekty.size() > 0 && obiekty.get(0) != null)	{							// sprawdza czy lista jest niepusta
			if (obiekty.get(0).getClass().getName().contains(".TabBudynek"))	// sprawdza jakiego typu s¹ obiekty na liœcie i oznacza to przy czyBudynek
				czyBudynek = true;
			else if (obiekty.get(0).getClass().getName().contains(".TabMiejsce"))
				czyBudynek = false;
			else {
				Log.d(DEBUG_TAG, "B³êdny typ danych w metodzie dodajMarkiery.");
				return null; // b³êdny typ danych u¿yty												
			}
			
			Log.d(DEBUG_TAG, "Klasa markierów: "+obiekty.get(0).getClass().getName());
		}
		else {
			Log.d(DEBUG_TAG, "Lista pusta w metodzie dodajMarkiery");
			return null;
		}
			
		
		for(T x: obiekty) {
			Log.d(DEBUG_TAG, "Latitude dla: "+x.getNazwa());
			latitude = x.getLatitude();
			longitude = x.getLongitude();

			if (latitude == 0 || longitude == 0)		// jeœli w bazie s¹ nieustawione to domyœlnie maj¹ wartoœæ 0, to niech ustawia domyœln¹ lokalizacjê
				pozycjaLL = domyslnaPozycja;
			else 
				pozycjaLL = new LatLng(latitude, longitude);

			tempMarker = map.addMarker((new MarkerOptions()
			.position(pozycjaLL)
			.title(x.getNazwa())
//			.alpha(czyBudynek ? alphaBudynek : alphaMiejsce )				// potrzebne do odró¿nienia przy wyœwietlaniu InfoWindow co jest wyœwietlane 
			.icon(czyBudynek ? colorBudynekMarker : colorMiejsceMarker)));	// kolor markera wybierany na podstawie zmiennej czyBudynek
//																			// szybszy jest ten sposob niz robienie TreeMap i wo¿enie Markera z map¹ z dodatkowymi atrybutami
			listaMarker.add(tempMarker);									// TreeMap ju¿ jest.
		}																	
		
		if(czyBudynek) {
			Log.d(DEBUG_TAG, "Wysy³am: "+listaMarker.size()+" markerów i "+obiekty.size()+" budynków.");
			return generujOpakowanieOpisBudynek(listaMarker, (List<TabBudynek>) obiekty);
		}
		else if(!czyBudynek) {
			return generujOpakowanieOpisMiejsce(listaMarker, (List<TabMiejsce>) obiekty);
		}
		
		return null;
	}

	public List<IfMarkierable> rozwiazZaleznosciSum(String nazwa, String typ, Boolean czyBudynek) {
		Set<IfMarkierable> zaleznoscMiejsca = new TreeSet<IfMarkierable>(new IfMarkierableComparator());
		
		Log.d(DEBUG_TAG, "Rozwiazuje zaleznosc dla "+nazwa+" typ "+typ);
		
		if (typ.equals("Miejsca")) {
			TabMiejsce obiekt = new Select().from(TabMiejsce.class).where("nazwa = ?", nazwa).executeSingle();
			
			if (!czyBudynek) {
				zaleznoscMiejsca.add(obiekt);												// dodaj samego zaiteresowanego do pobrania
				Log.d(DEBUG_TAG, "Znalazlem obiekt: "+obiekt.getNazwa());					// zespoly miejsc niezaimplementowane
			}
			if (czyBudynek) {
				TabBudynek budynek = obiekt.getBudynek();
				zaleznoscMiejsca.add(budynek);
			}
			
			Log.d(DEBUG_TAG, "Wysylam "+zaleznoscMiejsca.size()+" elementow.");
		}
		else if (typ.equals("Wydarzenia")) {
			TabWydarzenie obiekt = new Select().from(TabWydarzenie.class).where("nazwa = ?", nazwa).executeSingle();	
			if (!czyBudynek) {
				zaleznoscMiejsca.addAll(obiekt.getMiejsca());
				Log.d(DEBUG_TAG, "Znalazlem obiekt: "+obiekt.getNazwa());
			}
			
		}
		else if (typ.equals("Rzeczy")) {
			TabRzecz obiekt = new Select().from(TabRzecz.class).where("nazwa = ?", nazwa).executeSingle();	
			if (!czyBudynek) {
				zaleznoscMiejsca.add(obiekt.getMiejsce());
				Log.d(DEBUG_TAG, "Znalazlem obiekt: "+obiekt.getNazwa());
			}
		}
		else if (typ.equals("Postacie")) {
			/*TabPostac obiekt = new Select().from(TabPostac.class).where("nazwa = ?", nazwa).executeSingle();	
			
			if (!czyBudynek) {
				zaleznoscMiejsca.add(obiekt.getMiejsca());
				Log.d(DEBUG_TAG, "Znalazlem obiekt: "+obiekt.getNazwa());
			}*/
			// TODO not implemented kurde yet
		}
		
		return new ArrayList<IfMarkierable>(zaleznoscMiejsca);
	}
	
	private List<TabMiejsce> rozwiazZaleznosciRzeczy(List<TabRzecz> rzeczy) {
		List<TabMiejsce> listaMiejsc = new ArrayList<TabMiejsce>();
		TabMiejsce miejsce;
		for (TabRzecz x: rzeczy) {
			miejsce = x.getMiejsce();
			if (miejsce != null) listaMiejsc.add(miejsce);
		}
		return listaMiejsc;
	}
	
	private void rozwiazZaleznosciWydarzenia(List<TabWydarzenie> wydarzenia) {
		
	}
	
	private void przesunMape(LatLng pozycjaLL) {
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(pozycjaLL, domyslnyZoom));
	}
	
	public void dodajMarkier(String nazwa, String typ, Boolean czyBudynek) {
		IfMarkierable obiekt = rozwiazZaleznosciSum(nazwa, typ, czyBudynek).get(0);
		LatLng pozycja = new LatLng(obiekt.getLatitude(), obiekt.getLongitude());
		
		map.addMarker(new MarkerOptions()
		.position(pozycja)
		.title(obiekt.getNazwa())
		.snippet(obiekt.getAdres()));
		
		przesunMape(pozycja);
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
		// TODO zrzuciæ do Async
		return new Select().all().from(TabBudynek.class).execute(); 
	}
	
	public List<TabMiejsce> pobierzMiejsca() {
		// TODO zrzuciæ do Async
		return new Select().all().from(TabMiejsce.class).execute(); 
	}

	public LatLng zwrocOstatnia() {
		// TODO Auto-generated method stub
		return null;
	}
}
