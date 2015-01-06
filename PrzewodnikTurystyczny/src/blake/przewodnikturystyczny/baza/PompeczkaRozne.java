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
			Log.d(DEBUG_TAG, "Nie bendem pompowa³ bo ni wim co!");
		}
		
	}
	
	private void pompujBudynek() {	// zwraca -1 jeœli nie uda³o siê dodaæ! a id dodanego jeœli uda³o.
		long dodanyId=0;
		LinkedList<TabBudynek> listaDoDodania = new LinkedList<TabBudynek>();
		TabBudynek budynek;
		TabOkres okres;
		
		okres =  new Select().from(TabOkres.class).where("nazwa = ?", "XVII").executeSingle(); // znajdz okres dla budynku
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
			Log.d(DEBUG_TAG, "Budynek dodany ID="+dodanyId);	// zwraca -1 jeœli nie uda³o siê dodaæ! a id dodanego jeœli uda³o.
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
		
		okres =  new Select().from(TabOkres.class).where("nazwa = ?", "XVII").executeSingle(); // znajdz okres dla budynku
		miejsce = new TabMiejsce("Centrum Olimpijskie im. Jana Paw³a II", "Wybrze¿e Gdyñskie 4", false, "27.05.2004", "Kulczyñski Architekt Sp. z o.o: Bogdan Kulczyñski,Pawe³ Py³ka, Rados³aw Sojka",
				"Wielofunkcyjny budynek mieszcz¹cy siê w warszawskiej dzielnicy ¯oliborz przy ulicy Wybrze¿e Gdyñskie 4. Jest siedzib¹ Polskiego Komitetu Olimpijskiego.");
		miejsce.setOkres(okres);
		miejsce.setBranza(branza);
		listaDoDodania.add(miejsce);							// dodaj do listy - zapis do bazy na koñcu
		
		okres =  new Select().from(TabOkres.class).where("nazwa = ?", "XVII").executeSingle(); // znajdz okres dla budynku
		miejsce = new TabMiejsce("Muzeum Historii ¯ydów Polskich", "Mordechaja Anielewicza 6", false, "25.05.2005", "zespó³ fiñskich architektów kierowany przez Rainera Mahlamäkiego", 
				"Muzeum znajduj¹ce siê w Œródmieœciu Warszawy, na Muranowie dokumentuj¹ce wielowiekow¹ historiê ¯ydów w Polsce.");
		miejsce.setOkres(okres);
		miejsce.setBranza(branza);
		listaDoDodania.add(miejsce);							// dodaj do listy - zapis do bazy na koñcu
		
		okres =  new Select().from(TabOkres.class).where("nazwa = ?", "XVII").executeSingle(); // znajdz okres dla budynku
		miejsce = new TabMiejsce("Brama G³ówna Uniwersytetu Warszawskiego", "Krakowskie Przedmieœcie  26/28", false, "1911", "Zygmunt Langman", 
				"Stanowi centralne wejœcie na kampus g³ówny uczelni. Znajduje siê przy ulicy Krakowskie Przedmieœcie pod numerem 26/28, który odnosi siê te¿ do wiêkszoœci budynków na kampusie.");
		miejsce.setOkres(okres);
		miejsce.setBranza(branza);
		listaDoDodania.add(miejsce);							// dodaj do listy - zapis do bazy na koñcu
		
		okres =  new Select().from(TabOkres.class).where("nazwa = ?", "XVII").executeSingle(); // znajdz okres dla budynku
		miejsce = new TabMiejsce("Syrenka na warszawskiej Starówce", "Rynek Starego Miasta", false, "1855", "Konstanty Hegel", 
				"W 2008 roku oryginalna rzeŸba wykonana z br¹zowionego cynku zosta³a zabrana z rynku w celu wykonania prac konserwatorskich. RzeŸba by³a w bardzo z³ym stanie technicznym na skutek urazów mechanicznych i licznych aktów wandalizmu. 1 maja 2008 o 6 rano na cokó³ na rynku wróci³a oryginalna rzeŸba, jednak 12 maja orygina³ przeniesiono do Muzeum Historycznego m. st. Warszawy, jego miejsce zast¹pi³a kopia.");
		miejsce.setOkres(okres);
		miejsce.setBranza(branza);
		listaDoDodania.add(miejsce);							// dodaj do listy - zapis do bazy na koñcu
		
		okres =  new Select().from(TabOkres.class).where("nazwa = ?", "XVII").executeSingle(); // znajdz okres dla budynku
		miejsce = new TabMiejsce("Filtry Lindleya", "Koszykowa 81", false, "1886", "William Lindley", 
				"Warszawskie filtry wody, zlokalizowane miêdzy ulicami Koszykow¹, Krzywickiego[3], Filtrow¹ i Raszyñsk¹.Warszawskie filtry nale¿¹ do systemu wodoci¹gów warszawskich. Od 2012 r. ca³y kompleks, ³¹cznie z budynkami wybudowanymi dwa lata wczeœniej, zosta³ uznany za pomnik historii.");
		miejsce.setOkres(okres);
		miejsce.setBranza(branza);
		listaDoDodania.add(miejsce);							// dodaj do listy - zapis do bazy na koñcu
		
		okres =  new Select().from(TabOkres.class).where("nazwa = ?", "XVII").executeSingle(); // znajdz okres dla budynku
		miejsce = new TabMiejsce("Park Moczyd³o", "Górczewska", false, "Lata 60. XX wieku", "", 
				"Park na warszawskiej Woli - nazwany tak¿e od sztucznie usypanego tam wzniesienia Górk¹ Moczyd³owsk¹ - na terenie osiedla Ko³o, pomiêdzy ulicami: Deotymy, Górczewsk¹, Prymasa Tysi¹clecia i Czorsztyñsk¹.");
		miejsce.setOkres(okres);
		miejsce.setBranza(branza);
		miejsce.setLatitude(52.142400);
		miejsce.setLongitude(20.570719);
		listaDoDodania.add(miejsce);							// dodaj do listy - zapis do bazy na koñcu
		
	
		for (TabMiejsce x: listaDoDodania) {
			// TODO mo¿na zamieniæ na transakcjê - bêdzie szybciej
			dodanyId = x.save();
			Log.d(DEBUG_TAG, "Miejsce dodane ID="+dodanyId);	// zwraca -1 jeœli nie uda³o siê dodaæ! a id dodanego jeœli uda³o.
		}
		
		List<TabMiejsce> miejsca = new Select().from(TabMiejsce.class).execute();
		Log.d(DEBUG_TAG, 
				"Iloœæ miejsc w bazie: "+miejsca.size()+"\n"+
				"adres pierwszego to: "+miejsca.get(0).getAdres()+"\n"+
				"okres pierwszego to: "+miejsca.get(0).getOkres().getNazwa()+"\n"+
				"ID pierwszego to: "+miejsca.get(0).getId());
	}
}
