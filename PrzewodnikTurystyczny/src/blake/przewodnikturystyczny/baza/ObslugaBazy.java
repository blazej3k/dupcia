package blake.przewodnikturystyczny.baza;

import java.util.LinkedList;

import android.util.Log;
import blake.przewodnikturystyczny.baza.model.IfSelectable;
import blake.przewodnikturystyczny.baza.model.TabBranza;
import blake.przewodnikturystyczny.baza.model.TabBudynek;
import blake.przewodnikturystyczny.baza.model.TabMiejsce;
import blake.przewodnikturystyczny.baza.model.TabOkres;
import blake.przewodnikturystyczny.baza.model.TabPostac;
import blake.przewodnikturystyczny.baza.model.TabRod;
import blake.przewodnikturystyczny.baza.model.TabRzecz;
import blake.przewodnikturystyczny.baza.model.TabWydarzenie;
import blake.przewodnikturystyczny.baza.model.pomocniczy.TabMiejsceRzecz;
import blake.przewodnikturystyczny.baza.model.pomocniczy.TabMiejsceWydarzenie;

import com.activeandroid.Model;
import com.activeandroid.query.Select;

public class ObslugaBazy {

	public static final String DEBUG_TAG = "Przewodnik";

	String[] tabeleS = {
			"TabOkres.class",
			"TabBranza.class",
			"TabBudynek.class",
			"TabMiejsce.class",
			"TabWydarzenie.class",
			"TabPostac.class",
			"TabRzecz.class",
			"TabRod.class",
			"TabMiejsceWydarzenie.class"
	};
	private LinkedList<Class<? extends Model>> tabele;

	public ObslugaBazy() {
		tabele = new LinkedList<Class<? extends Model>>();
		tabele.add(TabBudynek.class);
		tabele.add(TabMiejsce.class);
		tabele.add(TabWydarzenie.class);
		tabele.add(TabPostac.class);
		tabele.add(TabRzecz.class);
		tabele.add(TabRod.class);
		tabele.add(TabOkres.class);
		tabele.add(TabBranza.class);
		tabele.add(TabMiejsceWydarzenie.class);
		tabele.add(TabMiejsceRzecz.class);
	}

	public void selectAll() {
//		for (Model x : new Select().all().from(TabOkres.class).execute()) {
//			Log.d(DEBUG_TAG, ((TabOkres) x).getNazwa());
//		}
//		for (Model x : new Select().all().from(TabBranza.class).execute()) {
//			Log.d(DEBUG_TAG, ((TabBranza) x).getNazwa());
//		}
//		for (Model x : new Select().all().from(TabBudynek.class).execute()) {
//			Log.d(DEBUG_TAG, ((TabBudynek) x).getNazwa());
//		}
//		for (Model x : new Select().all().from(TabMiejsce.class).execute()) {
//			Log.d(DEBUG_TAG, ((TabMiejsce) x).getNazwa());
//		}
//		for (Model x : new Select().all().from(TabWydarzenie.class).execute()) {
//			Log.d(DEBUG_TAG, ((TabWydarzenie) x).getNazwa());
//		}
//		for (Model x : new Select().all().from(TabPostac.class).execute()) {
//			Log.d(DEBUG_TAG, ((TabPostac) x).getNazwa());
//		}
//		for (Model x : new Select().all().from(TabRzecz.class).execute()) {
//			Log.d(DEBUG_TAG, ((TabRzecz) x).getNazwa());
//		}
//		for (Model x : new Select().all().from(TabRod.class).execute()) {
//			Log.d(DEBUG_TAG, ((TabRod) x).getNazwa());
//		}
//		for (Model x: new Select().all().from(TabMiejsceWydarzenie.class).execute()) {
//			Log.d(DEBUG_TAG, Long.toString(((TabMiejsceWydarzenie) x).getWydarzenie_id()));
//		}

		for (Class<? extends Model> x: tabele) {
			String nazwaKlasy=x.getName().substring(39);
			for (Model y: new Select().all().from((Class<? extends Model>) x).execute()) {
				Log.d(DEBUG_TAG, nazwaKlasy+": ID="+((IfSelectable) y).getId()+ ": " +((IfSelectable) y).getNazwa());
			}
		}
	}
	
	public void truncateAll() {
		for (Class<? extends Model> x: tabele) {
			try {
				Model.truncate(x);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
//		TabBudynek.truncate(TabBudynek.class);			// truncate ró¿ni siê od delete tym, ¿e czyœci tabelê (robi delete), ale te¿ resetuje jej ID
//		TabMiejsce.truncate(TabMiejsce.class);			// nowe pozycje z powrotem maj¹ id 1.
//		TabWydarzenie.truncate(TabWydarzenie.class);
//		TabPostac.truncate(TabPostac.class);
//		TabRzecz.truncate(TabRzecz.class);
//		TabRod.truncate(TabRod.class);
//		TabBranza.truncate(TabBranza.class);
//		TabOkres.truncate(TabOkres.class);
//		TabMiejsceWydarzenie.truncate(TabMiejsceWydarzenie.class);
	}
}
