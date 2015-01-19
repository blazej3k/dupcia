package blake.przewodnikturystyczny.model;

import java.util.List;

import android.content.Context;
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

	public KategorieListaAdapter(Context context, int layoutResourceId, List<String> pozycje, Boolean czyKategorie) {
		super(context, layoutResourceId, pozycje);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.pozycje = pozycje;
		this.czyKategorie = czyKategorie;
	}

	public int getCount() {
		return pozycje.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolderPattern view_holder;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(layoutResourceId, parent, false);

			view_holder = new ViewHolderPattern();
			view_holder.tv_kategoria = (TextView) convertView.findViewById(R.id.tv_row_obiekt);

			convertView.setTag(view_holder);
		}
		else {
			view_holder = (ViewHolderPattern) convertView.getTag();
		} 

		view_holder.tv_kategoria.setText(pozycje.get(position));

		if (!czyKategorie)
			view_holder.tv_kategoria.setCompoundDrawablesWithIntrinsicBounds(R.drawable.zolty, 0, 0, 0);

		return convertView;
	}

	private class ViewHolderPattern {
		TextView tv_kategoria;
	}
}
