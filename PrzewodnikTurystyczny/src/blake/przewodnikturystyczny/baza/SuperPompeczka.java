package blake.przewodnikturystyczny.baza;

import java.util.LinkedList;
import java.util.List;

import android.database.sqlite.SQLiteException;
import android.util.Log;
import blake.przewodnikturystyczny.baza.model.TabBranza;
import blake.przewodnikturystyczny.baza.model.TabBudynek;
import blake.przewodnikturystyczny.baza.model.TabMiejsce;
import blake.przewodnikturystyczny.baza.model.TabOkres;
import blake.przewodnikturystyczny.baza.model.TabRzecz;
import blake.przewodnikturystyczny.baza.model.TabWydarzenie;
import blake.przewodnikturystyczny.baza.model.pomocniczy.TabMiejsceWydarzenie;

import com.activeandroid.query.Select;

public class SuperPompeczka {
	
	public static final String DEBUG_TAG = "Przewodnik";
	
	public SuperPompeczka(int coPompowac) {
		
		switch (coPompowac) {
		case 1:
			pompujOkres();
			break;
		case 2:
			pompujBranze();
			break;
		case 3:
			pompujBudynek();
			break;
		case 4:
			pompujMiejsce();
			break;
		case 5:
			pompujWydarzenie();
			break;
		case 20:
			pompujWszystko();
			break;
		case 51:
			relacje();
			break;
		case 99:
			testuj();
			break;
		default:
			Log.d(DEBUG_TAG, "Nie bendem pompowa� bo ni wim co!");
		}
		
	}
	
	private void testuj() {
		TabMiejsce miejsce = new Select().from(TabMiejsce.class).where("nazwa = ?", "Ustro").executeSingle();
		
		try {
			List<TabWydarzenie> wydarzenia = miejsce.getWydarzenia();
			System.out.println("Ilo�� relacji z Ustro: "+wydarzenia.size());	
			for(TabWydarzenie x: wydarzenia) {
				Log.d(DEBUG_TAG, "Wydarzenie w relacji z "+miejsce.getNazwa()+": "+x.getNazwa());
			}
		} catch (SQLiteException e) { e.printStackTrace(); }
	}
	
	private void pompujWszystko() {
		pompujOkres();
		pompujBranze();
		pompujBudynek();
		pompujMiejsce();
		pompujWydarzenie();
		
		relacje();
	}
	
	private void relacje() {
		relacjeMiejsceBudynek();
		relacjeMiejsceWydarzenie();
	}
	
	private void pompujBudynek() {	// zwraca -1 je�li nie uda�o si� doda�! a id dodanego je�li uda�o.
		long dodanyId=0;
		LinkedList<TabBudynek> listaDoDodania = new LinkedList<TabBudynek>();
		TabBudynek budynek;
		TabOkres okres;
		
		okres =  new Select().from(TabOkres.class).where("nazwa = ?", "XVII").executeSingle(); // znajdz okres dla budynku
		budynek = new TabBudynek("pa�ac �azienkowski", "ul. Agrykola 1, 00-460 Warszawa", "Dominik Merlini", "1689", "Pa�ac Na Wyspie, pa�ac Na Wodzie, pa�ac �azienkowski, dawniej przed przebudow� XVIII w., nazywany: �azienk� Lubomirskiego, Hippokrene � g��wny kompleks architektoniczny w parku �azienkowskim. Zbudowany wed�ug projektu Tylmana z Gameren w 1683-1689 dla marsza�ka wielkiego koronnego Stanis�awa Herakliusza Lubomirskiego, przebudowany w 1788-1793 przez Dominika Merliniego i Jana Chrystiana Kamsetzera dla kr�la Stanis�awa Augusta Poniatowskiego[1].");
		budynek.setOkres(okres);
		listaDoDodania.add(budynek);							// dodaj do listy - zapis do bazy na ko�cu
		
		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1940").executeSingle(); 
		budynek = new TabBudynek("DS Ustronie", "Ksi�cia Janusza 39, 01-452 Warszawa", "Pijany Student ELKI", "1945", 
				"Najlepszy akademik w Warszawie, du�o imprez, cyck�w i w�dki.");
		budynek.setOkres(okres);
		listaDoDodania.add(budynek);
		
		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1901").executeSingle(); 
		budynek = new TabBudynek("Bazylika katedralna �wi�tych Micha�a i Floriana", "Floria�ska 3", "J�zef Pius Dzieko�ski", "1904", 
				"Ko�ci�na�warszawskiej Pradze�(dzisiejsza dzielnica�Praga-P�noc) wzniesiony w latach 1887�1904 wed�ug projektu�J�zefa Piusa Dzieko�skiego, tw�rcy kilkudziesi�ciu �wi�ty� w Polsce, kt�ry uzna� go za swoje najwi�ksze osi�gni�cie. ");
		budynek.setOkres(okres);
		listaDoDodania.add(budynek);	
		
		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1720").executeSingle(); 
		budynek = new TabBudynek("Pa�ac Przebendowskich", "Solidarno�ci 62", "Jan Zygmunt Deybel", "ok. 1730", 
				"Pa�ac w�Warszawie�wybudowany w pierwszej po�owie XVIII w. przez�Jana Jerzego Przebendowskiego. Mie�ci si� obecnie pomi�dzy jezdniami�al. �Solidarno�ci�.");
		budynek.setOkres(okres);
		listaDoDodania.add(budynek);	
		
		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1720").executeSingle(); 
		budynek = new TabBudynek("Ko�ci� Wizytek", "Krakowskie Przedmie�cie�34 ", "Karol Bay i�Efraim Szreger", "etapami w 1728�1733 oraz 1754�1763 ", 
				"Ko�ci� Wizytek w Warszawie, pod wezwaniem Opieki �w. J�zefa Oblubie�ca Niepokalanej Bogurodzicy Maryi � p�nobarokowy ko�ci�.");
		budynek.setOkres(okres);
		listaDoDodania.add(budynek);	
		
		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1770").executeSingle(); 
		budynek = new TabBudynek("Pa�ac Jab�onowskich", "Senatorska 14/16", "Jakub Fontana�i�Dominik Merlini", "1785", 
				"pa�ac�w�Warszawie, zlokalizowany w��r�dmie�ciu�przy�placu Teatralnym, wzniesiony w�XVIII wieku, rozebrany przez w�adze komunistyczne w 1952 roku i przykryty brukiem, zrekonstruowany wed�ug projektu�Lecha Klajnerta[1]�w 1997 roku.");
		budynek.setOkres(okres);
		listaDoDodania.add(budynek);
		
		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1802").executeSingle(); 
		budynek = new TabBudynek("Sob�r metropolitalny �wi�tej R�wnej Aposto�om Marii Magdaleny", "Solidarno�ci 52", "", "II po�owa XIX wieku", 
				"cerkiew�prawos�awna� wzniesiona w II po�owie XIX w., na potrzeby rosn�cej spo�eczno�ci rosyjskiej osiedlaj�cej si� w rejonie dzisiejszej�Pragi-P�noc, a tak�e w celu utrwalenia obecno�ci architektury rosyjskiej w zabudowie Warszawy poprzez wzniesienie kolejnego obiektu w stylu�bizantyjsko-rosyjskim�w wa�nym punkcie miasta.");
		budynek.setOkres(okres);
		listaDoDodania.add(budynek);
		
		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1605").executeSingle(); 
		budynek = new TabBudynek("Zamek Ostrogskich", "Tamka 41", "Tylman z Gameren", "II po�owa XVII wieku", 
				"Pierwsza budowla o charakterze warownym zosta�a wzniesiona w tym miejscu pod koniec XVI wieku. W XIX wieku w gmachu rozpocz�� dzia�alno�� Instytut Muzyczny. W Zamku swoj� siedzib� ma nowoczesne Muzeum Fryderyka Chopina. ");
		budynek.setOkres(okres);
		listaDoDodania.add(budynek);
		
		for (TabBudynek x: listaDoDodania) {
			// TODO mo�na zamieni� na transakcj� - b�dzie szybciej
			dodanyId = x.save();
			Log.d(DEBUG_TAG, "Budynek dodany ID="+dodanyId+" nazwa="+x.getNazwa());	// zwraca -1 je�li nie uda�o si� doda�! a id dodanego je�li uda�o.
		}
		
		List<TabBudynek> budynki = new Select().from(TabBudynek.class).execute();		// weryfikacja
		Log.d(DEBUG_TAG, 
				"Ilo�� budynk�w w bazie: "+budynki.size()+"\n"+
				"adres pierwszego to: "+budynki.get(0).getAdres()+"\n"+
				"okres pierwszego to: "+budynki.get(0).getOkres().getNazwa()+"\n"+
				"ID pierwszego to: "+budynki.get(0).getId());
	}
	
	private void pompujMiejsce() {
		long dodanyId=0;
		LinkedList<TabMiejsce> listaDoDodania = new LinkedList<TabMiejsce>();
		TabMiejsce miejsce;
		TabOkres okres;
		TabBranza branza = new Select().from(TabBranza.class).where("nazwa = ?", "Miejsca").executeSingle();
		
		okres =  new Select().from(TabOkres.class).where("nazwa = ?", "XVII").executeSingle(); // znajdz okres dla budynku
		miejsce = new TabMiejsce("Centrum Olimpijskie im. Jana Paw�a II", "Wybrze�e Gdy�skie�4", false, "27.05.2004", "Kulczy�ski Architekt Sp. z o.o:�Bogdan Kulczy�ski,Pawe� Py�ka,�Rados�aw Sojka",
				"Wielofunkcyjny budynek mieszcz�cy si� w�warszawskiej�dzielnicy��oliborz�przy�ulicy Wybrze�e Gdy�skie�4. Jest siedzib��Polskiego Komitetu Olimpijskiego.");
		miejsce.setOkres(okres);
		miejsce.setBranza(branza);
		listaDoDodania.add(miejsce);							// dodaj do listy - zapis do bazy na ko�cu
		
		okres =  new Select().from(TabOkres.class).where("nazwa = ?", "XVII").executeSingle(); // znajdz okres dla budynku
		miejsce = new TabMiejsce("Muzeum Historii �yd�w Polskich", "Mordechaja Anielewicza 6", false, "25.05.2005", "zesp� fi�skich architekt�w kierowany przez�Rainera Mahlam�kiego", 
				"Muzeum znajduj�ce si� w��r�dmie�ciu�Warszawy, na�Muranowie�dokumentuj�ce wielowiekow��histori� �yd�w w Polsce.");
		miejsce.setOkres(okres);
		miejsce.setBranza(branza);
		listaDoDodania.add(miejsce);							// dodaj do listy - zapis do bazy na ko�cu
		
		okres =  new Select().from(TabOkres.class).where("nazwa = ?", "XVII").executeSingle(); // znajdz okres dla budynku
		miejsce = new TabMiejsce("Brama G��wna Uniwersytetu Warszawskiego", "Krakowskie Przedmie�cie� 26/28", false, "1911", "Zygmunt Langman", 
				"Stanowi centralne wej�cie na�kampus�g��wny uczelni. Znajduje si� przy ulicy�Krakowskie Przedmie�cie�pod numerem 26/28, kt�ry odnosi si� te� do wi�kszo�ci budynk�w na kampusie.");
		miejsce.setOkres(okres);
		miejsce.setBranza(branza);
		listaDoDodania.add(miejsce);							// dodaj do listy - zapis do bazy na ko�cu
		
		okres =  new Select().from(TabOkres.class).where("nazwa = ?", "XVII").executeSingle(); // znajdz okres dla budynku
		miejsce = new TabMiejsce("Syrenka na warszawskiej Star�wce", "Rynek Starego Miasta", false, "1855", "Konstanty Hegel", 
				"W�2008�roku oryginalna rze�ba wykonana z br�zowionego cynku zosta�a zabrana z rynku w celu wykonania prac konserwatorskich. Rze�ba by�a w bardzo z�ym stanie technicznym na skutek uraz�w mechanicznych i licznych akt�w wandalizmu.�1 maja�2008 o 6 rano na cok� na rynku wr�ci�a oryginalna rze�ba, jednak 12 maja orygina� przeniesiono do Muzeum Historycznego m. st. Warszawy, jego miejsce zast�pi�a kopia.");
		miejsce.setOkres(okres);
		miejsce.setBranza(branza);
		listaDoDodania.add(miejsce);							// dodaj do listy - zapis do bazy na ko�cu
		
		okres =  new Select().from(TabOkres.class).where("nazwa = ?", "XVII").executeSingle(); // znajdz okres dla budynku
		miejsce = new TabMiejsce("Filtry Lindleya", "Koszykowa 81", false, "1886", "William Lindley", 
				"Warszawskie�filtry wody, zlokalizowane mi�dzy ulicami Koszykow�,�Krzywickiego[3],�Filtrow��i�Raszy�sk�.Warszawskie filtry nale�� do systemu�wodoci�g�w�warszawskich. Od 2012 r. ca�y kompleks, ��cznie z budynkami wybudowanymi dwa lata wcze�niej, zosta� uznany za�pomnik historii.");
		miejsce.setOkres(okres);
		miejsce.setBranza(branza);
		listaDoDodania.add(miejsce);							// dodaj do listy - zapis do bazy na ko�cu
		
		okres =  new Select().from(TabOkres.class).where("nazwa = ?", "XVII").executeSingle(); // znajdz okres dla budynku
		miejsce = new TabMiejsce("Park Moczyd�o", "G�rczewska", false, "Lata 60. XX wieku", "", 
				"Park na�warszawskiej�Woli - nazwany tak�e od sztucznie usypanego tam wzniesienia�G�rk� Moczyd�owsk� - na terenie osiedla�Ko�o, pomi�dzy ulicami: Deotymy,�G�rczewsk�,�Prymasa Tysi�clecia�i Czorszty�sk�.");
		miejsce.setOkres(okres);
		miejsce.setBranza(branza);
		miejsce.setLatitude(52.23999999);
		miejsce.setLongitude(20.951997);
		listaDoDodania.add(miejsce);							// dodaj do listy - zapis do bazy na ko�cu
		
		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1940").executeSingle(); 
		miejsce = new TabMiejsce("Ustro", "Ksi�cia Janusza 39", false, "1945", "Pijany Student", "Dobry klimat, fajny gmach, jo� jo�.");
		miejsce.setOkres(okres);
		miejsce.setBranza(branza);
		listaDoDodania.add(miejsce);
		
	
		for (TabMiejsce x: listaDoDodania) {
			// TODO mo�na zamieni� na transakcj� - b�dzie szybciej
			dodanyId = x.save();
			Log.d(DEBUG_TAG, "Miejsce dodane ID="+dodanyId+" nazwa="+x.getNazwa());	// zwraca -1 je�li nie uda�o si� doda�! a id dodanego je�li uda�o.
		}
		
		List<TabMiejsce> miejsca = new Select().from(TabMiejsce.class).execute();
		Log.d(DEBUG_TAG, 
				"Ilo�� miejsc w bazie: "+miejsca.size()+"\n"+
				"adres pierwszego to: "+miejsca.get(0).getAdres()+"\n"+
				"okres pierwszego to: "+miejsca.get(0).getOkres().getNazwa()+"\n"+
				"ID pierwszego to: "+miejsca.get(0).getId());
	}
	
	private void pompujWydarzenie() {
		long dodanyId=0;
		TabWydarzenie wydarzenie;
		LinkedList<TabWydarzenie> listaDoDodania = new LinkedList<TabWydarzenie>();
		TabBranza branza = new Select().from(TabBranza.class).where("nazwa = ?", "Wydarzenia").executeSingle();
		
		TabOkres okres =  new Select().from(TabOkres.class).where("nazwa = ?", "XXpo").executeSingle();
		wydarzenie = new TabWydarzenie("Budowa Ustro", "1945", "", "Pijani studenci wr�cili z imprezy i zbudowali", okres, branza);
		listaDoDodania.add(wydarzenie);
		
		wydarzenie = new TabWydarzenie("Poznanie �ony", "2014", "", "Tak wysz�o i by�o cudownie. I jest.", okres, branza);
		listaDoDodania.add(wydarzenie);
		
		wydarzenie = new TabWydarzenie("Najazd Brudnych Twarzy", "2014", "", "Wszystko brudne, syf w kuchni i �azience.", okres, branza);
		listaDoDodania.add(wydarzenie);
		
		wydarzenie = new TabWydarzenie("Zlecenie budowy", "1875", "", 
				"Budow� zleci� Sokrates Starynkiewicz (1820-1902), genera� rosyjski, prezydent Warszawy (1875-1892) ju� w pierwszym roku swoich rz�d�w. W 1881 r. Starynkiewicz zatwierdzi� projekt w Petersburgu i od tego roku do 1886 budowano pierwsz� sie� wodoci�g�w i kanalizacji w Warszawie.", 
				okres, branza);
		listaDoDodania.add(wydarzenie);
		
		wydarzenie = new TabWydarzenie("Budowa stacji ozonowania po�redniego i filtrowania na w�glu aktywnym", "2008", "2010", 
				"W latach 2008�2010 wybudowano stacj� ozonowania po�redniego i filtrowania na w�glu aktywnym. Znajduj� si� w niej 3 generatory ozonu i 18 kom�r filtr�w w�glowych. Architektonicznie nawi�zuje ona starszych obiekt�w stacji � materia� budowlany (ceg�a klinkierowa, piaskowiec) i stylizacja ornament�w do dziewi�tnastowiecznych budowli stacji, a p�askorze�by do relief�w w stylu art deco z mi�dzywojennej stacji filtr�w pospiesznych. Stacja w wyniku konkursu otrzyma�a nazw� �Socrates�.", 
				okres, branza);
		listaDoDodania.add(wydarzenie);
		
		wydarzenie = new TabWydarzenie("Pomnik historii", "2012", "", 
				"Ca�y kompleks, ��cznie z budynkami wybudowanymi dwa lata wcze�niej, zosta� uznany za pomnik historii.", 
				okres, branza);
		listaDoDodania.add(wydarzenie);
		
		
		for (TabWydarzenie x: listaDoDodania) {
			// TODO mo�na zamieni� na transakcj� - b�dzie szybciej
			dodanyId = x.save();
			Log.d(DEBUG_TAG, "Wydarzenie dodane ID="+dodanyId+" nazwa="+x.getNazwa());	// zwraca -1 je�li nie uda�o si� doda�! a id dodanego je�li uda�o.
		}
	}
	
	private void pompujRzeczy() {
		long dodanyId=0;
		TabRzecz rzecz;
		TabOkres okres;
		TabBudynek budynek;
		LinkedList<TabRzecz> listaDoDodania = new LinkedList<TabRzecz>();
		TabBranza branza = new Select().from(TabBranza.class).where("nazwa = ?", "Rzeczy").executeSingle();
		
		
		okres = new Select().from(TabOkres.class).where("rokPoczatek > ?", "1940").executeSingle(); 
		budynek =  new Select().from(TabBudynek.class).where("nazwa = ?", "nazwa budynku").executeSingle();
		rzecz = new TabRzecz("nazwa", "dataPowstania", "rodzaj", 
				"opis", 
				budynek, branza, okres);
		listaDoDodania.add(rzecz);
		
		for (TabRzecz x: listaDoDodania) {
			// TODO mo�na zamieni� na transakcj� - b�dzie szybciej
			dodanyId = x.save();
			Log.d(DEBUG_TAG, "Wydarzenie dodane ID="+dodanyId+" nazwa="+x.getNazwa());	// zwraca -1 je�li nie uda�o si� doda�! a id dodanego je�li uda�o.
		}
	}
	
	private void pompujOkres() {
		long dodanyId=0;
		TabOkres okres;
		LinkedList<TabOkres> listaDoDodania = new LinkedList<TabOkres>();
		
		okres = new TabOkres();
		okres.setNazwa("XIV-XV");
		okres.setRokPoczatek("1301");
		okres.setRokKoniec("1500");
		okres.setOpis("Okres XIV - XV - w tym powstanie Warszawy.");
		listaDoDodania.add(okres);
		
		okres = new TabOkres();
		okres.setNazwa("XVI");
		okres.setRokPoczatek("1501");
		okres.setRokKoniec("1600");
		okres.setOpis("Wiek XVI.");
		listaDoDodania.add(okres);
		
		okres = new TabOkres();
		okres.setNazwa("XVII");
		okres.setRokPoczatek("1601");
		okres.setRokKoniec("1700");
		okres.setOpis("Wiek XVII.");
		listaDoDodania.add(okres);
		
		okres = new TabOkres();
		okres.setNazwa("XVIII");
		okres.setRokPoczatek("1701");
		okres.setRokKoniec("1800");
		okres.setOpis("Wiek XVIII.");
		listaDoDodania.add(okres);
		
		okres = new TabOkres();
		okres.setNazwa("XIX");
		okres.setRokPoczatek("1801");
		okres.setRokKoniec("1900");
		okres.setOpis("Wiek XIX.");
		listaDoDodania.add(okres);
		
		okres = new TabOkres();
		okres.setNazwa("XX");
		okres.setRokPoczatek("1901");
		okres.setRokKoniec("1989");
		okres.setOpis("Wiek XX - Warszawa do ko�ca komunizmu.");
		listaDoDodania.add(okres);
		
		okres = new TabOkres();
		okres.setNazwa("XXpo");
		okres.setRokPoczatek("1990");
		okres.setRokKoniec("2015");
		okres.setOpis("Wiek XX - Warszawa w czasach wsp�czesnych. Od III Rzeczypospolitej.");
		listaDoDodania.add(okres);
		
		for (TabOkres x: listaDoDodania) {
			// TODO mo�na zamieni� na transakcj� - b�dzie szybciej
			dodanyId = x.save();
			Log.d(DEBUG_TAG, "Okres dodany ID="+dodanyId+" nazwa="+x.getNazwa());	// zwraca -1 je�li nie uda�o si� doda�! a id dodanego je�li uda�o.
		}
	}

	private void pompujBranze() {
		long dodanyId=0;
		TabBranza branza;
		LinkedList<TabBranza> listaDoDodania = new LinkedList<TabBranza>();
		
		branza = new TabBranza("Miejsca", "Grupa zawieraj�ca wszystkie znane aplikacji miejsca.");
		listaDoDodania.add(branza);
		
		branza = new TabBranza("Wydarzenia", "Grupa zawieraj�ca wszystkie znane aplikacji wydarzenia.");
		listaDoDodania.add(branza);
		
		branza = new TabBranza("Rzeczy", "Grupa zawieraj�ca wszystkie znane aplikacji rzeczy - przedmioty namacalne.");
		listaDoDodania.add(branza);
		
		branza = new TabBranza("Posta�", "Grupa zawieraj�ca wszystkie znane aplikacji postacie.");
		listaDoDodania.add(branza);
		
		for (TabBranza x: listaDoDodania) {
			// TODO mo�na zamieni� na transakcj� - b�dzie szybciej
			dodanyId = x.save();
			Log.d(DEBUG_TAG, "Bran�a dodana ID="+dodanyId+" nazwa="+x.getNazwa());	// zwraca -1 je�li nie uda�o si� doda�! a id dodanego je�li uda�o.
		}
	}
	
	private void relacjeMiejsceBudynek() {
		// sa to relacje 1-1 - zalozenie jest takie, ze miejsce moze sie skladac z wielu miejsc, a kazde miejsce to jeden budynek. wiec jesli miejsce to Aleje Ujazdowskie, to pomniki to beda miejsca zwiazane oraz budynki tez beda miejscami powiazanymi z miejscem
		TabBudynek budynek = new Select().from(TabBudynek.class).where("nazwa = ?", "DS Ustronie").executeSingle();
		TabMiejsce miejsce = new Select().from(TabMiejsce.class).where("nazwa = ?", "Ustro").executeSingle();
		
		budynek.setMiejsce(miejsce);
		miejsce.setBudynek(budynek);
	}
	
	private void relacjeMiejsceWydarzenie() {
		long dodanyId=0;
		LinkedList<TabMiejsceWydarzenie> listaDoDodania = new LinkedList<TabMiejsceWydarzenie>();
		TabMiejsceWydarzenie relacja;
		
		TabMiejsce miejsce = new Select().from(TabMiejsce.class).where("nazwa = ?", "Ustro").executeSingle();
		
		relacja = new TabMiejsceWydarzenie(miejsce.getId(), 1L);
		listaDoDodania.add(relacja);
		relacja = new TabMiejsceWydarzenie(miejsce.getId(), 2L);
		listaDoDodania.add(relacja);
		relacja = new TabMiejsceWydarzenie(miejsce.getId(), 3L);
		listaDoDodania.add(relacja);

		miejsce = new Select().from(TabMiejsce.class).where("nazwa = ?", "Filtry Lindleya").executeSingle();
		relacja = new TabMiejsceWydarzenie(miejsce.getId(), 4L);
		listaDoDodania.add(relacja);
		
		relacja = new TabMiejsceWydarzenie(miejsce.getId(), 5L);
		listaDoDodania.add(relacja);
		
		relacja = new TabMiejsceWydarzenie(miejsce.getId(), 6L);
		listaDoDodania.add(relacja);
		
		for (TabMiejsceWydarzenie x: listaDoDodania) {
			// TODO mo�na zamieni� na transakcj� - b�dzie szybciej
			dodanyId = x.save();
			Log.d(DEBUG_TAG, "Relacja dodana ID="+dodanyId+" Miejsce_ID="+x.getMiejsce_id()+" Wydarzenie_ID="+x.getWydarzenie_id());	// zwraca -1 je�li nie uda�o si� doda�! a id dodanego je�li uda�o.
		}
	}
}
