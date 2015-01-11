package blake.przewodnikturystyczny.activity;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import android.app.DialogFragment;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import blake.przewodnikturystyczny.R;
import blake.przewodnikturystyczny.baza.model.IfMarkierable;
import blake.przewodnikturystyczny.baza.model.TabBudynek;
import blake.przewodnikturystyczny.baza.model.TabMiejsce;

import com.activeandroid.query.Select;

public class DialogSzczegolyFragment extends DialogFragment {
	
	private class TVComparator implements Comparator<TextView> {
		@Override
		public int compare(TextView m1, TextView m2) {
			String title1 = m1.getText().toString();
			String title2 = m2.getText().toString();
			
			return title1.compareTo(title2);		// tu u¿ycie standardowego komparatora do Stringów, jest bardzo dobry i skuteczny
		}
	}
	
	
	public static final String DEBUG_TAG = "Przewodnik";
	
	private TextView tv_dialog_nazwa;
	private TextView tv_dialog_pole1;
	private TextView tv_dialog_pole2;
	private TextView tv_dialog_pole3;
	private TextView tv_dialog_pole4;
	private TextView tv_dialog_pole5;
	private TextView tv_dialog_opis;
	private TextView lbl_dialog_pole1;
	private TextView lbl_dialog_pole2;
	private TextView lbl_dialog_pole3;
	private TextView lbl_dialog_pole4;
	private TextView lbl_dialog_pole5;
	private TextView lbl_dialog_opis;
	
	private TreeMap<TextView, TextView> zestawDane;
	private Boolean czyBudynek;
	private String nazwaObiektu;
	
	public DialogSzczegolyFragment() {
		// pusty konstruktor podobno wymagany, ale dziala i bez
	}
	
	public DialogSzczegolyFragment(String nazwaObiektu, Boolean czyBudynek) {
		this.czyBudynek = czyBudynek;
		this.nazwaObiektu = nazwaObiektu;
		
		zestawDane = new TreeMap<TextView, TextView>(new TVComparator());
	}
	
	private void pobierzBudynek(String nazwa) {
		Log.d(DEBUG_TAG, "Dialog: pobieram budynek "+nazwa);
		TabBudynek budynek = new Select().from(TabBudynek.class).where("nazwa = ?", nazwa).executeSingle();
				
		ustawDialog(budynek);
	}
	
	private void pobierzMiejsce(String nazwa) {
		Log.d(DEBUG_TAG, "Dialog: pobieram miejsce "+nazwa);
		TabMiejsce miejsce = new Select().from(TabMiejsce.class).where("nazwa = ?", nazwa).executeSingle();
		
		ustawDialog(miejsce);
	}
	
	private <T extends IfMarkierable> void ustawDialog(T obiekt) {
		if(obiekt == null)
			Log.d(DEBUG_TAG, "jebany obiekt null");
		else
			Log.d(DEBUG_TAG, "Dialog ustaw obiekt: "+obiekt.getNazwa());
		
		tv_dialog_nazwa.setText(obiekt.getNazwa());
		tv_dialog_pole1.setText(obiekt.getAdres());
		tv_dialog_pole2.setText(obiekt.getProjektant());
		tv_dialog_pole3.setText(obiekt.getDataPowstania());
		tv_dialog_opis.setText(obiekt.getOpis());
		
		zestawDane.put(lbl_dialog_pole1, tv_dialog_pole1);
		zestawDane.put(lbl_dialog_pole2, tv_dialog_pole2);
		zestawDane.put(lbl_dialog_pole3, tv_dialog_pole3);
		zestawDane.put(lbl_dialog_opis, tv_dialog_opis);
		
	/*	tvLista.add(tv_dialog_nazwa);
		tvLista.add(tv_dialog_pole1);
		tvLista.add(tv_dialog_pole2);
		tvLista.add(tv_dialog_pole3);
		tvLista.add(tv_dialog_opis);
		
		lblLista.add(lbl_dialog_pole1);
		lblLista.add(lbl_dialog_pole2);
		lblLista.add(lbl_dialog_pole3);
		lblLista.add(lbl_dialog_opis);*/
		
		ustawWidocznosc();
	}
	
	private void ustawWidocznosc() {
		for(TreeMap.Entry<TextView, TextView> entry : zestawDane.entrySet()) {
			TextView lbl = entry.getKey();
			TextView tv = entry.getValue();

			if (!tv.getText().equals("")) {
				tv.setVisibility(View.VISIBLE);
				lbl.setVisibility(View.VISIBLE);
			}
		}
		
		
		/*for (TextView tv: tvLista) {
			if (!tv.getText().equals("")) {
				tv.setVisibility(View.VISIBLE);				// ustawia wszystkie niepuste jako widoczne, zeby puste znikaly
			}
		}
		*/	
	}


    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//      View view = inflater.inflate(R.layout.custom_dialog_szczegoly_obiektu, container);		// wczytuje layout z .xml-a, ma byæ null, Google tak ka¿e
		View view = inflater.inflate(R.layout.custom_dialog_szczegoly_obiektu, null);

		Rect displayRectangle = new Rect();
		Window window = getActivity().getWindow();
		window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

		view.setMinimumWidth((int)(displayRectangle.width() * 0.85f));
		view.setMinimumHeight((int)(displayRectangle.height() * 0.6f));

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

