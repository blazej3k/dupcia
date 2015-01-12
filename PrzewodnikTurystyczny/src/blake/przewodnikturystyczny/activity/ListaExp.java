/*package blake.przewodnikturystyczny.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.ExpandableListView;
import blake.przewodnikturystyczny.R;
import blake.przewodnikturystyczny.baza.model.TabBudynek;
import blake.przewodnikturystyczny.baza.model.TabMiejsce;
import blake.przewodnikturystyczny.baza.model.TabRzecz;
import blake.przewodnikturystyczny.baza.model.TabWydarzenie;
import blake.przewodnikturystyczny.model.ExListaAdapter;
import blake.przewodnikturystyczny.model.ExListaGrupa;

import com.activeandroid.query.Select;

public class ListaExp extends Activity {

	SparseArray<ExListaGrupa> grupyLista = new SparseArray<ExListaGrupa>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_exp);

		wypelnijListe();
		ExpandableListView listView = (ExpandableListView) findViewById(R.id.ex_lista_szczegoly);
		ExListaAdapter adapter = new ExListaAdapter(this, grupyLista);
		listView.setAdapter(adapter);
	}


	public void wypelnijListe() {
		TabMiejsce miejsce = new Select().from(TabMiejsce.class).where("nazwa = ?", "Ustro").executeSingle();
		TabBudynek budynek = miejsce.getBudynek();
		List<TabRzecz> rzeczy = miejsce.getRzeczy();
		List<TabWydarzenie> wydarzenia = miejsce.getWydarzenia();
		ExListaGrupa grupa;
		int i=0;
		
		if (budynek != null) {
			grupa = new ExListaGrupa("Budynek");
			grupa.elementy.add(budynek.getNazwa());
			grupyLista.append(i, grupa);
			i++;
		}

		if(rzeczy != null && !rzeczy.isEmpty()) {
			grupa = new ExListaGrupa("Rzeczy");

			for (TabRzecz rzecz: rzeczy) 
				grupa.elementy.add(rzecz.getNazwa());
			grupyLista.append(i, grupa);
			i++;
		}
		
		if(wydarzenia != null && !wydarzenia.isEmpty()) {
			grupa = new ExListaGrupa("Wydarzenia");

			for (TabWydarzenie wydarzenie: wydarzenia) 
				grupa.elementy.add(wydarzenie.getNazwa());
			grupyLista.append(i, grupa);
			i++;
		}
	}
}
*/