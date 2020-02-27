//package Koodit;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.HashMap;

/* Käyttöliittymä JDBC- ohjelmaan
 * 
 * 
 *  
 */
public class UserInterface {
    private ConnectDB database;
    private Connection connection;
    private Scanner inputReader;
    private LinkedList<Projekti> projektit;
	private Asiakas asiakas;
    private Projekti aktiivinenProjekti;
    private Suoritus aktiivinenSuoritus;


    /*Vakiot
     *
     */
    final String EXIT = "poistu";
    final String LISTA = "list";
    final String KYLLA = "y";
    final String EI = "n";
    final float TARVIKEALV = 0.24f;
    final float KIRJAALV = 0.1f;
    static final String TAVALLINEN = "tavallinen";
    static final String URAKKA = "urakka";
    static final String SUUNNITTELU = "suunnittelu";
    static final String NORMAALI = "normaalityo";
    static final String APUTYO = "aputyo";



	/* Rakentaja
	 * 
	 */
    public UserInterface() {
		database = new ConnectDB();
        connection = database.getCon1();
		inputReader = new Scanner(System.in);
		
		//LinkedList projektit = new LinkedList<>();

		//LinkedList<RaporttiNide> raportti = new LinkedList();
        reset();

    }

    public boolean run() {

        System.out.println("***********************");
        System.out.println("****Tmi Sähkötärsky****");
        System.out.println("***********************");
        System.out.println("*******Sähkötyöt*******");
        System.out.println("***********************");
        System.out.println("******Tervetuloa!******");
        System.out.println("*****Seppo Tärsky******");
        System.out.println("***********************\n");

        boolean running = true;

        while (running) {
            if (asiakas != null) {
                System.out.println("Tämänhetkinen asiakas:\n" + asiakas + '\n');

                System.out.println("********************************");
                System.out.println("*  1. Lisää uusi projekti      *");
                System.out.println("*  2. Hae nykyiset projektit   *");
                System.out.println("*  3. Tyhjennä asiakasvalinta  *");
                System.out.println("*  4. Poista asiakas           *");
                if (projektit.size() > 0){
					System.out.println("*  5. Valitse projekti         *");
					System.out.println("*  6. Listaa projektit         *");
				}
                System.out.println("********************************");

                String cmd = inputReader.nextLine();
                switch (cmd) {
                    case "1":
                        lisaaProjekti();
                        break;
                    case "2":
                        haeProjektit();
                        break;
                    case "3":
                        reset();
                        break;
                    case "4":
                        poistaAsiakas();
                        break;
					case "5":
					    if (projektit.size() > 0) {
					        valitseProjekti();
                            break;
                        }
					case "6":
					    if (projektit.size() > 0) {
					        listaaProjektit();
                            break;
                        }
                    default:
                        System.out.println("Virheellinen komento");
                        continue;
                }
            }
			else {
				System.out.println("");
                System.out.println("Asiakasta ei asetettu.\n");

                System.out.println("*******************************");
                System.out.println("*  1. Luo uusi asiakas        *");
                System.out.println("*  2. Hae asiakastiedot       *");
                System.out.println("*  3. Poistu ohjelmasta       *");
				System.out.println("*  4. Hallitse tarvikkeita    *");
				System.out.println("*  5. Luo raportti            *");
				System.out.println("*******************************");
				System.out.println("*  6. Tehtävien testaus       *");
                System.out.println("*******************************");
                String cmd = inputReader.nextLine();
                switch (cmd) {
                    case "1":
                        lisaaAsiakas();
                        break;
                    case "2":
                        haeAsiakas();
                        break;
                    case "3":
                        running = false;
                        break;
					case "4":
                        hallitseTarvikkeita();
                        break;
					case "5":
                        luoRaportti();
                        break;
					case "6":
                        //tehtavanAntoMenu();
                        break;
                    default:
                        System.out.println("Virheellinen komento");
                        continue;
                }
            }
        }
        return true;
    }
    private void projektiMenu() {
        boolean running = true;
        while (running) {
            System.out.println("");
            //System.out.println("Tämänhetkinen asiakas:\n" + asiakas + '\n');
            System.out.println("Valittu projekti:\n" + aktiivinenProjekti + '\n');
            /*Yksi tarvikeluettelo aina jokaista työsuoritusta kohden
             * Tarvikkeet lisätään tarvikelistasta, joka on luotu aiemmin.
             * ->Saldot toimivat näin oikein.
             */
            System.out.println("*******************************************");
            System.out.println("*  Projektin käsittelyvalikko             *");
            System.out.println("*  1. Lisää työsuoritus                   *");
			System.out.println("*                   *");
			System.out.println("*  3. Avaa suoritus                       *");
            System.out.println("*-----------------------------------------*");
            System.out.println("*  4. Luo lasku                           *");
            System.out.println("*-----------------------------------------*");
            System.out.println("*  5. Tyhjennä projektivalinta            *");
            System.out.println("*  6. Poista Projekti                     *");
            System.out.println("*******************************************");
            String cmd = inputReader.nextLine();
            switch (cmd) {
                case "1":
                    lisaaTyosuoritus(); // Lisää työsuorituksen ja sen kylkeen tarvikeluettelon
					break;
                case "2":
                    //haeSuoritukset(); //Tää operaatio vittuun käyttöliittymästä koska
					//Tää luo niitä listoja sinne.
                    break;
                case "3":
                    suoritusMenu();
					break;
                case "4":
                    //luoLasku();
					break;
                case "5":
                    aktiivinenProjekti = null;
                    return;
                case "6":
                    //poistaProjekti();
                default:
                    System.out.println("Virheellinen komento");
					continue;
            }
        }

    }

	private void suoritusMenu() {
        ArrayList<Suoritus> suoritukset = aktiivinenProjekti.getSuoritukset();
        if (suoritukset.size() <= 0) {
            System.out.println("Projektilla ei ole suorituksia!");
            return;
        }
        System.out.println("Minkä suorituksen haluat avata?");
        for (int i = 0; i < suoritukset.size(); i++) {
			Suoritus s = suoritukset.get(i);
            //System.out.println(i + ".\n" + suoritukset.get(i));
			System.out.println((i+1) +" - " + 
			s.getId() + "| " + s.getKokonaisHinta() + 
			"|" + s.getType() + "| " + s.getTyoTyyppi() + "| "+
			s.getTuntimaara() + "h ");
        }
		try{
			aktiivinenSuoritus = suoritukset.get(inputReader.nextInt()-1);
			//aktiivinenSuoritus.haeSuoritukset();
		}catch(Exception e){
			System.out.println("Virheellinen ID annettu");
			return;
		}
		

        System.out.println("");
        //System.out.println("Tämänhetkinen asiakas:\n" + asiakas + '\n');
        //System.out.println("Valittu projekti:\n" + aktiivinenProjekti + '\n');
		System.out.println("Käsiteltävä työsuoritus on:\n" + aktiivinenSuoritus + '\n');
                /* Tämä osio käsittelee työsuoritusta projektissa. Niitä voi
                 * lisätä ja samoin lisätä tarvikkeita työsuoritukseen
                 *
                 * Jos visioin oikein, niin aiemmin ollaan luotu työsuoritus
                 * ja samalla sille tarvikeluettelo. Tässä sille lisäillään
                 * tarvikkeita sit.
                 */
        //System.out.println("*******************************************");
			boolean running = true;
			while (running) {			
            System.out.println("*              Työsuorituksen             *");
            System.out.println("*             käsittelyvalikko            *");
            System.out.println("*  1. Lisää käytetty tarvike              *");
            System.out.println("*  2. Hae Kaupan tarvikkeet               *");
            System.out.println("*  3. Näytä työsuorituksen tarvikelista   *");
            System.out.println("*-----------------------------------------*");
            System.out.println("*  4. Takaisin                            *");
            System.out.println("*  5. poistaTarvikeSuorituksesta          *");
			
            System.out.println("*******************************************");
			

            String cmd = inputReader.nextLine();
            switch (cmd) {
                case "1":
					lisaaTarvike(haeTarvike());
					break;
                case "2":
					tulostaTarvikeLuettelo();
					break;
                case "3":
					tulostaTyosuorituksenTarvikelista();
					break;
                case "4":
                    aktiivinenSuoritus = null;
                    running = false;
                    continue;
                case "5":
                    poistaTarvikeSuorituksesta();
					break;
                default:
                    System.out.println("Virheellinen komento");
					continue;
            }
        }
    }
	
    private void hallitseTarvikkeita() {

        boolean running = true;
        while (running) {
            System.out.println("**********************************");
            System.out.println("*  1. Luo uusi tarvike           *");
            System.out.println("*  2. Poista tarvike             *");
            System.out.println("*  3. Näytä tarvikelista         *");
            System.out.println("*  4. Muokkaa tarvikkeen tietoja *");
            System.out.println("*  5. Palaa päävalikkoon         *");
            System.out.println("**********************************");
            String cmd = inputReader.nextLine();
            switch (cmd) {
                case "1":
                    luoUusiTarvike("");
                    break;
                case "2":
                    //poistaTarvike();
                    break;
                case "3":
                    tulostaTarvikeLuettelo();
                    break;
                case "4":
                    //asetaTarvikkeenTiedotUudelleen();
                    break;
                case "5":
                    run();
                    break;
                default:
                    System.out.println("Virheellinen komento");
                    continue;
            }

        }
    }

	private void poistaTarvikeSuorituksesta(){
		HashMap tarvikkeet = aktiivinenSuoritus.getTarvikkeet();
		System.out.println(tarvikkeet);
		tarvikkeet.forEach((key,value) -> System.out.println(key + " - " + value));
		
		
	}

	private void lisaaTarvike(Tarvike tarvike){
		
		PreparedStatement stmn = null;
		
		System.out.println("Anna Tarvikkeen " + tarvike.getType() + " määrä: ");
		
		int maara = inputReader.nextInt();
		
		String sql = "INSERT INTO tarvikeluettelo(tyosuoritusid, tarvikeid, maara) VALUES (?,?,?)";
		
		try {
            stmn = connection.prepareStatement(sql);
        } catch (Exception e) {
            System.out.println("Connection failed!"
            + e);

        }
		
		try {
			stmn.setInt(1, aktiivinenSuoritus.getId()); //Asettaa tyosuoritusID; tähän.
			stmn.setInt(2, tarvike.getId()); //Asettaa tarvikeID; tähän.
			stmn.setInt(3, maara); //Asettaa määrä
			
			stmn.executeUpdate();
			System.out.println("Tarvikkeen " + tarvike.getNimi() + " lisäys onnistui");
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
    private Tarvike haeTarvike() {
        System.out.println("Anna lisättävän tarvikkeen nimi.:");
        Tarvike tarvike = null;
        String nimi = inputReader.nextLine();
        String sql = "SELECT DISTINCT * " +
                "FROM tarvike " +
                "WHERE tarvikenimi LIKE '%" + nimi.replace("\"", "") + "%';";
        PreparedStatement stmn = null;

        try {
            stmn = connection.prepareStatement(sql);
        } catch (Exception e) {
            System.out.println("Connection failed!" + e);
        }

        LinkedList<Tarvike> loydetyt = new LinkedList<Tarvike>();

        try {
            ResultSet rs = stmn.executeQuery();
            while (rs.next()) {
                loydetyt.add(new Tarvike(rs.getInt(1),
                        rs.getString(2),
                        rs.getFloat(3),
                        rs.getFloat(4),
                        rs.getString(5),
                        rs.getFloat(6)));

            }
        } catch (Exception e) {
            System.out.println("Haku ei onnistunut: " + e);
        }

        if (loydetyt.size() > 0) {
            System.out.println("Tarkoititko jotain näistä? Kirjoita vastaava numero listasta:" );
            for (int i = 0; i < loydetyt.size(); i++) {
                Tarvike t = loydetyt.get(i);
                System.out.println(i + 1 + ".\n" + t);

            }
            System.out.println();
            String input = inputReader.nextLine();

            try {
                //tarvike = loydetyt.get(Integer.parseInt(input.trim())-1);
                return loydetyt.get(Integer.parseInt(input.trim())-1);
				//return tarvike;
            } catch (Exception e) {
                System.out.println("Jokin meni pieleen. Luodaan uusi tarvike.");
            }
		


        } else {
			System.out.println("Tarviketta ei löytynyt, joten luodaan uusi tarvike.");
			tarvike = luoUusiTarvike(nimi);
			return tarvike;
			//return luoUusiTarvike();
        }
		return null;
    }

    private int lisaaProjekti() {
        PreparedStatement stmn = null;
        String sql = "INSERT INTO tyoprojekti(asiakasid,tila,projektihinta,kohteenosoite)" +
                "VALUES(?,?,?,?)";
        try {
            stmn = connection.prepareStatement(sql);
        } catch (Exception e) {
            System.out.println("Connection failed!"
                    + e);
            return 0;
        }

        //asetetaan toiminnalle parametrit
        try {
            stmn.setInt(1,asiakas.getAsiakasid());
			System.out.println(asiakas.getAsiakasid());
            stmn.setBoolean(2,false);
            stmn.setFloat(3,0);
            System.out.println("Uuden kohteen osoite:");
            stmn.setString(4, inputReader.nextLine());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            return stmn.executeUpdate();
        } catch (Exception e) {
            System.out.println("Ei onnistunut kohteen lisäys: \n" + e + '\n');
        }
        return 0;
    }
	private void haeProjektit() {
        PreparedStatement stmn = null;
        ResultSet results = null;
		
        System.out.println("Haetaan asiakkaan projektit");
		try{

			//Projektin haku käsittelyssä olevalle asiakkaalle, toimio
			PreparedStatement prstmt = connection.prepareStatement("SELECT projektiid, " +
			"asiakasid, tila, projektihinta, kohteenosoite " +
					"FROM tyoprojekti " +
					"WHERE asiakasid = '" + asiakas.getAsiakasid() + "'");
			//Tulostaa yläindeksin ja tiedot tiedoista.
			ResultSet rs = prstmt.executeQuery();
			
            LinkedList projektit = new LinkedList<Projekti>();
			while(rs.next()){

				Projekti proj = new Projekti(
				        rs.getInt("projektiid"),
                        rs.getBoolean("tila"),
                        rs.getFloat("projektihinta"),
                        rs.getString("kohteenosoite"));
				projektit.add(proj);
				
			}
			if (projektit.size() <= 0) {
                System.out.println("Ei ollut projekteja!");
                return;
            }
			this.projektit = projektit;

		}catch(SQLException poikkeus) {
			System.out.println("Tapahtui virhe");
		}
	}
	private void listaaProjektit() {
		for (Projekti p : projektit) {
            System.out.println(p);
        }
		
    }
	private void valitseProjekti(){
	    // projektit -linkedlistissä on projektit muistissa tällä hetkellä!
		System.out.println("Anna projektin ID listasta. Kirjoita \"" + EXIT + "\" poistuaksesi.");
		
		System.out.println("Löydetyt projektit:");
		for (Projekti p : projektit) {
            System.out.println(p.getMetadata());

        }
        while (true) {
            String search = inputReader.nextLine();
            try {
                if (search.equals(EXIT)) {
                    return;
                }
                int temp = Integer.parseInt(search);
                for (Projekti p : projektit) {
                    if (p.getId() == temp) {
                        aktiivinenProjekti = p;
						haeSuoritukset(false);
                        projektiMenu();
                        return;
                    }
                }
				throw new Exception("Väärä arvo!");

            } catch (Exception e) {
                System.out.println("Virhe tapahtui. Syötä oikea arvo. \"" + EXIT + "\" poistuaksesi.");
            }
        }

		
	}
	
	/* Hakee suoritukset projektiID:llä
	 * 
	 */
	private void haeSuoritukset(boolean printataanko){
		
		PreparedStatement stmn = null;
		ResultSet results = null;
		
		System.out.println("Hakee työsuoritukset projektiID:n perusteella.");
		//Hakulause
		String sql = "SELECT * " +
		"FROM tyosuoritus " +
		"WHERE projektiid = '" + aktiivinenProjekti.getId() + "'";
		
        try {
            stmn = connection.prepareStatement(sql);
			//.Statement.RETURN_GENERATED_KEYS
        } catch (Exception e) {
            System.out.println("Connection failed!" + e);
        }
		
		try{
			ResultSet rs = stmn.executeQuery();
			while(rs.next()){
				//Hakee projektiID:n mukaiset työsuoritukset.
		
				if(printataanko){			
					System.out.println(rs.getInt(1) + "-" + rs.getFloat(3) + "-" + rs.getString(4) + "-" + rs.getFloat(6));
				} else{
					aktiivinenProjekti.lisaaSuoritus(new Suoritus(
						rs.getInt(1),
                        rs.getFloat(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getFloat(6),
                        rs.getFloat(7),
                        rs.getFloat(8),
                        rs.getString(9)));
				}
			}
		}
		catch(Exception e){
			System.out.println("Eipäs löytyny tarvikesuoritusta");
		}
		
	}
	
	
	private void lisaaTyosuoritus(){
		PreparedStatement stmn = null;
		//ResultSet results = null;
		
		String sql = "INSERT INTO tyosuoritus(projektiid,kokonaishinta,tyosuoritustyyppi, " +
		"tuntimaara,tuntihinta,kotitalousvahennys,alv,tyotyyppi)VALUES(?,?,?,?,?,?,?,?)";
				
        try {
            stmn = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } catch (Exception e) {
            System.out.println("Connection failed!"
            + e);

        }
        Suoritus uusiSuoritus = null;
		//asetetaan toiminnalle parametrit
        try {
			System.out.println("*Lisätään työsuoritus asetettuun projektiin. *");
			System.out.println("*--------------------------------------------*");
			System.out.println("*Onko kyseessä urakka vai tavallinen työ?    *");
			System.out.println("*1 - Tavallinen                              *");
			System.out.println("*2 - Urakka                                  *");
			System.out.println("*Kirjoita - " + EXIT + " niin pääset takaisin      *");
			//System.out.println("*Kirjoita - poistu niin pääset takaisin      *");

			String syote = inputReader.nextLine();
			
			if (syote.equals(EXIT)) {
                    return;
            }
			
			
			//Projekti id peritään luokasta.
			//System.out.println(aktiivinenProjekti.getId());
			/*Jos tavallinen työ, niin peritään hinta luokasta...
			*/
            stmn.setInt(1,aktiivinenProjekti.getId()); //Asettaa projektiID; tähän.

            float tuntitaksa = 0;
            int tuntimaara = 0;
            float kokonaishinta = 0;
            float kotitalousvahennys = 0;
            float verollinenHinta = 0;
            String tt = "";


            String tyotyyppi = "";


			switch (syote) {
                case "1":
                    tt = TAVALLINEN;

                    System.out.println("Valitse Työtyyppi");
                    System.out.println("1 - Suunnittelu\n" +
                            "2 - normaalityö\n" +
                            "3 - aputyö");
                    String ryp = inputReader.nextLine();

                    System.out.println("Aseta tuntimäärä: ");
                    tuntimaara = inputReader.nextInt();
                    switch (ryp) {
                        case "1":
                            tyotyyppi = SUUNNITTELU;
                            tuntitaksa = 55;
                            kokonaishinta = 55 * tuntimaara;
                            break;
                        case "2":
                            tyotyyppi= NORMAALI;
                            tuntitaksa = 45;
                            kokonaishinta = 45 * tuntimaara;
                            break;
                        case "3":
                            tyotyyppi = APUTYO;
                            tuntitaksa = 35;
                            kokonaishinta = 35 * tuntimaara;
                            break;
                        default:
                            System.out.println("error temp");
                    }
                    verollinenHinta = tuntimaara * tuntitaksa * 1.24f;
                    break;
                case "2":
                    tt = URAKKA;
                    tyotyyppi = null;
                    System.out.println(syote);
                    System.out.println("Syötä urakan kokonaishinta");
                    kokonaishinta = inputReader.nextFloat();
                    verollinenHinta = kokonaishinta;
                    break;
                default:
                    System.out.println("Jotain meni vikaan, aloitetaan alusta.");
                    lisaaTyosuoritus();
			}

            if (verollinenHinta > 100f) {
                kotitalousvahennys = 0.5f * verollinenHinta - 100f;

            }
            stmn.setInt(1,aktiivinenProjekti.getId()); //Asettaa projektiID; tähän.
            stmn.setFloat(2, kokonaishinta); //asetetaan kokonaishinta
            stmn.setString(3,tt); // Työsuoritustyyppi
            stmn.setInt(4, tuntimaara); // tuntimäärä on null
            stmn.setFloat(5, tuntitaksa); // Tuntihinta on null
            stmn.setFloat(6, kotitalousvahennys); // Kotitalousvähennys, luo olio
            stmn.setFloat(7, 0.24f); // ALV, luo olio
            stmn.setString(8, tyotyyppi); // suunnittelu/normaali/aputyö.
            uusiSuoritus = new Suoritus(kokonaishinta,tyotyyppi,tuntimaara,tuntitaksa,kotitalousvahennys,0.24f,tt);

        }
		catch (Exception e) {
            e.printStackTrace();
        }
		try {
            stmn.executeUpdate();
            ResultSet rs = stmn.getGeneratedKeys();
			
			if(rs.next()){
                uusiSuoritus.setId(rs.getInt(2));
			} else{
			    throw new Exception("Ei löytynyt tunnusta.");

			}

            aktiivinenProjekti.lisaaSuoritus(uusiSuoritus);

            System.out.println("Työsuoritus luotu onnistuneesti!");
        } catch (Exception e) {
            System.out.println("EI ONNISTUNUT LISÄYS: " + e);
        }
		
	}
    private void haeAsiakas() {
        PreparedStatement stmn = null;
        ResultSet results = null;
        System.out.println("Hae asiakastiedot nimellä, tai asiakastunnuksella:");
		System.out.println("Kirjoittamalla -list listataan kaikki asiakkaat");
		System.out.println("--------------------------------------------------");
        String temp = inputReader.nextLine();
		
		if (temp.equals(LISTA)){
			try{
				PreparedStatement prstmt = connection.prepareStatement("SELECT asiakasid, " +
				"osoite, puhelinnumero, nimi " +
						"FROM asiakas ");
				//Tulostaa yläindeksin ja tiedot asiakkaista.
				ResultSet rs = prstmt.executeQuery();
				System.out.println("Asiakkaiden tiedot:");
				System.out.println("AsiakasID |      osoite       | puhelinnumero | nimi |");
				System.out.println("----------+-------------------+---------------+------+-");
				
			while(rs.next()){
				System.out.print("|     ");
				System.out.print(rs.getInt("asiakasid") + "   | ");
				System.out.print(rs.getString("osoite") + "     | ");
				System.out.print(rs.getString("puhelinnumero") + "|  ");
				System.out.print(rs.getString("nimi") + "  |  ");
				System.out.println("");
			}
			System.out.println("------------------------------------------------------------");
				//Palauttaa käyttäjän komennon alkuun valitsemaan asiakkaansa.
				haeAsiakas();
				
			}catch(SQLException poikkeus) {
				
				System.out.println("Tapahtui virhe");
				//return 0;
				//System.out.println(asiakas.getAsiakasid());
			}
		}
		//SQL- kysely 
        String sql = "SELECT * " +
                "FROM asiakas " +
                "WHERE asiakasid=?::int OR nimi=?::varchar";
        //Alustetaan sql komento
        try {
            stmn = connection.prepareStatement(sql);
        } catch (Exception e) {
            System.out.println("Connection not established!!");
        }
        //Yritetään ensin koettaa ID perusteella
        try {
            stmn.setInt(1, Integer.parseInt(temp));
            stmn.setString(2, "");

        } catch (Exception e) {
            System.out.println("Ekan kohdan jälkeen error:" + e);
            try {
                stmn.setInt(1, 0);
                stmn.setString(2, temp);
            } catch (Exception e1) {

                System.out.println("Nyt meni jokin erittäin pieleen.\n" + e1 + "\n" );
            }
        }
        try {
            results = stmn.executeQuery();
        } catch (SQLException e1) {
            System.out.println("Ei tuloksia.\n");
        }

        //talletetaan nykyinen asiakas
        try {
            results.next();
            int id = 0;
            try {
                id = results.getInt("asiakasid");
            } catch (Exception e) {}
            String numero = results.getString("puhelinnumero");
            String osoite = results.getString("osoite");
            String nimi = results.getString("nimi");
            this.asiakas = new Asiakas(id,numero,osoite,nimi);
            //System.out.println(this.asiakas);

        } catch (SQLException e) {
            System.out.println("Asiakasta ei löytynyt!");
        }
    }
    private int lisaaAsiakas() {
        PreparedStatement stmn = null;
        String sql = "INSERT INTO asiakas(puhelinnumero,osoite,nimi) VALUES(?,?,?)";
        try {
            stmn = connection.prepareStatement(sql);
        } catch (Exception e) {
            System.out.println("Connection failed!"
            + e);
            return 0;
        }

        //Yritetään asettaa hakuparametrit SQL-hakuun
        try {
            System.out.println("Kirjoita \"poistu\" poistuaksesi lisäyksestä");

            System.out.println("Aseta puhelinnumero");
            String temp = inputReader.nextLine();
            if (temp.equals(EXIT)) return 0;
            stmn.setString(1,temp);

			System.out.println("Aseta osoite");
            temp = inputReader.nextLine();
            if (temp.equals(EXIT)) return 0;
            stmn.setString(2, temp);

			System.out.println("Aseta nimi");
            temp = inputReader.nextLine();
            if (temp.equals(EXIT)) return 0;
            stmn.setString(3, temp);

        } catch (SQLException e) {
            System.out.println("Tiedon asetus epäonnistui:" + e);
        }

        try {
            stmn.executeUpdate();
            System.out.println("Asiakas luotu onnistuneesti!");

        } catch (Exception e) {
            System.out.println("EI ONNISTUNUT LISÄYS: " + e);
        }
        return 0;
    }
	/* Operaatio poistaa käsittelyssä olevan asiakkaan
	 * (Tarvitaan vielä operaatio, joka poistaa hänen projektit ensin,
	 * sillä asiakasta ei voi poistaa, jos hänelle on luotu projekteja.)
	 *
	 */ 
	private void poistaAsiakas(){
		
		
		PreparedStatement stmn = null;
		ResultSet results = null;
		
		System.out.println("**************************************");
		System.out.println("*Haluatko varmasti poistaa asiakkaan?*");
		System.out.println("*Kyllä = 1                           *");
		System.out.println("*Ei = 2                              *");
		System.out.println("*Palaa alkuun = 3                    *");
		System.out.println("**************************************");
		
		String cmd = inputReader.nextLine();
		
            switch (cmd) {
                case "1":
				int asiakkaanId;
				asiakkaanId = asiakas.getAsiakasid();
				System.out.println(asiakas.getAsiakasid());
				System.out.println("Haluatko varmasti poistaa käsittelyssä olevan asiakkaan? y/n");
				String syote = inputReader.nextLine();
                    
					if(syote.equals(KYLLA)){
						try{
							//Projektin haku käsittelyssä olevalle asiakkaalle, toimio
							PreparedStatement prstmt = connection.prepareStatement("DELETE " +
							"FROM asiakas " +
							"WHERE asiakasid = '" + asiakkaanId + "'");

						}
						catch (SQLException e1) {
							System.out.println("Asiakkaan poisto ei onnistunut \n" + e1 + '\n');
						}
						try {
							stmn.executeUpdate();
							System.out.println("Asiakas poistettu onnistuneesti!");
						} catch (Exception e) {
							System.out.println("Asiakkaan poisto ei onnistunut \n" + e + '\n');
						}
					} 
					if(syote.equals(EI)){
						run();
					}
                    break;
                case "2":
					
				System.out.println("************************");
				System.out.println("*Asiakasta ei poistettu*");
				System.out.println("*Palataan päävalikkoon *");
				System.out.println("************************");
                    run();
                    break;
                case "3":
                    run();
			}
		
	}
	
	/* Operaatiolla luodaan järjestelmään uusi tarvike.
	 * Näitä tarvikkeita voidaan käyttää työsuorituksissa.
	 * Korjataan vielä hinnoittelu ja alv automaattiseksi.
	 */
	private Tarvike luoUusiTarvike(String nimi){
	    Tarvike tarvike = null;
		PreparedStatement stmn = null;
        String sql = "INSERT INTO tarvike(tarvikenimi, sisaanostohinta, myyntihinta, yksikkotyyppi, alv)" +
		"VALUES(?,?,?,?,?)";

        float sisaanostohinta = 0;
        float myyntihinta = 0;
        String yksikkoType = "";
        float vero = 0;


        try {
            stmn = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        } catch (Exception e) {
            System.out.println("Connection failed!"
            + e);
            //return 0;
        }
	
		//Yritetään asettaa hakuparametrit SQL-hakuun
        try {
			
			if(nimi.length() <= 0){
				System.out.println("Anna tarvikkeen nimi");
				nimi = inputReader.nextLine();
			}
			
			stmn.setString(1, nimi.trim().toLowerCase());
			System.out.println("Anna sisaanostohinta");
            sisaanostohinta = inputReader.nextFloat();
            stmn.setFloat(2, sisaanostohinta);

			//Myyntihinta on sisaanostohinta ja siihen lisätään 25%
            myyntihinta = sisaanostohinta * 1.25f;
			stmn.setFloat(3, myyntihinta);

			//Korjattu tupla readerillä. Tää täytyy kyl fixata.
			System.out.println("Anna yksikkotyyppi:");
            inputReader.nextLine();
			yksikkoType = inputReader.nextLine();
            stmn.setString(4, yksikkoType);
			
			//Arvolisäkannan valinta
			boolean falseInput = true;
			while (falseInput) {
				System.out.println("Anna ALV");
				System.out.println("1 - 24%");
				System.out.println("2 - 10%");
				String syote = inputReader.nextLine();
			
				switch (syote) {
					case "1":
						stmn.setFloat(5, TARVIKEALV);
						vero = TARVIKEALV;
						falseInput = false;
						break;
					case "2":
						stmn.setFloat(5, KIRJAALV);
						vero = KIRJAALV;
						falseInput = false;
						break;
					default:
						System.out.println("Yritä uudelleen :)");
				}
            }
        } catch (SQLException e) {
            System.out.println("Tiedon asetus epäonnistui:" + e);
        }

        try {
            stmn.executeUpdate();
            ResultSet rs = stmn.getGeneratedKeys();
            rs.next();

            tarvike = new Tarvike(rs.getInt(1),nimi,sisaanostohinta,myyntihinta,yksikkoType,vero);
            System.out.println("Tarvike luotu onnistuneesti!");
			return tarvike;
        } catch (Exception e) {
            System.out.println("EI ONNISTUNUT LISÄYS: " + e);
        }

		return null;
    }
	/* Operaatio tulostaa luettelon tarvikkeista, jotka on luotu.
	 *
	 */
	private void tulostaTarvikeLuettelo(){
		PreparedStatement stmn = null;
        ResultSet results = null;
		
        System.out.println("Haetaan luodut tarvikkeet");
		try{
			//Projektin haku käsittelyssä olevalle asiakkaalle, toimio
			PreparedStatement prstmt = connection.prepareStatement("SELECT tarvikeid, " +
			"tarvikenimi, sisaanostohinta, myyntihinta, yksikkotyyppi, alv " +
					"FROM tarvike ");
			//Tulostaa yläindeksin ja tiedot tiedoista.
			ResultSet rs = prstmt.executeQuery();
			System.out.println("ID|tyyppi| alv |ohinta | mhinta | tnimi ");
			System.out.println("--|------|-----------------------------------");
			
			while(rs.next()){
				System.out.print(rs.getInt("tarvikeid") + " |  ");
				System.out.print(rs.getString("yksikkotyyppi") + " |");
				System.out.print(rs.getFloat("alv") + " | ");
				System.out.print(rs.getFloat("sisaanostohinta") + " |");
				System.out.print(rs.getFloat("myyntihinta") + " | ");
				System.out.print(rs.getString("tarvikenimi") + "| "+ "\n");
			}
			System.out.println("");
		}catch(SQLException poikkeus) {
			System.out.println("Tapahtui virhe");
		}
	}
	/* Operaatio poistaa tarvikkeen ID:n perusteella
	 * Tämä operaatio ei toimi vielä, joten se kaipaa fixausta.
	 */
	private void poistaTarvike(){
		PreparedStatement stmn = null;
		ResultSet results = null;
		System.out.println("Anna poistettavan tarvikkeen ID");
		
		/*regex - tarvikenimi
		 * ja if else lause varmistamaan käyttäjän oikeaa toimia.
		 * peruutusvalinta myös.
		 */ 
		System.out.println("Haluatko varmasti poistaa tarvikkeen ? + /*regex*/ + ");
		
		int poistettavaId;
		poistettavaId = inputReader.nextInt();
		

		try{
			//Tarvikkeen haku käsittelyssä olevalle valitsemalle numerolle
			PreparedStatement prstmt = connection.prepareStatement("DELETE " +
			"FROM tarvike " +
			"WHERE tarvikeid = '" + poistettavaId + "'");

		}catch (SQLException e1) {
			System.out.println("Tarvikkeen poisto ei onnistunut \n" + e1 + '\n');
		}
		try {
			stmn.executeUpdate();
			System.out.println("Tarvike poistettu onnistuneesti!");
		} catch (Exception e) {
			System.out.println("Tarvikkeen poisto ei onnistunut nytte \n" + e + '\n');
		}
		hallitseTarvikkeita();
	}


    private void reset() {
        projektit = new LinkedList<>();
        asiakas = null;
        aktiivinenProjekti = null;
        aktiivinenSuoritus = null;

    }
	/* Operaatio tulostaa käsittelyssä olevan työsuorituksen tarvikelistan
	 * Eli ne tarvikkeet, jotka on asetettu työsuoritukseen kpl määrineen.
	 * Tää auttaa Seppoa kattoo mitä tarvikkeita on käytetty.
	 */
	private void tulostaTyosuorituksenTarvikelista(){
		
		PreparedStatement stmn = null;
        ResultSet results = null;
		
        System.out.println("Työsuorituksessa olevat tarvikkeet");
		try{
			//Projektin haku käsittelyssä olevalle asiakkaalle, toimio
			PreparedStatement prstmt = connection.prepareStatement("SELECT DISTINCT " +
			"tl.tarvikeID, tl.maara, t.yksikkotyyppi, t.tarvikenimi, tl.tyosuoritusID " +
			"FROM tarvikeluettelo as tl, tarvike as t, tyosuoritus as ty " +
			"WHERE tl.tarvikeID = t.tarvikeID AND " +
			"tl.tyosuoritusID = '" + aktiivinenSuoritus.getId() + "'");
			//Tulostaa yläindeksin ja tiedot tiedoista.
			ResultSet rs = prstmt.executeQuery();
			System.out.println("TarvID| määrä |tyyppi| tarvikenimi    ");
			System.out.println("------|-------|------|----------------");
			
			while(rs.next()){
				System.out.print("|  ");
				System.out.print(rs.getString("tarvikeID") + "  |   ");
				System.out.print(rs.getInt("maara") + "   |  ");
				System.out.print(rs.getString("yksikkotyyppi") + " | ");
				System.out.print(rs.getString("tarvikenimi") + "\n");
				rs.getInt("tyosuoritusID");
				
			}
			System.out.println("");
		}catch(SQLException poikkeus) {
			System.out.println("Tapahtui virhe");
		}
		
		
		
	}
	
	private void luoRaportti(){
		System.out.println("Luo raportin R1");
		System.out.println("Luo raportin R2");
	}


}
