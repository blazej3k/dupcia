package blake.przewodnikturystyczny.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import blake.przewodnikturystyczny.R;
import blake.przewodnikturystyczny.async.PobierzAdresAsync;
import blake.przewodnikturystyczny.async.PobierzAdresAsync.AsyncListener;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapa extends Activity implements OnMapReadyCallback, OnMapClickListener, OnMapLongClickListener, OnInfoWindowClickListener, AsyncListener {

	class MyInfoWindowAdapter implements InfoWindowAdapter {
		private final View myContentsView;

		MyInfoWindowAdapter() {
			myContentsView = getLayoutInflater().inflate(
					R.layout.custom_info_window_map, null);
		}

		@Override
		public View getInfoContents(Marker marker) {
			TextView tvTitle = ((TextView) myContentsView
					.findViewById(R.id.title));
			tvTitle.setText(marker.getTitle());
			TextView tvSnippet = ((TextView) myContentsView
					.findViewById(R.id.snippet));
			tvSnippet.setText(marker.getSnippet());

			return myContentsView;
		}

		@Override
		public View getInfoWindow(Marker marker) {

			return null;
		}
	}

	public static final String DEBUG_TAG = "Przewodnik";
	static final LatLng domyslnaPozycja = new LatLng(52.23, 21); // pozycja
																	// Warszawy
	static final int domyslnyZoom = 12;
	final int RQS_GooglePlayServices = 1;

	private Context context;
	private LocationManager serwis;

	private TextView tv_adres;
	private ProgressBar mActivityIndicator;

	private List<LatLng> pozycje;
	private List<String> opisy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getApplicationContext();

		serwis = (LocationManager) getSystemService(LOCATION_SERVICE);
		boolean locationEnabled = serwis
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// sprawdzanie czy lokalizacja jest w��czona, gps (chyba tylko to sprawdza)

		if (!locationEnabled) {
			Toast.makeText(context, "gpsa masz wy��czonego, id� se w��cz.",
					Toast.LENGTH_LONG);
			// Intent intent = new
			// Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			// startActivity(intent);
			// otwiera okno konfiguracji od zarz�dzania lokalizacj�
		}

		setContentView(R.layout.activity_mapa);

		tv_adres = (TextView) findViewById(R.id.tv_adres);
		mActivityIndicator = (ProgressBar) findViewById(R.id.progress_adres);

		Toast.makeText(
				context, "Us�ugi GooglePlay dost�pne: "+GooglePlayServicesUtil.isGooglePlayServicesAvailable(context),Toast.LENGTH_LONG).show();
		MapFragment mapFragment = (MapFragment) getFragmentManager()
				.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);

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

	private void dodajMarkery(GoogleMap map, List<LatLng> pozycjeLL) {
		map.addMarker(
				new MarkerOptions().position(domyslnaPozycja).title(
						"Centr�wka!")).setSnippet("Du�y opis rikitiki");

		LatLng obok = new LatLng(52, 21);
		LatLng obok2 = new LatLng(52, 21.1);
		LatLng obok3 = new LatLng(52, 21.2);

		Marker drugiMarkerek = map
				.addMarker(new MarkerOptions().title("Marker drugi")
						.snippet("No i du�y opis").position(obok));
		Marker trzeciMarkerek = map.addMarker(new MarkerOptions().position(
				obok2).icon(
				BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
		Marker czwartyMarkerek = map.addMarker(new MarkerOptions()
				.position(obok3)
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.ic_launcher))
				.title("Marker czwarty"));

		czwartyMarkerek.showInfoWindow();
	}

	private Location pobierzOstatniaLokalizacje() { // pobiera ostatni� znan�
													// lokalizacj�
		Criteria criteria = new Criteria(); // pozwala automatycznie wybrac
											// lepszego 'providera' lokalizacji
		String provider = serwis.getBestProvider(criteria, false); // domyslnie to chyba ostatniego bierze, bo Criteria bez parametr�w.
		Location lokalizacja = serwis.getLastKnownLocation(provider);

		Log.d(DEBUG_TAG, "Location_Provider: " + provider);

		return lokalizacja;
	}

	private String pobierzAdres(LatLng pozycjaLL) {
		String adres = "";
		
		Location pozycja = new Location("Test");
		pozycja.setLatitude(pozycjaLL.latitude);
		pozycja.setLongitude(pozycjaLL.longitude);
		
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
		
            new PobierzAdresAsync(context, this).execute(pozycja);

		return adres;
	}

	@Override
	public void onMapReady(GoogleMap map) {
		domyslnaMapa(map);
		dodajMarkery(map, new ArrayList<LatLng>());

		map.setInfoWindowAdapter(new MyInfoWindowAdapter()); // ustawia customowy adapter do okienek informacyjnych - tych po klikni�ciu w marker
															 // zdefiniowany jest w klasie wewn�trznej, na g�rze tej klasy
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
		
//		Log.d(DEBUG_TAG, "onMapClick: szer.: " + szerokosc + " d�.: " + dlugosc);
		Toast.makeText(context,	"onMapClick: szer.: " + szerokosc + " d�.: " + dlugosc, Toast.LENGTH_LONG).show();

		pobierzAdres(pozycja);
	}

	@Override
	public void onMapLongClick(LatLng pozycja) {
		Toast.makeText(context, pozycja.toString(), Toast.LENGTH_LONG).show();

	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		Toast.makeText(context, "Info Window clicked@" + marker.getId(),
				Toast.LENGTH_SHORT).show();
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

	@Override	// funkcja od naszego AsyncListenera - tutaj zwr�cony b�dzie adres z PobierzAdresAsync
	public void doStuff(String adres) {
        mActivityIndicator.setVisibility(View.GONE); 	// wypierdziel progressbar
        tv_adres.setText(adres);						// wy�wietl adres
	}

}
