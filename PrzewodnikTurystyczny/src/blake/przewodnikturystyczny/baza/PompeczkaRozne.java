package blake.przewodnikturystyczny.baza;

import java.util.LinkedList;
import java.util.List;

import android.util.Log;
import blake.przewodnikturystyczny.baza.model.TabBranza;
import blake.przewodnikturystyczny.baza.model.TabBudynek;
import blake.przewodnikturystyczny.baza.model.TabMiejsce;
import blake.przewodnikturystyczny.baza.model.TabOkres;

import com.activeandroid.query.Select;

public class PompeczkaRozne {
	
	public static final String DEBUG_TAG = "Przewodnik";
	
	TabOkres okres;
	TabBranza branza;
	
	public PompeczkaRozne(int coPompowac) {
		
		switch (coPompowac) {
		case 1:
			pompujBudynek();
			break;
		case 2:
			pompujMiejsce();
			break;
		default:
			Log.d(DEBUG_TAG, "Nie bendem pompowa� bo ni wim co!");
		}
		
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
			Log.d(DEBUG_TAG, "Budynek dodany ID="+dodanyId);	// zwraca -1 je�li nie uda�o si� doda�! a id dodanego je�li uda�o.
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
		miejsce.setLatitude(52.142400);
		miejsce.setLongitude(20.570719);
		listaDoDodania.add(miejsce);							// dodaj do listy - zapis do bazy na ko�cu
		
	
		for (TabMiejsce x: listaDoDodania) {
			// TODO mo�na zamieni� na transakcj� - b�dzie szybciej
			dodanyId = x.save();
			Log.d(DEBUG_TAG, "Miejsce dodane ID="+dodanyId);	// zwraca -1 je�li nie uda�o si� doda�! a id dodanego je�li uda�o.
		}
		
		List<TabMiejsce> miejsca = new Select().from(TabMiejsce.class).execute();
		Log.d(DEBUG_TAG, 
				"Ilo�� miejsc w bazie: "+miejsca.size()+"\n"+
				"adres pierwszego to: "+miejsca.get(0).getAdres()+"\n"+
				"okres pierwszego to: "+miejsca.get(0).getOkres().getNazwa()+"\n"+
				"ID pierwszego to: "+miejsca.get(0).getId());
	}
}
