package blake.przewodnikturystyczny.model;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import blake.przewodnikturystyczny.R;

public class KategorieListaAdapter extends ArrayAdapter<String>{

	Context context;
	List<String> pozycje;
	private int layoutResourceId;
	private Boolean czyKategorie;
	String wybranyOkres;
	String wybranaBranza;
	private ViewHolderPattern view_holder;

	public KategorieListaAdapter(Context context, int layoutResourceId, List<String> pozycje, String wybranyOkres, String wybranaBranza, Boolean czyKategorie) {
		super(context, layoutResourceId, pozycje);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.pozycje = pozycje;
		
		this.wybranyOkres = wybranyOkres;
		this.wybranaBranza = wybranaBranza;
		this.czyKategorie = czyKategorie;
	}

	public int getCount() {
		return pozycje.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		ViewHolderPattern view_holder;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(layoutResourceId, parent, false);

			view_holder = new ViewHolderPattern();
			view_holder.tv_row_obiekt = (TextView) convertView.findViewById(R.id.tv_row_obiekt);
			view_holder.tv_row_wybrane = (TextView) convertView.findViewById(R.id.tv_row_wybrane);

			convertView.setTag(view_holder);
		}
		else {
			view_holder = (ViewHolderPattern) convertView.getTag();
		} 

		view_holder.tv_row_obiekt.setText(pozycje.get(position));
		
		if (wybranyOkres != null && !wybranyOkres.equals("") && position == 0) {
			view_holder.tv_row_wybrane.setText(wybranyOkres);
			view_holder.tv_row_wybrane.setVisibility(View.VISIBLE);
		}
		else if (wybranaBranza != null && !wybranaBranza.equals("") && position == 1) {
			view_holder.tv_row_wybrane.setText(wybranaBranza);
			view_holder.tv_row_wybrane.setVisibility(View.VISIBLE);
		}	
			
		

		if (!czyKategorie) 
			view_holder.tv_row_obiekt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.zolty, 0, 0, 0);
		

		
		/*if(wOkres != null && !wOkres.isEmpty()) {
			Log.d("Przewodnik", "Ustawiam widocznosc Wybranych");
			view_holder.tv_row_wybrane.setText(wOkres);
			view_holder.tv_row_wybrane.setVisibility(View.VISIBLE);
			wOkres = null; 	// nulluje ja zeby sie ponownie nie ustawiala, np dla Okresu zeby nie wskoczyla
		}*/
		
		
		return convertView;
	}
	
	public void ustawWybrane(String wybrane, Boolean czyOkres) {
		if(wybrane != null) {
			Log.d("Przewodnik", "Ustawiam widocznosc Wybranych");
			if (czyOkres) 
				wybranyOkres = wybrane;
			else
				wybranaBranza = wybrane;
			//			wybranaBranza = null; 	// nulluje ja zeby sie ponownie nie ustawiala, np dla Okresu zeby nie wskoczyla
		}
	}

	private class ViewHolderPattern {
		TextView tv_row_obiekt;
		TextView tv_row_wybrane;
	}
}
