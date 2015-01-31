package blake.przewodnikturystyczny.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import blake.przewodnikturystyczny.R;
import blake.przewodnikturystyczny.baza.model.IfSelectable;
import blake.przewodnikturystyczny.baza.model.TabBranza;
import blake.przewodnikturystyczny.baza.model.TabMiejsce;
import blake.przewodnikturystyczny.baza.model.TabOkres;
import blake.przewodnikturystyczny.baza.model.TabPostac;
import blake.przewodnikturystyczny.baza.model.TabRzecz;
import blake.przewodnikturystyczny.baza.model.TabWydarzenie;
import blake.przewodnikturystyczny.model.KategorieListaAdapter;

import com.activeandroid.Model;
import com.activeandroid.query.Select;

public class Wyszukiwanie extends Activity {
	
	public static final String DEBUG_TAG = "Przewodnik";
	private Context context;
	
	private ListView kategorie_lista;
	private ListView szczegoly_lista;
	private TextView lbl_wybierz_szczegol;
	private Button btn_przejdz_do_zwiedzania;
	
//	private final String[] naglowki = { "Okresy", "Kategorie", "Budynki", "Miejsca", "Ludzie", "Wydarzenia", "Przedmioty, dzie³a sztuki, eksponaty" };
	private final String[] naglowki = { "Okres", "Kategoria" };
	private List<String> naglowkiLista;
	private List<String> dane;
	private List<String> daneSzczegoly;
	private List<String> wybrane;
	private KategorieListaAdapter adapter;
	private KategorieListaAdapter adapterSzczegoly;
	private Boolean trybListy = false; 	// false gdy lista kategorii, true gdy zawartoœæ Okresu lub Bran¿y
	private Boolean czyOkres = false;
	private String wybranyOkres = "";
	private String wybranaBranza = "";
	String wybranyElement = "";
	private List<TabOkres> okresy;
	private List<TabBranza> branze;
	List<? extends Model> listaObiektow = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wyszukiwanie);
		context = getApplicationContext();
		
		
		kategorie_lista = (ListView) findViewById(R.id.kategorieLista);
		szczegoly_lista = (ListView) findViewById(R.id.szczegolyLista);
		lbl_wybierz_szczegol = (TextView) findViewById(R.id.lbl_wybierz_szczegol);
		btn_przejdz_do_zwiedzania = (Button) findViewById(R.id.btn_przejdz_do_zwiedzania);
		
		dane = new ArrayList<String>();
		daneSzczegoly = new ArrayList<String>();
		naglowkiLista = new ArrayList<String>();
		wybrane = new ArrayList<String>();
		
		wybrane.add(wybranaBranza);
		wybrane.add(wybranyOkres);
		
		actionBar();
		zbudujListe();
		initOnClickListener();
		
		okresy = new Select().all().from(TabOkres.class).execute();
		branze = new Select().all().from(TabBranza.class).execute();
	}

	private void zbudujListe() {
		for (int i = 0; i < naglowki.length; i++)		// zbuduj liste naglowkow. potem nie bedzie trzeba jej budowac za kazdym razem
			naglowkiLista.add(naglowki[i]);
		
		dane.addAll(naglowkiLista);
		adapter = new KategorieListaAdapter(this, R.layout.cust_row_kategorie_lista, dane, wybranyOkres, wybranaBranza, true);
		kategorie_lista.setAdapter(adapter);
		
		adapterSzczegoly = new KategorieListaAdapter(this, R.layout.cust_row_kategorie_lista, daneSzczegoly, null, null, false);
		szczegoly_lista.setAdapter(adapterSzczegoly);
		
		initOnItemClickListener();
	}
	
	private void budujSzczegoly() {
		Log.d(DEBUG_TAG, "Pojawiam szczegoly");
		szczegoly_lista.setVisibility(View.VISIBLE);		// pojaw liste :)
		lbl_wybierz_szczegol.setVisibility(View.VISIBLE);	// i label ;)
		
//		Class<? extends Model> tabelaKlasa = null;
		
		daneSzczegoly.clear();
		
//		if (wybranaBranza.equals("Miejsca")) {
//			List<TabMiejsce> lista = new Select().all().from(TabMiejsce.class).execute();	
//
//			for (TabMiejsce x: lista) {
//				daneSzczegoly.add(x.getNazwa());
//			}
//		}
//		else if (wybranaBranza.equals("Wydarzenia")) {
//			List<TabWydarzenie> lista = new Select().all().from(TabWydarzenie.class).execute();	
//
//			for (TabWydarzenie x: lista) {
//				daneSzczegoly.add(x.getNazwa());
//			}
//		}
//		else if (wybranaBranza.equals("Rzeczy")) {
//			List<TabRzecz> lista = new Select().all().from(TabRzecz.class).execute();	
//
//			for (TabRzecz x: lista) {
//				daneSzczegoly.add(x.getNazwa());
//			}
//		}
//		else if (wybranaBranza.equals("Postacie")) {
//			List<TabPostac> lista = new Select().all().from(TabPostac.class).execute();	
//
//			for (TabPostac x: lista) {
//				daneSzczegoly.add(x.getNazwa());
//			}
//		}
		
		TabOkres okres = new Select().from(TabOkres.class).where("nazwa = ?", wybranyOkres).executeSingle(); 
				
		if (wybranaBranza.equals("Miejsca")) {
			listaObiektow = new Select().from(TabMiejsce.class).where("Okres_ID = ?", okres.getId()).execute();
		}
		else if (wybranaBranza.equals("Wydarzenia")) {
			listaObiektow = new Select().from(TabWydarzenie.class).where("Okres_ID = ?", okres.getId()).execute();	
		}
		else if (wybranaBranza.equals("Rzeczy")) {
			listaObiektow = new Select().from(TabRzecz.class).where("Okres_ID = ?", okres.getId()).execute();	
		}
		else if (wybranaBranza.equals("Postacie")) {
			listaObiektow = new Select().from(TabPostac.class).where("Okres_ID = ?", okres.getId()).execute();	
		}
		
		for (Model y: listaObiektow) {
			daneSzczegoly.add(((IfSelectable) y).getNazwa());
//			Log.d(DEBUG_TAG, ": ID="+((IfSelectable) y).getId()+ ": " +((IfSelectable) y).getNazwa());
		}
		
		
		adapterSzczegoly.notifyDataSetChanged();

	}
	
	private void actionBar() {
		getActionBar().setTitle("Rozpocznij zwiedzanie");
//		getActionBar().setDisplayHomeAsUpEnabled(true);		// i tak go nie uzywam bo bym musial Support ladowac
		// to ta strzalka w lewo, przy ikonce aplikacji, taki 'Wstecz'
	}
	
	private void initOnItemClickListener() {
		OnItemClickListener listenerKategorie = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				Toast.makeText(getApplicationContext(), "Klikniêto pozycjê: "+position, Toast.LENGTH_SHORT).show();

				if (!trybListy) { 						// jesli jest w widoku kategorii, to przejdz do szczegolowego
					switch(position) {
					case 0:								// wybrano okres
						dane.clear();
						for (TabOkres okres: okresy) 
							dane.add(okres.getNazwa());
						
						czyOkres = true;				// przejscie do szczegolowego - okres
						break;
					case 1:								// wybrano branze
						dane.clear();
						for (TabBranza branza: branze) 
							dane.add(branza.getNazwa());
						
						czyOkres = false;				// przejscie do szczegolowego - branza
						break;
					}
					
					trybListy = true;					// oznacz przejscie do szczegolowego
				} 
				else if (trybListy) {					// jesli w widoku szczegolowym to wroc do kategorii
					if (czyOkres) { 
						wybranyOkres = dane.get(position);	// bierze nazwe, spoko
						adapter.ustawWybrane(wybranyOkres, true);
					}
					else if(!czyOkres) {
						wybranaBranza = dane.get(position);
						adapter.ustawWybrane(wybranaBranza, false);
					}
					
					Toast.makeText(getApplicationContext(), "Wybrano Okres: "+wybranyOkres+" wybrana Bran¿a: "+wybranaBranza, Toast.LENGTH_SHORT).show();
					dane.clear();
					dane.addAll(naglowkiLista);

					if ( (!wybranaBranza.equals("")) && (!wybranyOkres.equals("")) ) {
							budujSzczegoly();
					}
					
					trybListy = false;
				}
				
				Log.d(DEBUG_TAG, "Powiadamiam adapter.");
				adapter.notifyDataSetChanged();
			}
		};

		OnItemClickListener listenerSzczegoly = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				wybranyElement = ((IfSelectable) listaObiektow.get(position)).getNazwa();
				Toast.makeText(getApplicationContext(), "Wybrano pozycjê: "+wybranyElement, Toast.LENGTH_SHORT).show();
			}			
		};
		
		kategorie_lista.setOnItemClickListener(listenerKategorie);
		szczegoly_lista.setOnItemClickListener(listenerSzczegoly);
	}
	
	private void initOnClickListener() {
		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, Mapa.class);
				intent.putExtra("obiekt", wybranyElement);
				intent.putExtra("typObiektu", wybranaBranza);
				startActivity(intent);
			}
		};
		
		btn_przejdz_do_zwiedzania.setOnClickListener(listener);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wyszukiwanie, menu);
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
}
