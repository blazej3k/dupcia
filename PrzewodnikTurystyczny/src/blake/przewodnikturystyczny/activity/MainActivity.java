package blake.przewodnikturystyczny.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import blake.przewodnikturystyczny.R;
import blake.przewodnikturystyczny.baza.Namierzanie;
import blake.przewodnikturystyczny.baza.ObslugaBazy;
import blake.przewodnikturystyczny.baza.SuperPompeczka;
import blake.przewodnikturystyczny.baza.model.TabBranza;
import blake.przewodnikturystyczny.baza.model.TabBudynek;
import blake.przewodnikturystyczny.baza.model.TabMiejsce;
import blake.przewodnikturystyczny.baza.model.TabOkres;
import blake.przewodnikturystyczny.baza.model.TabPostac;
import blake.przewodnikturystyczny.baza.model.TabRod;
import blake.przewodnikturystyczny.baza.model.TabRzecz;
import blake.przewodnikturystyczny.baza.model.TabWydarzenie;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

public class MainActivity extends Activity {

	public static final String DEBUG_TAG = "Przewodnik";
	private Context context;
	
	private Button btn_mapa;
	private Button pompeczka_okresbranza;
	private Button btn_pompeczka_budynki;
	private Button btn_pompeczka_miejsca;
	private Button btn_pompeczka_wydarzenia;
	private Button btn_relacje;
	private Button btn_relacje_x;
	private Button btn_czysc_budynki;
	private Button btn_count_all;
	private Button btn_czysc_wsio;
	private Button btn_namierz_budynki;
	private Button btn_namierz_miejsca;
	private Button btn_select_all;
	private Button btn_init;
	private Button btn_testuj;
	
	private ObslugaBazy obslugaBazy;
	private Namierzanie namierzanie;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        
        obslugaBazy = new ObslugaBazy();
        
        btn_mapa = (Button) findViewById(R.id.btn_mapa);
        pompeczka_okresbranza = (Button) findViewById(R.id.pompeczka_okresbranza);
        btn_pompeczka_budynki = (Button) findViewById(R.id.btn_pompeczka_budynki);
        btn_pompeczka_miejsca = (Button) findViewById(R.id.btn_pompeczka_miejsca);
        btn_pompeczka_wydarzenia = (Button) findViewById(R.id.btn_pompeczka_wydarzenia);
        
        btn_relacje = (Button) findViewById(R.id.btn_relacje); 
        btn_relacje_x = (Button) findViewById(R.id.btn_relacje_x);
        btn_count_all = (Button) findViewById(R.id.btn_count_all);
        btn_czysc_budynki = (Button) findViewById(R.id.btn_czysc_budynki);
        btn_czysc_wsio = (Button) findViewById(R.id.btn_czysc_wsio);
        btn_namierz_budynki = (Button) findViewById(R.id.btn_namierz_budynki);
        btn_namierz_miejsca = (Button) findViewById(R.id.btn_namierz_miejsca);
        btn_select_all = (Button) findViewById(R.id.btn_select_all);
        btn_init = (Button) findViewById(R.id.btn_init);
        btn_testuj = (Button) findViewById(R.id.btn_testuj);
        
        initBtnOnClickListeners();
        
    }

    
	private void initBtnOnClickListeners() {
		OnClickListener onClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case (R.id.btn_mapa):
					Intent intent = new Intent(context, Mapa.class);
					startActivity(intent);
				break;
				case (R.id.pompeczka_okresbranza):
					new SuperPompeczka(1);
					new SuperPompeczka(2);
					break;
				case (R.id.btn_pompeczka_budynki):
					new SuperPompeczka(3);
					break;
				case (R.id.btn_pompeczka_miejsca):
					new SuperPompeczka(4);
					break;
				case (R.id.btn_pompeczka_wydarzenia):
					new SuperPompeczka(5);
					break;
				case (R.id.btn_testuj):
					new SuperPompeczka(99);
					break;
				case (R.id.btn_relacje):
					new SuperPompeczka(51);
					break;
				case (R.id.btn_relacje_x):
//					Intent intent2 = new Intent(context, ListaExp.class);
//					startActivity(intent2);
					break;
				case (R.id.btn_count_all):
					showDialog(1);
					break;
				case (R.id.btn_czysc_budynki):
					new Delete().from(TabBudynek.class).execute();
					break;
				case (R.id.btn_czysc_wsio):
					obslugaBazy.truncateAll();
					break;
				case (R.id.btn_namierz_budynki):
					namierzanie = new Namierzanie(context, true);
					break;
				case (R.id.btn_namierz_miejsca):
					namierzanie = new Namierzanie(context, false);
					break;
				case (R.id.btn_select_all):
					obslugaBazy.selectAll();
					break;
				case (R.id.btn_init):
					obslugaBazy.truncateAll();
					new SuperPompeczka(20);
				}	
			}
		};
		
        btn_mapa.setOnClickListener(onClickListener);
        pompeczka_okresbranza.setOnClickListener(onClickListener);
        btn_pompeczka_budynki.setOnClickListener(onClickListener);
        btn_pompeczka_miejsca.setOnClickListener(onClickListener);
        btn_pompeczka_wydarzenia.setOnClickListener(onClickListener);
        btn_testuj.setOnClickListener(onClickListener);
        btn_relacje.setOnClickListener(onClickListener);
        btn_relacje_x.setOnClickListener(onClickListener);
        btn_count_all.setOnClickListener(onClickListener);
        btn_czysc_budynki.setOnClickListener(onClickListener);
        btn_czysc_wsio.setOnClickListener(onClickListener);
        btn_namierz_budynki.setOnClickListener(onClickListener);
        btn_namierz_miejsca.setOnClickListener(onClickListener);
        btn_select_all.setOnClickListener(onClickListener);
        btn_init.setOnClickListener(onClickListener);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Statystyka tabel.");
        dialogBuilder.setMessage(
        		"Okres: " + new Select().from(TabOkres.class).count()+"\n"+
        		"Bran¿a: " + new Select().from(TabBranza.class).count()+"\n"+
        		"Budynek: " + new Select().from(TabBudynek.class).count()+"\n"+
        		"Miejsce: " + new Select().from(TabMiejsce.class).count()+"\n"+
        		"Wydarzenie: " + new Select().from(TabWydarzenie.class).count()+"\n"+
        		"Postaæ: " + new Select().from(TabPostac.class).count()+"\n"+
        		"Rzecz: " + new Select().from(TabRzecz.class).count()+"\n"+
        		"Ród: " + new Select().from(TabRod.class).count()+"\n"
        		);
        return dialogBuilder.create();
    }
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    

    @Override
    protected void onDestroy() {
    	super.onDestroy();
    }
}



/*btn_mapa.setOnClickListener(new OnClickListener() {
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(context, Mapa.class);
		startActivity(intent);
	}
});	

btn_pompeczka_branza.setOnClickListener(new OnClickListener() {
	@Override
	public void onClick(View v) {
		new PompeczkaBranzaOkres(false);
	}
});

btn_pompeczka_okres.setOnClickListener(new OnClickListener() {
	@Override
	public void onClick(View v) {
		new PompeczkaBranzaOkres(true);
	}
});

btn_pompeczka_budynki.setOnClickListener(new OnClickListener() {
	@Override
	public void onClick(View v) {
		new PompeczkaRozne(1);
	}
});

btn_pompeczka_miejsca.setOnClickListener(new OnClickListener() {
	@Override
	public void onClick(View v) {
		new PompeczkaRozne(2);
	}
});

btn_pompeczka_wydarzenia.setOnClickListener(new OnClickListener() {
	@Override
	public void onClick(View v) {
		new PompeczkaRozne(3);
	}
});

btn_pompeczka_x.setOnClickListener(new OnClickListener() {
	@Override
	public void onClick(View v) {
		new PompeczkaRozne(99);
	}
});

btn_count_all.setOnClickListener(new OnClickListener() {
	@Override
	public void onClick(View v) {
		showDialog(1);
//		Toast.makeText(context, "Liczba Bran¿ w bazie: "+TabBranza.count(TabBranza.class), Toast.LENGTH_SHORT).show();
	}
});	

btn_czysc_okresy.setOnClickListener(new OnClickListener() {
	@Override
	public void onClick(View v) {
		new Delete().from(TabOkres.class).execute();
	}
});

btn_czysc_branze.setOnClickListener(new OnClickListener() {
	@Override
	public void onClick(View v) {
		new Delete().from(TabBranza.class).execute();
	}
});	

btn_czysc_budynki.setOnClickListener(new OnClickListener() {
	@Override
	public void onClick(View v) {
		new Delete().from(TabBudynek.class).execute();
	}
});	

btn_namierz_budynki.setOnClickListener(new OnClickListener() {
	@Override
	public void onClick(View v) {
		namierzanie = new Namierzanie(context, true);
	}
});	

btn_namierz_miejsca.setOnClickListener(new OnClickListener() {
	@Override
	public void onClick(View v) {
		namierzanie = new Namierzanie(context, false);
	}
});	

btn_czysc_wsio.setOnClickListener(new OnClickListener() {
	@Override
	public void onClick(View v) {
		TabBudynek.truncate(TabBudynek.class);			// truncate ró¿ni siê od delete tym, ¿e czyœci tabelê (robi delete), ale te¿ resetuje jej ID
		TabMiejsce.truncate(TabMiejsce.class);			// nowe pozycje z powrotem maj¹ id 1.
		TabWydarzenie.truncate(TabWydarzenie.class);
		TabPostac.truncate(TabPostac.class);
		TabRzecz.truncate(TabRzecz.class);
		TabRod.truncate(TabRod.class);
		TabBranza.truncate(TabBranza.class);
		TabOkres.truncate(TabOkres.class);
		
//		new Delete().from(TabBudynek.class).execute();
//		new Delete().from(TabMiejsce.class).execute();
//		new Delete().from(TabWydarzenie.class).execute();
//		new Delete().from(TabPostac.class).execute();
//		new Delete().from(TabRzecz.class).execute();
//		new Delete().from(TabRod.class).execute();
//		new Delete().from(TabBranza.class).execute();
//		new Delete().from(TabOkres.class).execute();
	}
});	*/
