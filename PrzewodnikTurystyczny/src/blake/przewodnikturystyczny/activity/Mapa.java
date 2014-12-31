package blake.przewodnikturystyczny.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import blake.przewodnikturystyczny.R;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapa extends Activity implements OnMapReadyCallback {

	static final LatLng domyslnaPozycja = new LatLng(52.23, 21);  // pozycja Warszawy
	static final int domysnyZoom = 12;
	static final LatLng KIEL = new LatLng(53.551, 9.993);

	private Context context;
	
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

	@Override
	public void onMapReady(GoogleMap map) {
	    map.addMarker(new MarkerOptions()
        .position(domyslnaPozycja)
        .title("Centrówka!"));

	    domyslnaMapa(map);
	}
	
	private void domyslnaMapa(GoogleMap map) {
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(domyslnaPozycja, domysnyZoom));
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		map.getUiSettings().setCompassEnabled(true);
		        
        
/*        map:cameraTilt="30"
        map:cameraZoom="13"
        map:mapType="normal"
        map:uiCompass="false"
        map:uiRotateGestures="true"
        map:uiScrollGestures="false"
        map:uiTiltGestures="true"
        map:uiZoomControls="false"
        map:uiZoomGestures="true" >*/
	}
}
