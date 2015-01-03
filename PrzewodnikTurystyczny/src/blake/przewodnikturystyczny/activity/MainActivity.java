package blake.przewodnikturystyczny.activity;

import java.util.List;

import blake.przewodnikturystyczny.R;
import blake.przewodnikturystyczny.baza.PompeczkaBranzaOkres;
import blake.przewodnikturystyczny.baza.PompeczkaRozne;
import blake.przewodnikturystyczny.baza.model.TabBranza;
import blake.przewodnikturystyczny.baza.model.TabOkres;
import android.app.Activity;
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
	private Button btn_count_okresy;
	private Button btn_count_branze;
	private Button btn_czysc_okresy;
	private Button btn_czysc_branze;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
//        SugarContext.init(context);	// inicjalizacja Sugara - orma od bazy danych
        
        btn_mapa = (Button) findViewById(R.id.btn_mapa);
        btn_pompeczka_branza = (Button) findViewById(R.id.btn_pompeczka_branza);
        btn_pompeczka_okres = (Button) findViewById(R.id.btn_pompeczka_okres);
        btn_pompeczka_rozne = (Button) findViewById(R.id.btn_pompeczka_rozne);
        btn_count_okresy = (Button) findViewById(R.id.btn_count_okresy);
        btn_count_branze = (Button) findViewById(R.id.btn_count_branze);
        btn_czysc_okresy = (Button) findViewById(R.id.btn_czysc_okresy);
        btn_czysc_branze = (Button) findViewById(R.id.btn_czysc_branze);
        
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
		
		btn_count_okresy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "Liczba Okresów w bazie: "+TabOkres.count(TabOkres.class), Toast.LENGTH_SHORT).show();
			}
		});	
		
		btn_count_branze.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "Liczba Bran¿ w bazie: "+TabBranza.count(TabBranza.class), Toast.LENGTH_SHORT).show();
			}
		});	
		
		btn_czysc_okresy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TabOkres.deleteAll(TabOkres.class);
			}
		});
		
		btn_czysc_branze.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TabOkres.deleteAll(TabBranza.class);
			}
		});	
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
    	// TODO ogolnie to trzeba SugarContext.terminate() przeniesc do Application.onTerminate() - zgodnie z zaleceniem Sugara, albo do MainActivity.onPause
    	// Wywalony bo po poprawce manifestu .init chyba nie jest potrzebny, wiêc to te¿ zakomentowane
    	// SugarContext.terminate();
    	super.onDestroy();
    }
}




