package blake.przewodnikturystyczny.baza;

import java.util.ArrayList;

import android.util.Log;
import blake.przewodnikturystyczny.activity.MainActivity;
import blake.przewodnikturystyczny.baza.model.TabBranza;
import blake.przewodnikturystyczny.baza.model.TabOkres;

public class PompeczkaBranzaOkres {
	TabOkres okres;
	TabBranza branza;

	public PompeczkaBranzaOkres(Boolean coRobic) {
		
		if(coRobic) zrobOkresy();
		else zrobBranze();
	}

	private void zrobOkresy() {
		ArrayList<TabOkres> okresy = new ArrayList<TabOkres>();
		
		// TODO zrobiæ inicjowanie okresu za ka¿dym razem od nowa bo siê same 'ostatnie' wrzucaj¹ na listê.
		
		okres = new TabOkres();
		okres.setNazwa("XIV-XV");
		okres.setRokPoczatek("1301");
		okres.setRokKoniec("1500");
		okres.setOpis("Okres XIV - XV - w tym powstanie Warszawy.");
		okresy.add(okres);
		
		okres = new TabOkres();
		okres.setNazwa("XVI");
		okres.setRokPoczatek("1501");
		okres.setRokKoniec("1600");
		okres.setOpis("Wiek XVI.");
		okresy.add(okres);
		
		okres = new TabOkres();
		okres.setNazwa("XVII");
		okres.setRokPoczatek("1601");
		okres.setRokKoniec("1700");
		okres.setOpis("Wiek XVII.");
		okresy.add(okres);
		
		okres = new TabOkres();
		okres.setNazwa("XVIII");
		okres.setRokPoczatek("1701");
		okres.setRokKoniec("1800");
		okres.setOpis("Wiek XVIII.");
		okresy.add(okres);
		
		okres = new TabOkres();
		okres.setNazwa("XIX");
		okres.setRokPoczatek("1801");
		okres.setRokKoniec("1900");
		okres.setOpis("Wiek XIX.");
		okresy.add(okres);
		
		okres = new TabOkres();
		okres.setNazwa("XX");
		okres.setRokPoczatek("1901");
		okres.setRokKoniec("1989");
		okres.setOpis("Wiek XX - Warszawa do koñca komunizmu.");
		okresy.add(okres);
		
		okres = new TabOkres();
		okres.setNazwa("XXpo");
		okres.setRokPoczatek("1990");
		okres.setRokKoniec("2015");
		okres.setOpis("Wiek XX - Warszawa w czasach wspó³czesnych. Od III Rzeczypospolitej.");
		okresy.add(okres);
		
		for (TabOkres x: okresy)
			Log.d(MainActivity.DEBUG_TAG, x.getNazwa());
		
		Log.d(MainActivity.DEBUG_TAG, "Na liœcie: "+okresy.size());
		TabOkres.saveInTx(okresy);
	}

	private void zrobBranze() {
		long id;
		
		branza = new TabBranza("Miejsca", "Grupa zawieraj¹ca wszystkie znane aplikacji miejsca.");
		id = branza.save();
		Log.d(MainActivity.DEBUG_TAG, "Sejwowa³em Bran¿ê ID="+id+" nazwa="+branza.getNazwa());
		
		branza = new TabBranza("Wydarzenia", "Grupa zawieraj¹ca wszystkie znane aplikacji wydarzenia.");
		id = branza.save();
		Log.d(MainActivity.DEBUG_TAG, "Sejwowa³em Bran¿ê ID="+id+" nazwa="+branza.getNazwa());
		
		branza = new TabBranza("Rzeczy", "Grupa zawieraj¹ca wszystkie znane aplikacji rzeczy - przedmioty namacalne.");
		id = branza.save();
		Log.d(MainActivity.DEBUG_TAG, "Sejwowa³em Bran¿ê ID="+id+" nazwa="+branza.getNazwa());
		
		branza = new TabBranza("Postaæ", "Grupa zawieraj¹ca wszystkie znane aplikacji postacie.");
		id = branza.save();
		Log.d(MainActivity.DEBUG_TAG, "Sejwowa³em Bran¿ê ID="+id+" nazwa="+branza.getNazwa());
	}

}