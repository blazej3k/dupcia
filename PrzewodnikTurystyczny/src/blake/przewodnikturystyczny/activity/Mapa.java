package blake.przewodnikturystyczny.activity;

import java.util.List;
import java.util.TreeMap;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import blake.przewodnikturystyczny.R;
import blake.przewodnikturystyczny.baza.model.TabBudynek;
import blake.przewodnikturystyczny.mapa.ObslugaMapy;
import blake.przewodnikturystyczny.mapa.PobierzAdresAsync;
import blake.przewodnikturystyczny.mapa.PobierzAdresAsync.AsyncAdresListener;
import blake.przewodnikturystyczny.mapa.PobierzWspolrzedneAsync;
import blake.przewodnikturystyczny.mapa.PobierzWspolrzedneAsync.AsyncWspolrzedneListener;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class Mapa extends Activity implements OnMapReadyCallback, OnMapClickListener, OnMapLongClickListener, OnInfoWindowClickListener, AsyncAdresListener, AsyncWspolrzedneListener {

	private class MyInfoWindowAdapter implements InfoWindowAdapter {
		private final View myContentsView;
//		Boolean czyBudynek;

		MyInfoWindowAdapter() {
			myContentsView = getLayoutInflater().inflate(
					R.layout.custom_info_window_map, null);
		}
		/* 
		 * najpierw wywo³ywane jest getInfoWindow, w nim tworzymy ca³y widok, jeœli zwróci nulla,
		 * to jest robione getInfoContents, s³u¿y jakby do samego modyfikowania treœci - wg dokumentacji Google Maps
		 */
		@Override
		public View getInfoContents(Marker marker) {		// pobierane s¹ opisy wszystkich obiektów naraz, w dwóch oddzielnych listach. Mo¿na z³¹czyæ w jedn¹ funkcjê generyczn¹, bêdzie mniej sprawdzeñ, ale nie jest to konieczne - ró¿ne pola s¹ i by³oby to niewygodne
			currentMarker = marker;							// przypisuje marker, zeby mozna bylo jego info window chowac
			
			TextView tvTitle = ((TextView) myContentsView
					.findViewById(R.id.wp_title));
			TextView tvSnippet = ((TextView) myContentsView
					.findViewById(R.id.wp_snippet));
			ImageView iv_icon = ((ImageView)myContentsView.findViewById(R.id.wp_icon));
			   
			String snippet="";
						
			if (opakowanieBudynek.containsKey(marker)) {
				List<String> opis = opakowanieBudynek.get(marker);
				snippet = opis.get(0);
				iv_icon.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_camera));
			}
			else if (opakowanieMiejsce.containsKey(marker)) {
				List<String> opis = opakowanieMiejsce.get(marker);
				snippet = opis.get(0);
				iv_icon.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_gallery));
			}
			else
				iv_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));
			
			tvTitle.setText(marker.getTitle());
			tvSnippet.setText(snippet);
			
			return myContentsView;
		}

		@Override
		public View getInfoWindow(Marker marker) {
			return null;
		}
	}

	public static final String DEBUG_TAG = "Przewodnik";
	
	static final LatLng domyslnaPozycja = new LatLng(52.23, 21); // pozycja Warszawy
	static final int domyslnyZoom = 12;
	final int RQS_GooglePlayServices = 1;

	private Context context;
	private LocationManager serwis;
	private GoogleMap map;
	private ObslugaMapy obslugaMapy;
	private TreeMap<Marker, List<String>> opakowanieBudynek; // opakowania - czyli paczki dodatkowych parametrów do Markerów (na potrzeby wyœwietlenia w InfoWindow)
	private TreeMap<Marker, List<String>> opakowanieMiejsce;
	
	private TextView tv_adres;
	private TextView tv_lokalizacja;
	private Button btn_znajdz;
	private EditText et_podaj_adres;
	private ProgressBar mActivityIndicator;
	private Marker currentMarker;

	private Boolean ifZnajdz = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getApplicationContext();
		actionBar();

		serwis = (LocationManager) getSystemService(LOCATION_SERVICE);
		boolean locationEnabled = serwis
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// sprawdzanie czy lokalizacja jest w³¹czona, gps (chyba tylko to sprawdza)

		if (!locationEnabled) {
			Toast.makeText(context, "gpsa masz wy³¹czonego, idŸ se w³¹cz.",
					Toast.LENGTH_LONG);
			// Intent intent = new
			// Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			// startActivity(intent);
			// otwiera okno konfiguracji od zarz¹dzania lokalizacj¹
		}

		setContentView(R.layout.activity_mapa);

		tv_adres = (TextView) findViewById(R.id.tv_adres);
		tv_lokalizacja = (TextView) findViewById(R.id.tv_lokalizacja);
		et_podaj_adres = (EditText) findViewById(R.id.et_podaj_adres);
		btn_znajdz = (Button) findViewById(R.id.btn_znajdz);
		mActivityIndicator = (ProgressBar) findViewById(R.id.progress_adres);

		Toast.makeText(
				context, "Us³ugi GooglePlay dostêpne: "+GooglePlayServicesUtil.isGooglePlayServicesAvailable(context),Toast.LENGTH_LONG).show();
		MapFragment mapFragment = (MapFragment) getFragmentManager()
				.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
		
		btn_znajdz.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pobierzWspolrzedne(et_podaj_adres.getText().toString());
				ifZnajdz = true;
			}
		});	
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.action_bar, menu);
    
    return true;
    }
	
	private void actionBar() {
		getActionBar().setTitle("Obwarzanek");
//		getActionBar().setDisplayHomeAsUpEnabled(true);		// i tak go nie uzywam bo bym musial Support ladowac
		// to ta strzalka w lewo, przy ikonce aplikacji, taki 'Wstecz'
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Toast.makeText(getApplicationContext(), "Naciœniêto: " + item.getTitle().toString(), Toast.LENGTH_SHORT).show();
		
		switch (item.getItemId()) {
		case (R.id.ab_wstecz): {
	        Intent intent = new Intent(context, MainActivity.class);
	        startActivity(intent);
//			finish();		// niby nie polecany ale szybszy i jo³ jo³
			break;
		}
		case (R.id.ab_dalej): {
			
			break;
		}
		case (R.id.ab_pokazukryj): {
			if (currentMarker != null)
				currentMarker.hideInfoWindow();
			break;
		}
//		case (android.R.id.home):		// przecie nie uzywam i wylaczony
//			// 	finish();
//			return false;
////			break;
		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
 
    @Override
    public void onBackPressed() {
    	Toast.makeText(getApplicationContext(), "Naciœniêto przycisk wstecz", Toast.LENGTH_SHORT).show();
    super.onBackPressed();
    }
	
	private void domyslnaMapa(GoogleMap map) {
		Location ostatniaLokalizacja = pobierzOstatniaLokalizacje();
		LatLng ostatniaLL = new LatLng(ostatniaLokalizacja.getLatitude(), ostatniaLokalizacja.getLongitude());

		map.moveCamera(CameraUpdateFactory.newLatLngZoom(ostatniaLL, domyslnyZoom)); 			// przenosi do ostatniej lokalizacji
		// map.moveCamera(CameraUpdateFactory.newLatLngZoom(domyslnaPozycja, domyslnyZoom)); 	// przenosi do domyslnej lokalizacji
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		map.setMyLocationEnabled(true);
		map.getUiSettings().setRotateGesturesEnabled(false); // blokowanie obracania gestem
		map.getUiSettings().setZoomControlsEnabled(true);
		map.getUiSettings().setMapToolbarEnabled(false);
		map.getUiSettings().setTiltGesturesEnabled(false);
	}

	private Location pobierzOstatniaLokalizacje() { // pobiera ostatni¹ znan¹ lokalizacjê
		Criteria criteria = new Criteria(); // pozwala automatycznie wybrac lepszego 'providera' lokalizacji (gps, wifi, 'gdzies cos zapisane')
		String provider = serwis.getBestProvider(criteria, false); // domyslnie to chyba ostatniego z dostêpnych bierze, bo Criteria bez parametrów.
		Location lokalizacja = serwis.getLastKnownLocation(provider);

		Log.d(DEBUG_TAG, "Location_Provider: " + provider);
		return lokalizacja;
	}

	private String pobierzAdres(LatLng pozycjaLL) {
		String adres = "";
		
//		Location pozycja = new Location("Test");
//		pozycja.setLatitude(pozycjaLL.latitude);
//		pozycja.setLongitude(pozycjaLL.longitude);
		
//        // Ensure that a Geocoder services is available
//        if (Build.VERSION.SDK_INT >=
//                Build.VERSION_CODES.GINGERBREAD
//                            &&
//                Geocoder.isPresent()) {
//            // Show the activity indicator
//            mActivityIndicator.setVisibility(View.VISIBLE);
            /*
             * Reverse geocoding is long-running and synchronous.
             * Run it on a background thread.
             * Pass the current location to the background task.
             * When the task finishes,
             * onPostExecute() displays the address.
             */
		
		new PobierzAdresAsync(context, this).execute(pozycjaLL);

		return adres;
	}
	
	private void pobierzWspolrzedne(String adres) {
//		new PobierzWspolrzedneAsync(context, this).execute(adres);
		new PobierzWspolrzedneAsync<TabBudynek>(context, this, null, false).execute(adres);
	}

	private void przesunMape(LatLng pozycjaLL) {
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(pozycjaLL, domyslnyZoom));
	}
	
	@Override
	public void onMapReady(GoogleMap map) {
		this.map = map;
		obslugaMapy = new ObslugaMapy(map);
		domyslnaMapa(map);
		opakowanieBudynek = obslugaMapy.dodajMarkery(obslugaMapy.pobierzBudynki()); // pobiera budynki jako liste i wywoluje generyczn¹ metode do wyswietlania budynkow
		opakowanieMiejsce = obslugaMapy.dodajMarkery(obslugaMapy.pobierzMiejsca());	// funkcja od razu zwraca opakowania - czyli paczki dodatkowych parametrów do Markerów (na potrzeby wyœwietlenia w InfoWindow)

		map.setInfoWindowAdapter(new MyInfoWindowAdapter()); // ustawia customowy adapter do okienek informacyjnych - tych po klikniêciu w marker
															 // zdefiniowany jest w klasie wewnêtrznej, na górze tej klasy
		map.setOnMapClickListener(this);
		map.setOnMapLongClickListener(this);
		map.setOnInfoWindowClickListener(this);
	}

	@Override
	public void onMapClick(LatLng pozycja) {
		double szerokosc = pozycja.latitude;
		double dlugosc = pozycja.longitude;

		szerokosc *= 100; 	// mnozenie *100, zaokraglanie, dzielenie przez 100 =
		dlugosc *= 100;		// zaokraglanie do 2 miejsca po przecinku
		szerokosc = Math.round(szerokosc) / 100.0 ;
		dlugosc = Math.round(dlugosc) / 100.0 ;
		
//		Log.d(DEBUG_TAG, "onMapClick: szer.: " + szerokosc + " d³.: " + dlugosc);
		Toast.makeText(context,	"onMapClick: szer.: " + szerokosc + " d³.: " + dlugosc, Toast.LENGTH_LONG).show();

		pobierzAdres(pozycja);
	}

	@Override
	public void onMapLongClick(LatLng pozycja) {
		Toast.makeText(context, pozycja.toString(), Toast.LENGTH_LONG).show();

	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		Toast.makeText(context, "Klikniêto: " + marker.getTitle(), Toast.LENGTH_SHORT).show();
		showSzczegolyDialog(marker);
	}

    public void showSzczegolyDialog(Marker marker) {
    	DialogFragment dialog;
    	
    	if (opakowanieBudynek.containsKey(marker))
    		dialog = new DialogSzczegolyFragment(marker.getTitle(), true);
    	else if (opakowanieMiejsce.containsKey(marker))
    		dialog = new DialogSzczegolyFragment(marker.getTitle(), false);
    	else
    		dialog = new DialogFragment();
        
        dialog.show(getFragmentManager(), "DialogSzczegolyFragment");
    }
	
	@Override
	protected void onResume() {
		super.onResume();

		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getApplicationContext());

		if (resultCode == ConnectionResult.SUCCESS) {
			Toast.makeText(context, "isGooglePlayServicesAvailable SUCCESS",
					Toast.LENGTH_LONG).show();
		} else {
			GooglePlayServicesUtil.getErrorDialog(resultCode, this,
					RQS_GooglePlayServices);
		}
	}

	@Override	// funkcja od naszego AsyncAdresListenera - tutaj zwrócony bêdzie adres z PobierzAdresAsync
	public void jestAdres(String adres) {
        mActivityIndicator.setVisibility(View.GONE); 	// wypierdziel progressbar
        tv_adres.setText(adres);						// wyœwietl adres
        
        pobierzWspolrzedne(adres);							// dla celów testowych wywo³uje od razu pobieranie lokalizacji z pobranego adresu
	}

	@Override	// funkcja od naszego AsyncPozycjaListener - tutaj zwrócona bêdzie pozycja z PobierzLokalizacjeAsync
	public void jestPozycja(String pozycja) {
		LatLng pozycjaLL;
		
		if(Character.isDigit(pozycja.charAt(0))) { // sprawdza czy mamy wspó³rzêdne czy tekst b³êdu.
			String[] pozycjaS;
			pozycjaS = pozycja.split(",");
			pozycjaLL = new LatLng(Double.parseDouble(pozycjaS[0]), Double.parseDouble(pozycjaS[1]));
			tv_lokalizacja.setText(pozycjaLL.toString());

			if(ifZnajdz) {			// jezeli byl klikniety przycisk Znajdz, to ustaw na false i przesun mape - zapobiega przesuwaniu do kliknietego miejsca
				ifZnajdz = false;
				przesunMape(pozycjaLL);
			}
//			Log.d(DEBUG_TAG, "Robie if z jestPozycja, nie else'a");
		}
		else tv_lokalizacja.setText(pozycja); // wyœwietl to co wróci³o, czyli w tym wypadku treœæ b³êdu.
	}
}
