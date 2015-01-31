package blake.przewodnikturystyczny.activity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.DialogFragment;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import blake.przewodnikturystyczny.R;
import blake.przewodnikturystyczny.baza.model.IfMarkierable;
import blake.przewodnikturystyczny.baza.model.TabBudynek;
import blake.przewodnikturystyczny.baza.model.TabMiejsce;
import blake.przewodnikturystyczny.baza.model.TabRzecz;
import blake.przewodnikturystyczny.baza.model.TabWydarzenie;
import blake.przewodnikturystyczny.mapa.ObslugaMapy;
import blake.przewodnikturystyczny.model.ExListaAdapter;
import blake.przewodnikturystyczny.model.ExListaGrupa;

import com.activeandroid.query.Select;

public class DialogSzczegolyFragment extends DialogFragment {
	
/*	private class TVComparator implements Comparator<TextView> {
		@Override
		public int compare(TextView m1, TextView m2) {
			String title1 = m1.getText().toString();
			String title2 = m2.getText().toString();
			
			return title1.compareTo(title2);		// tu u¿ycie standardowego komparatora do Stringów, jest bardzo dobry i skuteczny
		}
	}*/
	
	public static final String DEBUG_TAG = "Przewodnik";
	
	private TextView tv_dialog_nazwa;
	private TextView tv_dialog_pole1;
	private TextView tv_dialog_pole2;
	private TextView tv_dialog_pole3;
//	private TextView tv_dialog_pole4;
//	private TextView tv_dialog_pole5;
	private TextView tv_dialog_opis;
	private TextView lbl_dialog_pole1;
	private TextView lbl_dialog_pole2;
	private TextView lbl_dialog_pole3;
//	private TextView lbl_dialog_pole4;
//	private TextView lbl_dialog_pole5;
	private TextView lbl_dialog_opis;
	private Button btn_pokaz_na_mapie;
	private ExpandableListView expLista;
	private ExListaAdapter adapter;
	
	private final String[][] etykiety = { 
			{ "Adres:", "Projektant:", "Data powstania:", "Opis:" }, 
			{ "Data powstania:", "Rodzaj:", "Opis:" },
			{ "Data rozpoczêcia:", "Data zakoñczenia:", "Opis:" }
			};
	
	private Boolean czyBudynek;
	private String nazwaObiektu;
	private List<ExListaGrupa> grupyLista = new LinkedList<ExListaGrupa>();
//	private TreeMap<TextView, TextView> zestawDane = new TreeMap<TextView, TextView>(new TVComparator());
	List<TextView> lblLista = new ArrayList<TextView>();
	List<TextView> tvLista = new ArrayList<TextView>();
	
	private ObslugaMapy obslugaMapy;
	private String aktualnyObiekt;
	private String aktualnyObiektTyp;
	private Boolean aktualnyObiektCzyBudynek; 
	
	public DialogSzczegolyFragment() {
		// pusty konstruktor podobno wymagany, ale dziala i bez
	}
	
	public DialogSzczegolyFragment(String nazwaObiektu, ObslugaMapy obslugaMapy) {
		this.nazwaObiektu = nazwaObiektu;
		this.obslugaMapy = obslugaMapy;
	}
	
	public DialogSzczegolyFragment(String nazwaObiektu, Boolean czyBudynek, ObslugaMapy obslugaMapy) {
		this.czyBudynek = czyBudynek;
		this.nazwaObiektu = nazwaObiektu;
		this.obslugaMapy = obslugaMapy;
	}
	
	private void wypelnijDialog() {
		if(czyBudynek)
			pobierzBudynek(nazwaObiektu);
		else if(!czyBudynek)
			pobierzMiejsce(nazwaObiektu);
	}
	
	private <T extends IfMarkierable> void ustawPola(T obiekt) {			// dla miejsc i budynkow
		if(obiekt == null)
			Log.d(DEBUG_TAG, "jebany obiekt null");
		else
			Log.d(DEBUG_TAG, "Dialog ustaw obiekt: "+obiekt.getNazwa());
		
		wyczyscPola();														// sprzata po poprzedniku
		tvLista.add(tv_dialog_pole1);
		tvLista.add(tv_dialog_pole2);
		tvLista.add(tv_dialog_pole3);
		tvLista.add(tv_dialog_opis);
		
		lblLista.add(lbl_dialog_pole1);
		lblLista.add(lbl_dialog_pole2);
		lblLista.add(lbl_dialog_pole3);
		lblLista.add(lbl_dialog_opis);
		
		
		for (TextView lbl: lblLista) {										// ustawia opisy labeli
			Log.d(DEBUG_TAG, "Numer etykiety: "+lblLista.indexOf(lbl));
			lbl.setText(etykiety[0][lblLista.indexOf(lbl)]);
		}
		
		tv_dialog_nazwa.setText(obiekt.getNazwa());
		tv_dialog_pole1.setText(obiekt.getAdres());
		tv_dialog_pole2.setText(obiekt.getProjektant());
		tv_dialog_pole3.setText(obiekt.getDataPowstania());
		tv_dialog_opis.setText(obiekt.getOpis());
		
//		zestawDane.put(lbl_dialog_pole1, tv_dialog_pole1);
//		zestawDane.put(lbl_dialog_pole2, tv_dialog_pole2);
//		zestawDane.put(lbl_dialog_pole3, tv_dialog_pole3);
//		zestawDane.put(lbl_dialog_opis, tv_dialog_opis);
		
		ustawWidocznosc();
	}
	
	private void ustawPolaRzecz(TabRzecz rzecz) {
		if(rzecz == null)
			Log.d(DEBUG_TAG, "jebana rzecz null");
		else
			Log.d(DEBUG_TAG, "Dialog ustaw obiekt: "+rzecz.getNazwa());
		
		wyczyscPola();														// sprzata po poprzedniku
		tvLista.add(tv_dialog_pole1);
		tvLista.add(tv_dialog_pole2);
		tvLista.add(tv_dialog_opis);
		
		lblLista.add(lbl_dialog_pole1);
		lblLista.add(lbl_dialog_pole2);
		lblLista.add(lbl_dialog_opis);
		
		for (int i=0; i < etykiety[1].length; i++) {						// ustawia opisy labeli
			lblLista.get(i).setText(etykiety[1][i]);
		}
		
		tv_dialog_nazwa.setText(rzecz.getNazwa());
		tv_dialog_pole1.setText(rzecz.getDataPowstania());
		tv_dialog_pole2.setText(rzecz.getRodzaj());
		tv_dialog_opis.setText(rzecz.getOpis());
		
//		zestawDane.put(lbl_dialog_pole1, tv_dialog_pole1);
//		zestawDane.put(lbl_dialog_pole3, tv_dialog_pole3);
//		zestawDane.put(lbl_dialog_pole4, tv_dialog_pole4);
//		zestawDane.put(lbl_dialog_opis, tv_dialog_opis);
		
		ustawWidocznosc();
	}
	
	private void ustawPolaWydarzenie(TabWydarzenie wydarzenie) {
		if(wydarzenie == null)
			Log.d(DEBUG_TAG, "jebane wydarzenie null");
		else
			Log.d(DEBUG_TAG, "Dialog ustaw obiekt: "+wydarzenie.getNazwa());
		
		wyczyscPola();														// sprzata po poprzedniku
		tvLista.add(tv_dialog_pole1);
		tvLista.add(tv_dialog_pole2);
		tvLista.add(tv_dialog_opis);
		
		lblLista.add(lbl_dialog_pole1);
		lblLista.add(lbl_dialog_pole2);
		lblLista.add(lbl_dialog_opis);
		
		for (int i=0; i < etykiety[2].length; i++) {					// ustawia opisy labeli
			lblLista.get(i).setText(etykiety[2][i]);
		}		
		
		tv_dialog_nazwa.setText(wydarzenie.getNazwa());
		tv_dialog_pole1.setText(wydarzenie.getDataPoczatek());
		tv_dialog_pole2.setText(wydarzenie.getDataKoniec());
		tv_dialog_opis.setText(wydarzenie.getOpis());
		
//		zestawDane.put(lbl_dialog_pole1, tv_dialog_pole1);
//		zestawDane.put(lbl_dialog_pole3, tv_dialog_pole3);
//		zestawDane.put(lbl_dialog_opis, tv_dialog_opis);
		
		ustawWidocznosc();
	}
	
	private void wypelnijListeBudynek(TabBudynek budynek) {				// mozna dopisac relacje z rodami, postaciami i rzeczami
		List<TabMiejsce> miejsca = budynek.getMiejsca();
		ExListaGrupa grupa = null;
		
		if (miejsca != null && !miejsca.isEmpty()) {
			for (TabMiejsce miejsce: miejsca) {
				grupa = new ExListaGrupa("Miejsca");
				grupa.elementy.add(miejsce.getNazwa());
			}
			grupyLista.add(grupa);
		}
		
	}
	
	private void wypelnijListeMiejsce(TabMiejsce miejsce) {				// mozna dopisac jeszcze relacje z postaciami i rodami
		TabBudynek budynek = miejsce.getBudynek();
		List<TabRzecz> rzeczy = miejsce.getRzeczy();
		List<TabWydarzenie> wydarzenia = miejsce.getWydarzenia();
		ExListaGrupa grupa = null;
		
		if (budynek != null) {
			grupa = new ExListaGrupa("Budynki");
			grupa.elementy.add(budynek.getNazwa());
			grupyLista.add(grupa);
		}

		if(rzeczy != null && !rzeczy.isEmpty()) {
			grupa = new ExListaGrupa("Rzeczy");

			for (TabRzecz rzecz: rzeczy) 
				grupa.elementy.add(rzecz.getNazwa());
			grupyLista.add(grupa);
		}
		
		if(wydarzenia != null && !wydarzenia.isEmpty()) {
			grupa = new ExListaGrupa("Wydarzenia");

			for (TabWydarzenie wydarzenie: wydarzenia) 
				grupa.elementy.add(wydarzenie.getNazwa());
			grupyLista.add(grupa);
		}
	}
	
	private void wypelnijListeRzecz(TabRzecz rzecz) {				// mozna dopisac relacje z rodami, postaciami i rzeczami
		TabMiejsce miejsce = rzecz.getMiejsce();
		ExListaGrupa grupa = null;
		
		if (miejsce != null) {
			grupa = new ExListaGrupa("Miejsca");
			grupa.elementy.add(miejsce.getNazwa());
			grupyLista.add(grupa);
		}
		
	}
	
	private void wypelnijListeWydarzenie(TabWydarzenie wydarzenie) { // mozna dopisac relacje z rodami, postaciami i rzeczami
		List<TabMiejsce> miejsca = wydarzenie.getMiejsca();
//		List<TabRzecz> rzeczy = wydarzenie.getRzeczy();
		ExListaGrupa grupa = null;
		
		if (miejsca != null && !miejsca.isEmpty()) {
			grupa = new ExListaGrupa("Miejsca");
			for (TabMiejsce miejsce: miejsca) {
				grupa.elementy.add(miejsce.getNazwa());
			}
			grupyLista.add(grupa);
		}
//		if (rzeczy != null) {
//			grupa = new ExListaGrupa("Rzeczy");
//			for (TabRzecz rzecz: rzeczy) {
//				grupa.elementy.add(rzecz.getNazwa());
//			}
//			grupyLista.append(i, grupa);
//			i++;
//		}
	}
	
	private void pobierzBudynek(String nazwa) {
		Log.d(DEBUG_TAG, "Dialog: pobieram budynek "+nazwa);
		TabBudynek budynek = new Select().from(TabBudynek.class).where("nazwa = ?", nazwa).executeSingle();
		
		ustawPola(budynek);
		wypelnijListeBudynek(budynek);
	}
	
	private void pobierzMiejsce(String nazwa) {
		Log.d(DEBUG_TAG, "Dialog: pobieram miejsce "+nazwa);
		TabMiejsce miejsce = new Select().from(TabMiejsce.class).where("nazwa = ?", nazwa).executeSingle();
		aktualnyObiekt = nazwa;
		aktualnyObiektTyp = "Miejsca";
		aktualnyObiektCzyBudynek = false;
		
		ustawPola(miejsce);
		wypelnijListeMiejsce(miejsce);
	}
	
	private void pobierzRzecz(String nazwa) {
		Log.d(DEBUG_TAG, "Dialog: pobieram Rzecz "+nazwa);
		TabRzecz rzecz = new Select().from(TabRzecz.class).where("nazwa = ?", nazwa).executeSingle();
		
		ustawPolaRzecz(rzecz);
		wypelnijListeRzecz(rzecz);
	}
	
	private void pobierzWydarzenie(String nazwa) {
		Log.d(DEBUG_TAG, "Dialog: pobieram Wydarzenie "+nazwa);
		TabWydarzenie wydarzenie = new Select().from(TabWydarzenie.class).where("nazwa = ?", nazwa).executeSingle();
		
		ustawPolaWydarzenie(wydarzenie);
		wypelnijListeWydarzenie(wydarzenie);
	}
	
	
	
	private void wyczyscPola() {
		for (TextView tv: tvLista) {
			tv.setText("");
			tv.setVisibility(View.GONE);
		}

		for (TextView tv: lblLista) {
			tv.setText("");
			tv.setVisibility(View.GONE);
		}
		tvLista.clear();					// czysci od razu listy, zevby potem widocznosc dobrzer sie ustawiala, tylko dla wlasciwych pol
		lblLista.clear();
	}
	
	private void ustawWidocznosc() {
//		for(TreeMap.Entry<TextView, TextView> entry : zestawDane.entrySet()) {
//			TextView lbl = entry.getKey();
//			TextView tv = entry.getValue();
//
//			if (!tv.getText().equals("")) {
//				tv.setVisibility(View.VISIBLE);
//				lbl.setVisibility(View.VISIBLE);
//			}
//		}

		for (TextView tv: tvLista) {					// tym co maja pola ustaw visible, reszta gone
			if (!tv.getText().equals("")) {
				tv.setVisibility(View.VISIBLE);
				lblLista.get(tvLista.indexOf(tv)).setVisibility(View.VISIBLE);
//				Log.d(DEBUG_TAG, "ustawiam widocznoœæ dla tv: "+tv.getText()+" oraz lbl: "+lblLista.get(tvLista.indexOf(tv)).getText());
			}
//			else {
//				tv.setVisibility(View.GONE);
//				lblLista.get(tvLista.indexOf(tv)).setVisibility(View.GONE);
//			}
		}
	}

	private void initOnChildClickListeners() {
		OnChildClickListener onChildClickListener = new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,	int groupPosition, int childPosition, long id) {
				String grupa = grupyLista.get(groupPosition).nazwa;
				String element = grupyLista.get(groupPosition).elementy.get(childPosition);

				Log.d(DEBUG_TAG, "Ja listener! grupa: "+grupa+" element: "+element);
				
				for (ExListaGrupa g: grupyLista)
					g.elementy.clear();
				
				grupyLista.clear();
				
				if (grupa.equals("Budynki")) {
					pobierzBudynek(element);
				}
				else if (grupa.equals("Miejsca")) {
					pobierzMiejsce(element);
				}
				else if(grupa.equals("Rzeczy")){
					pobierzRzecz(element);
				}
				else if(grupa.equals("Wydarzenia")) {
					pobierzWydarzenie(element);
				}
				adapter.notifyDataSetChanged();
//				adapter.notifyDataSetInvalidated();
				
				for (int i=0; i < grupyLista.size(); i++)
					parent.collapseGroup(i);

				return true;
			}
		};
		
		expLista.setOnChildClickListener(onChildClickListener);
	}
	
	private void initOnClickListeners() {
		OnClickListener btnListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				obslugaMapy.dodajMarkier(aktualnyObiekt, aktualnyObiektTyp, aktualnyObiektCzyBudynek);

			}
		};
		
		btn_pokaz_na_mapie.setOnClickListener(btnListener);
	}
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//      View view = inflater.inflate(R.layout.custom_dialog_szczegoly_obiektu, container);		// wczytuje layout z .xml-a, ma byæ null, Google tak ka¿e
		View view = inflater.inflate(R.layout.custom_dialog_szczegoly_obiektu, null);

		Rect displayRectangle = new Rect();
		Window window = getActivity().getWindow();
		window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

		view.setMinimumWidth((int)(displayRectangle.width() * 0.99f));				// tak jest dobrze, marginesy trzymaja ten obraz w ryzach i wyglada ladnie.
		view.setMinimumHeight((int)(displayRectangle.height() * 0.75f));

		tv_dialog_nazwa = (TextView) view.findViewById(R.id.tv_dialog_nazwa);
		tv_dialog_pole1 = (TextView) view.findViewById(R.id.tv_dialog_pole1);
		tv_dialog_pole2 = (TextView) view.findViewById(R.id.tv_dialog_pole2);
		tv_dialog_pole3 = (TextView) view.findViewById(R.id.tv_dialog_pole3);
//		tv_dialog_pole4 = (TextView) view.findViewById(R.id.tv_dialog_pole4);
//		tv_dialog_pole5 = (TextView) view.findViewById(R.id.tv_dialog_pole5);
		tv_dialog_opis = (TextView) view.findViewById(R.id.tv_dialog_opis);
		lbl_dialog_pole1 = (TextView) view.findViewById(R.id.lbl_dialog_pole1);
		lbl_dialog_pole2 = (TextView) view.findViewById(R.id.lbl_dialog_pole2);
		lbl_dialog_pole3 = (TextView) view.findViewById(R.id.lbl_dialog_pole3);
//		lbl_dialog_pole4 = (TextView) view.findViewById(R.id.lbl_dialog_pole4);
//		lbl_dialog_pole5 = (TextView) view.findViewById(R.id.lbl_dialog_pole5);
		lbl_dialog_opis = (TextView) view.findViewById(R.id.lbl_dialog_opis);
		expLista = (ExpandableListView) view.findViewById(R.id.ex_lista_szczegoly);
		btn_pokaz_na_mapie = (Button) view.findViewById(R.id.btn_pokaz_na_mapie);

		wypelnijDialog();
		adapter = new ExListaAdapter(this, grupyLista);
		expLista.setAdapter(adapter);
		
		initOnChildClickListeners();
		initOnClickListeners();

		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setStyle(STYLE_NO_TITLE, 0);
		super.onCreate(savedInstanceState);
	}
}

/*    @Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    LayoutInflater inflater = getActivity().getLayoutInflater();
    View view = inflater.inflate(R.layout.custom_dialog_szczegoly_obiektu, null);		// ma byæ null bo Google tak ka¿e
    
    Rect displayRectangle = new Rect();
    Window window = getActivity().getWindow();
    window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

    view.setMinimumWidth((int)(displayRectangle.width() * 0.85f));
    view.setMinimumHeight((int)(displayRectangle.height() * 0.5f));
    
    builder.setView(view);
    
    tv_dialog_nazwa = (TextView) view.findViewById(R.id.tv_dialog_nazwa);
    tv_dialog_pole1 = (TextView) view.findViewById(R.id.tv_dialog_pole1);
    tv_dialog_pole2 = (TextView) view.findViewById(R.id.tv_dialog_pole2);
    tv_dialog_pole3 = (TextView) view.findViewById(R.id.tv_dialog_pole3);
    tv_dialog_pole4 = (TextView) view.findViewById(R.id.tv_dialog_pole4);
    tv_dialog_pole5 = (TextView) view.findViewById(R.id.tv_dialog_pole5);
    tv_dialog_opis = (TextView) view.findViewById(R.id.tv_dialog_opis);
    lbl_dialog_pole1 = (TextView) view.findViewById(R.id.lbl_dialog_pole1);
    lbl_dialog_pole2 = (TextView) view.findViewById(R.id.lbl_dialog_pole2);
    lbl_dialog_pole3 = (TextView) view.findViewById(R.id.lbl_dialog_pole3);
    lbl_dialog_pole4 = (TextView) view.findViewById(R.id.lbl_dialog_pole4);
    lbl_dialog_pole5 = (TextView) view.findViewById(R.id.lbl_dialog_pole5);
    lbl_dialog_opis = (TextView) view.findViewById(R.id.lbl_dialog_opis);
    
    if(czyBudynek)
		pobierzBudynek(nazwaObiektu);
	else if(!czyBudynek)
		pobierzMiejsce(nazwaObiektu);
    
    return builder.create();
}*/


/*
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
  View view = getActivity().getLayoutInflater().inflate(R.layout.custom_dialog_szczegoly_obiektu, container);		// wczytuje layout z .xml-a, ma byæ null, Google tak ka¿e
//  View x = getDialog().
  tv_dialog_nazwa = (TextView) view.findViewById(R.id.tv_dialog_nazwa);
  tv_dialog_nazwa.setText("Spermidon");
  
  
  // Create the AlertDialog object and return it
  
  return view;
}
*/
/*builder.setMessage(R.string.demo_str)
.setPositiveButton(R.string.demo_str, new DialogInterface.OnClickListener() {
    public void onClick(DialogInterface dialog, int id) {
        // FIRE ZE MISSILES!
    }
})
.setNegativeButton(R.string.demo_str, new DialogInterface.OnClickListener() {
    public void onClick(DialogInterface dialog, int id) {
        // User cancelled the dialog
    }
});*/

