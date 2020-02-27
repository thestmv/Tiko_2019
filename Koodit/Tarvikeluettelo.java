//package Koodit;

import java.util.HashMap;

public class Tarvikeluettelo<Tarvike,Int> {
    private HashMap tarvikkeet;


    public Tarvikeluettelo(HashMap tarvikkeet) {
        this.tarvikkeet = tarvikkeet;
    }

    public Tarvikeluettelo() {
        tarvikkeet = new HashMap<>();
    }
	
	//Getteri
	public HashMap getTarvikkeet(){
		return tarvikkeet;
	}
	
	public void asetaTarvike(Tarvike t, int kpl){
		try {
			int lkm = (int)tarvikkeet.get(t);
			tarvikkeet.put(t,kpl + lkm);
		}
		catch (Exception e) {
			tarvikkeet.put(t,kpl);
		}
	}
		
	
	public int vahennaTarvike(Tarvike t, int kpl){
		try{
			tarvikkeet.put(t,(int)tarvikkeet.get(t)-kpl);
			if((int)tarvikkeet.get(t) <= 0){
				tarvikkeet.remove(t);
				return 0; // tarvike poistettu
			}
			return 1;
		}
		catch (Exception e){
			System.out.println("Kyseistä tarviketta ei ole!");
			return -1;
		}
	}
	

}