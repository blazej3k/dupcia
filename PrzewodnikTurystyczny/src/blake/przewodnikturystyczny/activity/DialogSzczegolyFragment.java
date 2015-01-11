package blake.przewodnikturystyczny.activity;

import java.util.LinkedList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import blake.przewodnikturystyczny.R;
import blake.przewodnikturystyczny.baza.model.IfMarkierable;
import blake.przewodnikturystyczny.baza.model.TabBudynek;
import blake.przewodnikturystyczny.baza.model.TabMiejsce;

import com.activeandroid.query.Select;

public class DialogSzczegolyFragment extends DialogFragment {
	
	public static final String DEBUG_TAG = "Przewodnik";
	
	private TextView tv_dialog_nazwa;
	private TextView tv_dialog_pole1;
	private TextView tv_dialog_pole2;
	private TextView tv_dialog_pole3;
	private TextView tv_dialog_pole4;
	private TextView tv_dialog_pole5;
	private TextView tv_dialog_opis;
	private List<TextView> tvLista;
	private Boolean czyBudynek;
	private String nazwaObiektu;
	
	public DialogSzczegolyFragment() {
		// pusty konstruktor podobno wymagany, ale dziala i bez
	}
	
	public DialogSzczegolyFragment(String nazwaObiektu, Boolean czyBudynek) {
		this.czyBudynek = czyBudynek;
		this.nazwaObiektu = nazwaObiektu;
						
		tvLista = new LinkedList<TextView>();
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
		
		tvLista.add(tv_dialog_nazwa);
		tvLista.add(tv_dialog_pole1);
		tvLista.add(tv_dialog_pole2);
		tvLista.add(tv_dialog_pole3);
		tvLista.add(tv_dialog_opis);
		
		ustawWidocznosc(tvLista);
	}
	
	private void ustawWidocznosc(List<TextView> tvLista) {
		for (TextView tv: tvLista) {
				tv.setVisibility(View.VISIBLE);				// ustawia wszystkie jako widoczne, wtedy w zaleznosci od liczby pol (np Miejsce czy Budynek) bedzie sie inaczej formatowalo, ale tez jelsi w bazie dane pole puste, to nie bedzie przesuwac bo puste
		}
	}

    @Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog_szczegoly_obiektu, null);		// ma byæ null bo Google tak ka¿e
        builder.setView(view);
        
        tv_dialog_nazwa = (TextView) view.findViewById(R.id.tv_dialog_nazwa);
        tv_dialog_pole1 = (TextView) view.findViewById(R.id.tv_dialog_pole1);
        tv_dialog_pole2 = (TextView) view.findViewById(R.id.tv_dialog_pole2);
        tv_dialog_pole3 = (TextView) view.findViewById(R.id.tv_dialog_pole3);
        tv_dialog_pole4 = (TextView) view.findViewById(R.id.tv_dialog_pole4);
        tv_dialog_pole5 = (TextView) view.findViewById(R.id.tv_dialog_pole5);
        tv_dialog_opis = (TextView) view.findViewById(R.id.tv_dialog_opis);
        
        if(czyBudynek)
			pobierzBudynek(nazwaObiektu);
		else if(!czyBudynek)
			pobierzMiejsce(nazwaObiektu);
        
        return builder.create();
	}
    
    
}




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

