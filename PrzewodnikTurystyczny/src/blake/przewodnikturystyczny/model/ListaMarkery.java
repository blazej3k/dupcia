package blake.przewodnikturystyczny.model;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;

public class ListaMarkery {
	List<LatLng> pozycje = new ArrayList<LatLng>();
	List<String> opisy = new ArrayList<String>();
	
	public ListaMarkery() {	}

	public ListaMarkery(List<LatLng> pozycje, List<String> opisy) {
		this.pozycje = pozycje;
		this.opisy = opisy;
	}
	
	public ArrayList getLista() {
		return null;
//		return new ArrayList<List<LatLng>, List<String>>();
	}
	
	
}
