package Koodit;


import java.util.ArrayList;

public class Projekti {
    private int id;
    private boolean onkoValmis;
    private float hinta;
    private String kohteenOsoite;
    private ArrayList<Suoritus> tyosuoritukset;



    public Projekti(int id, boolean valmis, float hinta, String o) {
        tyosuoritukset = new ArrayList<Suoritus>();
        this.id = id;
        onkoValmis = valmis;
        this.hinta = hinta;
		kohteenOsoite = o;

    }


    public ArrayList<Suoritus> getSuoritukset() {
        return tyosuoritukset;
    }


	
	
	public int getId() {
        return id;
    }

    public void lisaaSuoritus(Suoritus suoritus) {
        tyosuoritukset.add(suoritus);

    }
	


    public boolean GetOnkoValmis() {
        return onkoValmis;
    }

    public float getHinta() {
        return hinta;
    }
	
	public String getOsoite(){
		return kohteenOsoite;
	}



    public String getMetadata() {
        return id + " - " + kohteenOsoite;
    }
    @Override
    public String toString() {
		 return "******************************\n" +
                "*  ID: " + id + "\n" +
                "*  Osoite: " + kohteenOsoite + "\n" +
                "*  Hinta: " + hinta + "\n" +
                "*  Kohteen Tila: " + onkoValmis +  "\n" +
                "******************************\n";
    }
	
	
}