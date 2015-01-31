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
	public static final String RELACJA_TAG = "Relacja";
	
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
			Log.d(DEBUG_TAG, "Nie bendem pompowa³ bo ni wim co!");
		}
		
	}
	
	private void testuj() {
		TabMiejsce miejsce = new Select().from(TabMiejsce.class).where("nazwa = ?", "Muzeum Techniki i Przemys³u NOT").executeSingle();
		
		try {
			TabBudynek budynek = miejsce.getBudynek();

			Log.d(DEBUG_TAG, "Miejsce: "+miejsce.getNazwa());
			Log.d(DEBUG_TAG, " w relacji z budynkiem: "+budynek.getNazwa());
		} catch (SQLiteException e) { e.printStackTrace(); }
	}
	
	private void pompujWszystko() {
		pompujOkres();
		pompujBranze();
		pompujBudynek();
		pompujMiejsce();
		pompujWydarzenie();
		pompujRzeczy();
		
		relacje();
	}
	
	private void relacje() {
		relacjeBudynekMiejsce();
		relacjeMiejsceWydarzenie();
	}
	
	private void pompujBudynek() {	// zwraca -1 jeœli nie uda³o siê dodaæ! a id dodanego jeœli uda³o.
		long dodanyId=0;
		LinkedList<TabBudynek> listaDoDodania = new LinkedList<TabBudynek>();
		TabBudynek budynek;
		TabOkres okres;
		
		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1689").executeSingle(); // znajdz okres dla budynku
		budynek = new TabBudynek("pa³ac £azienkowski", "ul. Agrykola 1, 00-460 Warszawa", "Dominik Merlini", "1689", "Pa³ac Na Wyspie, pa³ac Na Wodzie, pa³ac £azienkowski, dawniej przed przebudow¹ XVIII w., nazywany: £azienk¹ Lubomirskiego, Hippokrene – g³ówny kompleks architektoniczny w parku £azienkowskim. Zbudowany wed³ug projektu Tylmana z Gameren w 1683-1689 dla marsza³ka wielkiego koronnego Stanis³awa Herakliusza Lubomirskiego, przebudowany w 1788-1793 przez Dominika Merliniego i Jana Chrystiana Kamsetzera dla króla Stanis³awa Augusta Poniatowskiego[1].");
		budynek.setOkres(okres);
		listaDoDodania.add(budynek);							// dodaj do listy - zapis do bazy na koñcu
		
		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1940").executeSingle(); 
		budynek = new TabBudynek("DS Ustronie", "Ksiêcia Janusza 39, 01-452 Warszawa", "Pijany Student ELKI", "1945", 
				"Najlepszy akademik w Warszawie, du¿o imprez, cycków i wódki.");
		budynek.setOkres(okres);
		listaDoDodania.add(budynek);
		
		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1901").executeSingle(); 
		budynek = new TabBudynek("Bazylika katedralna Œwiêtych Micha³a i Floriana", "Floriañska 3", "Józef Pius Dziekoñski", "1904", 
				"Koœció³ na warszawskiej Pradze (dzisiejsza dzielnica Praga-Pó³noc) wzniesiony w latach 1887–1904 wed³ug projektu Józefa Piusa Dziekoñskiego, twórcy kilkudziesiêciu œwi¹tyñ w Polsce, który uzna³ go za swoje najwiêksze osi¹gniêcie. ");
		budynek.setOkres(okres);
		listaDoDodania.add(budynek);	
		
		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1720").executeSingle(); 
		budynek = new TabBudynek("Pa³ac Przebendowskich", "Solidarnoœci 62", "Jan Zygmunt Deybel", "ok. 1730", 
				"Pa³ac w Warszawie wybudowany w pierwszej po³owie XVIII w. przez Jana Jerzego Przebendowskiego. Mieœci siê obecnie pomiêdzy jezdniami al. „Solidarnoœci”.");
		budynek.setOkres(okres);
		listaDoDodania.add(budynek);	
		
		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1720").executeSingle(); 
		budynek = new TabBudynek("Koœció³ Wizytek", "Krakowskie Przedmieœcie 34 ", "Karol Bay i Efraim Szreger", "etapami w 1728–1733 oraz 1754–1763 ", 
				"Koœció³ Wizytek w Warszawie, pod wezwaniem Opieki œw. Józefa Oblubieñca Niepokalanej Bogurodzicy Maryi – póŸnobarokowy koœció³.");
		budynek.setOkres(okres);
		listaDoDodania.add(budynek);	
		
		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1770").executeSingle(); 
		budynek = new TabBudynek("Pa³ac Jab³onowskich", "Senatorska 14/16", "Jakub Fontana i Dominik Merlini", "1785", 
				"pa³ac w Warszawie, zlokalizowany w Œródmieœciu przy placu Teatralnym, wzniesiony w XVIII wieku, rozebrany przez w³adze komunistyczne w 1952 roku i przykryty brukiem, zrekonstruowany wed³ug projektu Lecha Klajnerta[1] w 1997 roku.");
		budynek.setOkres(okres);
		listaDoDodania.add(budynek);
		
		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1802").executeSingle(); 
		budynek = new TabBudynek("Sobór metropolitalny Œwiêtej Równej Aposto³om Marii Magdaleny", "Solidarnoœci 52", "", "II po³owa XIX wieku", 
				"cerkiew prawos³awna  wzniesiona w II po³owie XIX w., na potrzeby rosn¹cej spo³ecznoœci rosyjskiej osiedlaj¹cej siê w rejonie dzisiejszej Pragi-Pó³noc, a tak¿e w celu utrwalenia obecnoœci architektury rosyjskiej w zabudowie Warszawy poprzez wzniesienie kolejnego obiektu w stylu bizantyjsko-rosyjskim w wa¿nym punkcie miasta.");
		budynek.setOkres(okres);
		listaDoDodania.add(budynek);
		
		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1605").executeSingle(); 
		budynek = new TabBudynek("Zamek Ostrogskich", "Tamka 41", "Tylman z Gameren", "II po³owa XVII wieku", 
				"Pierwsza budowla o charakterze warownym zosta³a wzniesiona w tym miejscu pod koniec XVI wieku. W XIX wieku w gmachu rozpocz¹³ dzia³alnoœæ Instytut Muzyczny. W Zamku swoj¹ siedzibê ma nowoczesne Muzeum Fryderyka Chopina. ");
		budynek.setOkres(okres);
		listaDoDodania.add(budynek);
		
		for (TabBudynek x: listaDoDodania) {
			// TODO mo¿na zamieniæ na transakcjê - bêdzie szybciej
			dodanyId = x.save();
			Log.d(DEBUG_TAG, "Budynek dodany ID="+dodanyId+" nazwa="+x.getNazwa());	// zwraca -1 jeœli nie uda³o siê dodaæ! a id dodanego jeœli uda³o.
		}
		
		List<TabBudynek> budynki = new Select().from(TabBudynek.class).execute();		// weryfikacja
		Log.d(DEBUG_TAG, 
				"Iloœæ budynków w bazie: "+budynki.size()+"\n"+
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
		
		
		
		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1855").executeSingle(); // znajdz okres dla budynku
		miejsce = new TabMiejsce("Muzeum Techniki i Przemys³u NOT", "plac Defilad 1, Warszawa", false, "1955", "",
				"Muzeum Techniki w nawi¹zaniu do tradycji Muzeum Techniki i Przemys³u organizuje wystawy sta³e i czasowe, gromadzi zbiory z zakresu historii techniki i jej rozwoju, a tak¿e zbiorów z wybranych dziedzin nauki.");
		miejsce.setOkres(okres);
		miejsce.setBranza(branza);
		listaDoDodania.add(miejsce);							// dodaj do listy - zapis do bazy na koñcu
		
		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1955").executeSingle(); // znajdz okres dla budynku
		miejsce = new TabMiejsce("Centrum Olimpijskie im. Jana Paw³a II", "Wybrze¿e Gdyñskie 4", false, "27.05.2004", "Kulczyñski Architekt Sp. z o.o: Bogdan Kulczyñski,Pawe³ Py³ka, Rados³aw Sojka",
				"Wielofunkcyjny budynek mieszcz¹cy siê w warszawskiej dzielnicy ¯oliborz przy ulicy Wybrze¿e Gdyñskie 4. Jest siedzib¹ Polskiego Komitetu Olimpijskiego.");
		miejsce.setOkres(okres);
		miejsce.setBranza(branza);
		listaDoDodania.add(miejsce);							// dodaj do listy - zapis do bazy na koñcu
		
		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1955").executeSingle(); // znajdz okres dla budynku
		miejsce = new TabMiejsce("Muzeum Historii ¯ydów Polskich", "Mordechaja Anielewicza 6", false, "25.05.2005", "zespó³ fiñskich architektów kierowany przez Rainera Mahlamäkiego", 
				"Muzeum znajduj¹ce siê w Œródmieœciu Warszawy, na Muranowie dokumentuj¹ce wielowiekow¹ historiê ¯ydów w Polsce.");
		miejsce.setOkres(okres);
		miejsce.setBranza(branza);
		listaDoDodania.add(miejsce);							// dodaj do listy - zapis do bazy na koñcu
		
		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1855").executeSingle(); // znajdz okres dla budynku
		miejsce = new TabMiejsce("Brama G³ówna Uniwersytetu Warszawskiego", "Krakowskie Przedmieœcie  26/28", false, "1911", "Zygmunt Langman", 
				"Stanowi centralne wejœcie na kampus g³ówny uczelni. Znajduje siê przy ulicy Krakowskie Przedmieœcie pod numerem 26/28, który odnosi siê te¿ do wiêkszoœci budynków na kampusie.");
		miejsce.setOkres(okres);
		miejsce.setBranza(branza);
		listaDoDodania.add(miejsce);							// dodaj do listy - zapis do bazy na koñcu
		
		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1755").executeSingle(); // znajdz okres dla budynku
		miejsce = new TabMiejsce("Syrenka na warszawskiej Starówce", "Rynek Starego Miasta", false, "1855", "Konstanty Hegel", 
				"W 2008 roku oryginalna rzeŸba wykonana z br¹zowionego cynku zosta³a zabrana z rynku w celu wykonania prac konserwatorskich. RzeŸba by³a w bardzo z³ym stanie technicznym na skutek urazów mechanicznych i licznych aktów wandalizmu. 1 maja 2008 o 6 rano na cokó³ na rynku wróci³a oryginalna rzeŸba, jednak 12 maja orygina³ przeniesiono do Muzeum Historycznego m. st. Warszawy, jego miejsce zast¹pi³a kopia.");
		miejsce.setOkres(okres);
		miejsce.setBranza(branza);
		listaDoDodania.add(miejsce);							// dodaj do listy - zapis do bazy na koñcu
		
		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1786").executeSingle(); // znajdz okres dla budynku
		miejsce = new TabMiejsce("Filtry Lindleya", "Koszykowa 81", false, "1886", "William Lindley", 
				"Warszawskie filtry wody, zlokalizowane miêdzy ulicami Koszykow¹, Krzywickiego[3], Filtrow¹ i Raszyñsk¹.Warszawskie filtry nale¿¹ do systemu wodoci¹gów warszawskich. Od 2012 r. ca³y kompleks, ³¹cznie z budynkami wybudowanymi dwa lata wczeœniej, zosta³ uznany za pomnik historii.");
		miejsce.setOkres(okres);
		miejsce.setBranza(branza);
		listaDoDodania.add(miejsce);							// dodaj do listy - zapis do bazy na koñcu
		
		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1855").executeSingle(); // znajdz okres dla budynku
		miejsce = new TabMiejsce("Park Moczyd³o", "Górczewska", false, "Lata 60. XX wieku", "", 
				"Park na warszawskiej Woli - nazwany tak¿e od sztucznie usypanego tam wzniesienia Górk¹ Moczyd³owsk¹ - na terenie osiedla Ko³o, pomiêdzy ulicami: Deotymy, Górczewsk¹, Prymasa Tysi¹clecia i Czorsztyñsk¹.");
		miejsce.setOkres(okres);
		miejsce.setBranza(branza);
		miejsce.setLatitude(52.23999999);
		miejsce.setLongitude(20.951997);
		listaDoDodania.add(miejsce);							// dodaj do listy - zapis do bazy na koñcu
		
		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1845").executeSingle(); 
//		miejsce = new TabMiejsce("Ustro", "Ksiêcia Janusza 39", false, "1945", "Pijany Student", "Dobry klimat, fajny gmach, jo³ jo³.");
		miejsce = new TabMiejsce("Fort IX Twierdzy Warszawa", "Ksiêcia Janusza 39", false, "1945", "Pijany Student", "Dobry klimat, fajny gmach, jo³ jo³.");
		miejsce.setOkres(okres);
		miejsce.setBranza(branza);
		listaDoDodania.add(miejsce);
		
	
		for (TabMiejsce x: listaDoDodania) {
			// TODO mo¿na zamieniæ na transakcjê - bêdzie szybciej
			dodanyId = x.save();
			Log.d(DEBUG_TAG, "Miejsce dodane ID="+dodanyId+" nazwa="+x.getNazwa());	// zwraca -1 jeœli nie uda³o siê dodaæ! a id dodanego jeœli uda³o.
		}
		
		List<TabMiejsce> miejsca = new Select().from(TabMiejsce.class).execute();
		Log.d(DEBUG_TAG, 
				"Iloœæ miejsc w bazie: "+miejsca.size()+"\n"+
				"adres pierwszego to: "+miejsca.get(0).getAdres()+"\n"+
				"okres pierwszego to: "+miejsca.get(0).getOkres().getNazwa()+"\n"+
				"ID pierwszego to: "+miejsca.get(0).getId());
	}
	
	private void pompujWydarzenie() {
		long dodanyId=0;
		TabWydarzenie wydarzenie;
		LinkedList<TabWydarzenie> listaDoDodania = new LinkedList<TabWydarzenie>();
		TabBranza branza = new Select().from(TabBranza.class).where("nazwa = ?", "Wydarzenia").executeSingle();
		
		TabOkres okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1945").executeSingle();
		wydarzenie = new TabWydarzenie("Budowa Ustro", "1945", "", "Pijani studenci wrócili z imprezy i zbudowali", okres, branza);
		listaDoDodania.add(wydarzenie);
		
		wydarzenie = new TabWydarzenie("Poznanie ¯ony", "2014", "", "Tak wysz³o i by³o cudownie. I jest.", okres, branza);
		listaDoDodania.add(wydarzenie);
		
		wydarzenie = new TabWydarzenie("Najazd Brudnych Twarzy", "2014", "", "Wszystko brudne, syf w kuchni i ³azience.", okres, branza);
		listaDoDodania.add(wydarzenie);
		
		wydarzenie = new TabWydarzenie("Zlecenie budowy", "1875", "", 
				"Budowê zleci³ Sokrates Starynkiewicz (1820-1902), genera³ rosyjski, prezydent Warszawy (1875-1892) ju¿ w pierwszym roku swoich rz¹dów. W 1881 r. Starynkiewicz zatwierdzi³ projekt w Petersburgu i od tego roku do 1886 budowano pierwsz¹ sieæ wodoci¹gów i kanalizacji w Warszawie.", 
				okres, branza);
		listaDoDodania.add(wydarzenie);
		
		wydarzenie = new TabWydarzenie("Budowa stacji ozonowania poœredniego i filtrowania na wêglu aktywnym", "2008", "2010", 
				"W latach 2008–2010 wybudowano stacjê ozonowania poœredniego i filtrowania na wêglu aktywnym. Znajduj¹ siê w niej 3 generatory ozonu i 18 komór filtrów wêglowych. Architektonicznie nawi¹zuje ona starszych obiektów stacji – materia³ budowlany (ceg³a klinkierowa, piaskowiec) i stylizacja ornamentów do dziewiêtnastowiecznych budowli stacji, a p³askorzeŸby do reliefów w stylu art deco z miêdzywojennej stacji filtrów pospiesznych. Stacja w wyniku konkursu otrzyma³a nazwê „Socrates”.", 
				okres, branza);
		listaDoDodania.add(wydarzenie);
		
		wydarzenie = new TabWydarzenie("Pomnik historii", "2012", "", 
				"Ca³y kompleks, ³¹cznie z budynkami wybudowanymi dwa lata wczeœniej, zosta³ uznany za pomnik historii.", 
				okres, branza);
		listaDoDodania.add(wydarzenie);
		
		
		for (TabWydarzenie x: listaDoDodania) {
			// TODO mo¿na zamieniæ na transakcjê - bêdzie szybciej
			dodanyId = x.save();
			Log.d(DEBUG_TAG, "Wydarzenie dodane ID="+dodanyId+" nazwa="+x.getNazwa());	// zwraca -1 jeœli nie uda³o siê dodaæ! a id dodanego jeœli uda³o.
		}
	}
	
	private void pompujRzeczy() {
		long dodanyId=0;
		TabRzecz rzecz;
		TabOkres okres;
		TabMiejsce miejsce;
		LinkedList<TabRzecz> listaDoDodania = new LinkedList<TabRzecz>();
		TabBranza branza = new Select().from(TabBranza.class).where("nazwa = ?", "Rzeczy").executeSingle();
		

		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1858").executeSingle();
		miejsce =  new Select().from(TabMiejsce.class).where("nazwa = ?", "Muzeum Techniki i Przemys³u NOT").executeSingle();
		rzecz = new TabRzecz("AKAT-1", "1958", "Komputer", 
				"Pierwszy na œwiecie tranzystorowy analizator równañ ró¿niczkowych (komputer analogowy), konstrukcji Jacka Karpiñskiego, wykonany przez Instytut Automatyki Polskiej Akademii Nauk w 1958 r. Przeznaczony do rozwi¹zywania uk³adów równañ ró¿niczkowych i symulacji procesów.", 
				branza, okres);
		rzecz.setMiejsce(miejsce);
		listaDoDodania.add(rzecz);

		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1950").executeSingle();
		miejsce =  new Select().from(TabMiejsce.class).where("nazwa = ?", "Muzeum Techniki i Przemys³u NOT").executeSingle();
		rzecz = new TabRzecz("Szklana Panienka", "XXI wiek", "Eksponat", 
				"Model szklanej kobiety z widocznymi organami wewnêtrznymi. W trakcie pokazu s¹ one podœwietlane, a s³yszany przez uczestników komentarz wyjaœnia ich budowê i biologiczne funkcje.", 
				branza, okres);
		rzecz.setMiejsce(miejsce);
		listaDoDodania.add(rzecz);

		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1850").executeSingle();
		miejsce =  new Select().from(TabMiejsce.class).where("nazwa = ?", "Muzeum Techniki i Przemys³u NOT").executeSingle();
		rzecz = new TabRzecz("Marysia", "lata 30. XX wieku", "Pralka", 
				"Pierwsza pralka mechaniczna polskiej konstrukcji: pochodz¹ca z lat 30-tych XX wieku.", 
				branza, okres);
		rzecz.setMiejsce(miejsce);
		listaDoDodania.add(rzecz);

		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1795").executeSingle();
		miejsce =  new Select().from(TabMiejsce.class).where("nazwa = ?", "Muzeum Techniki i Przemys³u NOT").executeSingle();
		rzecz = new TabRzecz("Lotnia Tañskiego", "1895", "Szybowiec", 
				"Pierwszy polskiej konstrukcji szybowiec zbudowany w 1895 roku przez wynalazcê oraz pioniera awiacji w Polsce Czes³awa Tañskiego. Od nazwy wynalazku wywodzi siê polska nazwa lotni, któr¹ stosuje siê do czasów obecnych na nazwanie konstrukcji tego typu.", 
				branza, okres);
		rzecz.setMiejsce(miejsce);
		listaDoDodania.add(rzecz);

		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1696").executeSingle();
		miejsce =  new Select().from(TabMiejsce.class).where("nazwa = ?", "Pa³ac £azienkowski").executeSingle();
		rzecz = new TabRzecz("Królewska Kolekcja Medali", "XVIII wiek", "Kolekcja", 
				"Z³ote medale prezentowane w Pa³acu na Wyspie na wystawie „Z³oty poczet królów polskich” ze zbiorów Narodowego Muzeum Historii Ukrainy w Kijowie wybite zosta³y w koñcu XVIII wieku przez Mennicê Warszawsk¹. Stanis³aw August zleci³ ich projekt i wykonanie dwóm wybitnym medalierom – Janowi Filipowi Holzhäusserowi i Janowi Jakubowi Reichlowi – wed³ug portretów królów polskich pêdzla Marcello Bacciarellego.", 
				branza, okres);
		rzecz.setMiejsce(miejsce);
		listaDoDodania.add(rzecz);

		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1696").executeSingle();
		miejsce =  new Select().from(TabMiejsce.class).where("nazwa = ?", "Pa³ac £azienkowski").executeSingle();
		rzecz = new TabRzecz("La Frileuse - Alegoria Zimy", "XVIII wiek", "RzeŸba", 
				"Replika autorska rzeŸby Jeana-Antoine Houdona, jednego z najwybitniejszych rzeŸbiarzy epoki neoklasycyzmu. Nale¿a³a do ulubionych rzêŸb Stanis³awa Augusta Poniatowskiego. Obecnie znajduje siê w Starej Pomarañczarni.", 
				branza, okres);
		rzecz.setMiejsce(miejsce);
		listaDoDodania.add(rzecz);

		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1950").executeSingle();
		miejsce =  new Select().from(TabMiejsce.class).where("nazwa = ?", "Muzeum Historii ¯ydów Polskich").executeSingle();
		rzecz = new TabRzecz("Szachy wykonane w ukryciu", "II wojna œwiatowa", "Szachy", 
				"Pola wraz z mê¿em, Marcelim Najderem oraz oœmioma innymi osobami ukrywa³a siê, po likwidacji tamtejszego getta, w bunkrze w Ko³omyi. To w³aœnie wtedy Pola wyrzeŸbi³a z drewna kasztanowego szachy, które pos³u¿y³y do gry, zape³niaj¹c codziennoœæ ukrywaj¹cym siê. ", 
				branza, okres);
		rzecz.setMiejsce(miejsce);
		listaDoDodania.add(rzecz);

		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1849").executeSingle();
		miejsce =  new Select().from(TabMiejsce.class).where("nazwa = ?", "Muzeum Historii ¯ydów Polskich").executeSingle();
		rzecz = new TabRzecz("Papieroœnica Józefa Sigalina", "1949", "Papieroœnica", 
				"Srebrna papieroœnica warszawskiego architekta i urbanisty Józefa Sigalina, jednego z autorów projektu trasy W-Z. Sigalin otrzyma³ papieroœnicê od Zygmunta Stêpiñskiego, bliskiego przyjaciela oraz jednego ze wspó³twórców trasy w dniu jej otwarcia." +
						"	We wnêtrzu papieroœnicy widnieje wygrawerowany widok przedstawiaj¹cy Plac Zamkowy oraz tunel trasy W-Z, poni¿ej daty jej budowy: 15.4.47 – 22.7.49 i imiê ”Zygmunt”.", 
						branza, okres);
		rzecz.setMiejsce(miejsce);
		listaDoDodania.add(rzecz);

		okres =  new Select().from(TabOkres.class).where("rokPoczatek > ?", "1865").executeSingle();
		miejsce =  new Select().from(TabMiejsce.class).where("nazwa = ?", "Muzeum Historii ¯ydów Polskich").executeSingle();
		rzecz = new TabRzecz("Dyplom Sprawiedliwych wœród Narodów Œwiata Ireny Sendlerowej", "1965", "Dyplom", 
				"W 1965 roku Irena Sendlerowa zosta³a uznana za jedn¹ ze Sprawiedliwych wœród Narodów Œwiata. Odznaczenie przyznawane jest przez instytut pamiêci Yad Vashem. Honorowy dyplom oraz medal na którym znajduje siê fragment z Talmudu: \"Kto ratuje jedno ¿ycie – ratuje ca³y œwiat\", mo¿e byæ wrêczany tym wszystkim, którzy nara¿aj¹c w³asne ¿ycie, nieœli pomoc ¯ydom podczas II wojny œwiatowej.", 
				branza, okres);
		rzecz.setMiejsce(miejsce);
		listaDoDodania.add(rzecz);

		for (TabRzecz x: listaDoDodania) {
			// TODO mo¿na zamieniæ na transakcjê - bêdzie szybciej
			dodanyId = x.save();
			Log.d(DEBUG_TAG, "Rzecz dodana ID="+dodanyId+" nazwa="+x.getNazwa());	// zwraca -1 jeœli nie uda³o siê dodaæ! a id dodanego jeœli uda³o.
		}
	}
	
	private void pompujOkres() {
		long dodanyId=0;
		TabOkres okres;
		LinkedList<TabOkres> listaDoDodania = new LinkedList<TabOkres>();
		
		okres = new TabOkres();
		okres.setNazwa("XIV-XV wiek");
		okres.setRokPoczatek("1301");
		okres.setRokKoniec("1500");
		okres.setOpis("Okres XIV - XV - w tym powstanie Warszawy.");
		listaDoDodania.add(okres);
		
		okres = new TabOkres();
		okres.setNazwa("XVI wiek");
		okres.setRokPoczatek("1501");
		okres.setRokKoniec("1600");
		okres.setOpis("Wiek XVI.");
		listaDoDodania.add(okres);
		
		okres = new TabOkres();
		okres.setNazwa("XVII wiek");
		okres.setRokPoczatek("1601");
		okres.setRokKoniec("1700");
		okres.setOpis("Wiek XVII.");
		listaDoDodania.add(okres);
		
		okres = new TabOkres();
		okres.setNazwa("XVIII wiek");
		okres.setRokPoczatek("1701");
		okres.setRokKoniec("1800");
		okres.setOpis("Wiek XVIII.");
		listaDoDodania.add(okres);
		
		okres = new TabOkres();
		okres.setNazwa("XIX wiek");
		okres.setRokPoczatek("1801");
		okres.setRokKoniec("1900");
		okres.setOpis("Wiek XIX.");
		listaDoDodania.add(okres);
		
		okres = new TabOkres();
		okres.setNazwa("XX wiek - do 1989");
		okres.setRokPoczatek("1901");
		okres.setRokKoniec("1989");
		okres.setOpis("Wiek XX - Warszawa do koñca komunizmu.");
		listaDoDodania.add(okres);
		
		okres = new TabOkres();
		okres.setNazwa("XX wiek - od 1990");
		okres.setRokPoczatek("1990");
		okres.setRokKoniec("2015");
		okres.setOpis("Wiek XX - Warszawa w czasach wspó³czesnych. Od III Rzeczypospolitej.");
		listaDoDodania.add(okres);
		
		for (TabOkres x: listaDoDodania) {
			// TODO mo¿na zamieniæ na transakcjê - bêdzie szybciej
			dodanyId = x.save();
			Log.d(DEBUG_TAG, "Okres dodany ID="+dodanyId+" nazwa="+x.getNazwa());	// zwraca -1 jeœli nie uda³o siê dodaæ! a id dodanego jeœli uda³o.
		}
	}

	private void pompujBranze() {
		long dodanyId=0;
		TabBranza branza;
		LinkedList<TabBranza> listaDoDodania = new LinkedList<TabBranza>();
		
		branza = new TabBranza("Miejsca", "Grupa zawieraj¹ca wszystkie znane aplikacji miejsca.");
		listaDoDodania.add(branza);
		
		branza = new TabBranza("Wydarzenia", "Grupa zawieraj¹ca wszystkie znane aplikacji wydarzenia.");
		listaDoDodania.add(branza);
		
		branza = new TabBranza("Rzeczy", "Grupa zawieraj¹ca wszystkie znane aplikacji rzeczy - przedmioty namacalne.");
		listaDoDodania.add(branza);
		
		branza = new TabBranza("Postacie", "Grupa zawieraj¹ca wszystkie znane aplikacji postacie.");
		listaDoDodania.add(branza);
		
		for (TabBranza x: listaDoDodania) {
			// TODO mo¿na zamieniæ na transakcjê - bêdzie szybciej
			dodanyId = x.save();
			Log.d(DEBUG_TAG, "Bran¿a dodana ID="+dodanyId+" nazwa="+x.getNazwa());	// zwraca -1 jeœli nie uda³o siê dodaæ! a id dodanego jeœli uda³o.
		}
	}
	
	private void relacjeBudynekMiejsce() {
		// sa to relacje 1-1 - zalozenie jest takie, ze miejsce moze sie skladac z wielu miejsc, a kazde miejsce to jeden budynek. wiec jesli miejsce to Aleje Ujazdowskie, to pomniki to beda miejsca zwiazane oraz budynki tez beda miejscami powiazanymi z miejscem
		TabBudynek budynek = new Select().from(TabBudynek.class).where("nazwa = ?", "DS Ustronie").executeSingle();
//		TabMiejsce miejsce = new Select().from(TabMiejsce.class).where("nazwa = ?", "Ustro").executeSingle();
		TabMiejsce miejsce = new Select().from(TabMiejsce.class).where("nazwa = ?", "Fort IX Twierdzy Warszawa").executeSingle();
		
		dodajRelacjeBudynekMiejsce(budynek, miejsce);
	}
	
	private void dodajRelacjeBudynekMiejsce(TabBudynek budynek, TabMiejsce miejsce) {
		long dodanyBId=0, dodanyMId=0;
		
//		budynek.setMiejsce(miejsce);
		miejsce.setBudynek(budynek);
		
		dodanyBId=budynek.save();
		dodanyMId=miejsce.save();
		
		Log.d(RELACJA_TAG, "Dodana relacja 1-1 budynek: "+dodanyBId+ " miejsce: "+dodanyMId);
	}
	
	private void relacjeMiejsceWydarzenie() {
		long dodanyId=0;
		LinkedList<TabMiejsceWydarzenie> listaDoDodania = new LinkedList<TabMiejsceWydarzenie>();
		TabMiejsceWydarzenie relacja;
		
		TabMiejsce miejsce = new Select().from(TabMiejsce.class).where("nazwa = ?", "Fort IX Twierdzy Warszawa").executeSingle();
		
		relacja = new TabMiejsceWydarzenie(miejsce.getId(), 1L);
		listaDoDodania.add(relacja);
		relacja = new TabMiejsceWydarzenie(miejsce.getId(), 2L);
		listaDoDodania.add(relacja);
		relacja = new TabMiejsceWydarzenie(miejsce.getId(), 3L);
		relacja = new TabMiejsceWydarzenie(miejsce.getId(), 6L);		// relacja tez z jednym wydarzeniem z filtrow, dla testow.
		listaDoDodania.add(relacja);

		miejsce = new Select().from(TabMiejsce.class).where("nazwa = ?", "Filtry Lindleya").executeSingle();
		relacja = new TabMiejsceWydarzenie(miejsce.getId(), 4L);
		listaDoDodania.add(relacja);
		
		relacja = new TabMiejsceWydarzenie(miejsce.getId(), 5L);
		listaDoDodania.add(relacja);
		
		relacja = new TabMiejsceWydarzenie(miejsce.getId(), 6L);
		listaDoDodania.add(relacja);
		
		for (TabMiejsceWydarzenie x: listaDoDodania) {
			// TODO mo¿na zamieniæ na transakcjê - bêdzie szybciej
			dodanyId = x.save();
			Log.d(RELACJA_TAG, "Relacja dodana ID="+dodanyId+" Miejsce_ID="+x.getMiejsce_id()+" Wydarzenie_ID="+x.getWydarzenie_id());	// zwraca -1 jeœli nie uda³o siê dodaæ! a id dodanego jeœli uda³o.
		}
	}
}
