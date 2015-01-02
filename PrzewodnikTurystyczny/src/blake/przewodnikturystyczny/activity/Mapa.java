package blake.przewodnikturystyczny.activity;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import blake.przewodnikturystyczny.R;

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

public class Mapa extends Activity implements OnMapReadyCallback, OnMapClickListener, OnMapLongClickListener, OnInfoWindowClickListener {

	class MyInfoWindowAdapter implements InfoWindowAdapter {
		private final View myContentsView;
		
		MyInfoWindowAdapter() {
			myContentsView = getLayoutInflater().inflate(R.layout.custom_info_window_map, null);
		}
		
		@Override
		public View getInfoContents(Marker marker) {
			TextView tvTitle = ((TextView)myContentsView.findViewById(R.id.title));
            tvTitle.setText(marker.getTitle());
            TextView tvSnippet = ((TextView)myContentsView.findViewById(R.id.snippet));
            tvSnippet.setText(marker.getSnippet());
   
            return myContentsView;
		}

		@Override
		public View getInfoWindow(Marker marker) {
			// TODO Auto-generated method stub
			return null;
		}
	}
		
	public static final String DEBUG_TAG = "Przewodnik";
	static final LatLng domyslnaPozycja = new LatLng(52.23, 21);  // pozycja Warszawy
	static final int domyslnyZoom = 12;
	final int RQS_GooglePlayServices = 1;

	private Context context;
	
	private List<LatLng> pozycje;
	private List<String> opisy;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getApplicationContext();
		
		setContentView(R.layout.activity_mapa);

		Toast.makeText(context, "Us³ugi GooglePlay dostêpne: "+GooglePlayServicesUtil.isGooglePlayServicesAvailable(context), Toast.LENGTH_LONG).show();
		
		MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
		
/*		try {
			gmap = mapFragment.getMap(); // pobieranie mapy
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (gmap != null) { //dodaje markery na mapê, te ciuæki
			Log.d(MainActivity.DEBUG_TAG, "gmap nie jest nullem");
			
			Marker mHamburg = gmap.addMarker(new MarkerOptions().position(
					HAMBURG).title("Hamburg"));
			Marker kiel = gmap.addMarker(new MarkerOptions()
					.position(KIEL)
					.title("Kiel")
					.snippet("Kiel is cool")
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
			
			gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15)); // przesuwa kamerê do markera, z zoomem 15
			
			// Zoom in, animating the camera.
			gmap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
		}*/
		
		
	}

	private void domyslnaMapa(GoogleMap map) {
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(domyslnaPozycja, domyslnyZoom));
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		map.setMyLocationEnabled(true);
		map.getUiSettings().setRotateGesturesEnabled(false); // blokowanie obracania gestem
		map.getUiSettings().setZoomControlsEnabled(true);
		map.getUiSettings().setMapToolbarEnabled(false);
		map.getUiSettings().setTiltGesturesEnabled(false);
	}
	
	private void dodajMarkery(GoogleMap map) {
		map.addMarker(new MarkerOptions()
		.position(domyslnaPozycja)
		.title("Centrówka!"))
		.setSnippet("Du¿y opis rikitiki");
		
		LatLng obok = new LatLng(52, 21);
		LatLng obok2 = new LatLng(52, 21.1);
		LatLng obok3 = new LatLng(52, 21.2);
		
		Marker drugiMarkerek = map.addMarker(new MarkerOptions().title("Marker drugi").snippet("No i du¿y opis").position(obok));
		Marker trzeciMarkerek = map.addMarker(new MarkerOptions().position(obok2).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
		Marker czwartyMarkerek = map.addMarker(new MarkerOptions().position(obok3).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)).title("Marker czwarty"));
		
		czwartyMarkerek.showInfoWindow();
		
	}

	@Override
	public void onMapReady(GoogleMap map) {
		domyslnaMapa(map);
	    dodajMarkery(map);
	    
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
		
		szerokosc *= 100;	// mnozenie *100, zaokraglanie, dzielenie przez 100 = zaokraglanie do 2 miejsca po przecinku
		dlugosc *= 100;
		szerokosc = Math.round(pozycja.latitude) / 100;
		dlugosc = Math.round(pozycja.longitude) / 100;
		
		Toast.makeText(context, "onMapClick: szer.: "+pozycja.latitude+" d³.: "+dlugosc, 
				Toast.LENGTH_LONG).show();
		
		Log.d(DEBUG_TAG, "onMapClick: szer. :");
		
	}
	@Override
	public void onMapLongClick(LatLng pozycja) {
		Toast.makeText(context, 
				pozycja.toString(), 
				Toast.LENGTH_LONG).show();

	}
	
	@Override
	public void onInfoWindowClick(Marker marker) {
		Toast.makeText(context, 
				"Info Window clicked@" + marker.getId(), 
				Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onResume() {
		super.onResume();

		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

		if (resultCode == ConnectionResult.SUCCESS){
			Toast.makeText(context, 
					"isGooglePlayServicesAvailable SUCCESS", 
					Toast.LENGTH_LONG).show(); 
		}else{
			GooglePlayServicesUtil.getErrorDialog(resultCode, this, RQS_GooglePlayServices); 
		}
	}


	
	
}
