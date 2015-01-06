package blake.przewodnikturystyczny.activity;

import java.util.List;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import blake.przewodnikturystyczny.R;
import blake.przewodnikturystyczny.baza.Namierzanie;
import blake.przewodnikturystyczny.baza.PompeczkaBranzaOkres;
import blake.przewodnikturystyczny.baza.PompeczkaRozne;
import blake.przewodnikturystyczny.baza.model.TabBranza;
import blake.przewodnikturystyczny.baza.model.TabBudynek;
import blake.przewodnikturystyczny.baza.model.TabMiejsce;
import blake.przewodnikturystyczny.baza.model.TabOkres;
import blake.przewodnikturystyczny.baza.model.TabPostac;
import blake.przewodnikturystyczny.baza.model.TabRod;
import blake.przewodnikturystyczny.baza.model.TabRzecz;
import blake.przewodnikturystyczny.baza.model.TabWydarzenie;
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
import android.widget.Toast;

public class MainActivity extends Activity {

	public static final String DEBUG_TAG = "Przewodnik";
	private Context context;
	
	private Button btn_mapa;
	private Button btn_pompeczka_branza;
	private Button btn_pompeczka_okres;
	private Button btn_pompeczka_rozne;
	private Button btn_czysc_budynki;
	private Button btn_count_all;
	private Button btn_czysc_okresy;
	private Button btn_czysc_branze;
	private Button btn_czysc_wsio;
	private Button btn_namierz_budynki;
	private Button btn_namierz_miejsca;
	
	private Namierzanie namierzanie;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        
        btn_mapa = (Button) findViewById(R.id.btn_mapa);
        btn_pompeczka_branza = (Button) findViewById(R.id.btn_pompeczka_branza);
        btn_pompeczka_okres = (Button) findViewById(R.id.btn_pompeczka_okres);
        btn_pompeczka_rozne = (Button) findViewById(R.id.btn_pompeczka_rozne);
        btn_count_all = (Button) findViewById(R.id.btn_count_all);
        btn_czysc_okresy = (Button) findViewById(R.id.btn_czysc_okresy);
        btn_czysc_branze = (Button) findViewById(R.id.btn_czysc_branze);
        btn_czysc_budynki = (Button) findViewById(R.id.btn_czysc_budynki);
        btn_namierz_budynki = (Button) findViewById(R.id.btn_namierz_budynki);
        btn_namierz_miejsca = (Button) findViewById(R.id.btn_namierz_miejsca);
        btn_czysc_wsio = (Button) findViewById(R.id.btn_czysc_wsio);
        
        initBtnOnClickListeners();
    }

    
	private void initBtnOnClickListeners() {
		btn_mapa.setOnClickListener(new OnClickListener() {
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
		
		btn_pompeczka_rozne.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new PompeczkaRozne();
			}
		});
		
		btn_count_all.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(1);
//				Toast.makeText(context, "Liczba Bran¿ w bazie: "+TabBranza.count(TabBranza.class), Toast.LENGTH_SHORT).show();
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
				new Delete().from(TabBudynek.class).execute();
				new Delete().from(TabMiejsce.class).execute();
				new Delete().from(TabWydarzenie.class).execute();
				new Delete().from(TabPostac.class).execute();
				new Delete().from(TabRzecz.class).execute();
				new Delete().from(TabRod.class).execute();
				new Delete().from(TabBranza.class).execute();
				new Delete().from(TabOkres.class).execute();
			}
		});	
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




