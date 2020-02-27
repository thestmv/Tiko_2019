
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
	public HashMap<Tarvike, Integer> getTarvikkeet(){
		return tarvikkeet;
	}
	
	public void asetaTarvike(Tarvike t, int kpl){
		try {
			if (tarvikkeet.containsKey(t)) {
				tarvikkeet.put(t,kpl + (int)tarvikkeet.get(t));
			} else {
				tarvikkeet.put(t,kpl);
			}

		}
		catch (Exception e) {
			System.out.println("tarvikkeen lisäämisessä ongelmaa (tarvikeluettelo)");
			System.out.println(e + "\n");
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