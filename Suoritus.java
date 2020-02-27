
import java.math.BigDecimal;
import java.util.HashMap;

public class Suoritus {
	
	//Attribuutit
	private int id;
    private BigDecimal kokonaisHinta;
    private String type; //urakka tai tavallinen
    private int tuntimaara;
    private float tuntiHinta;
    private float kotiTalousVahennys;
    private float alv;
    private String tyoTyyppi; // suunnittelu/normaali/aputyö
	private Tarvikeluettelo kaytetytTarvikkeet;
	
	
	public Suoritus(int id, BigDecimal kh, String t, int tm, float th, float ktv, float al, String tt){
		this.id = id;
		kaytetytTarvikkeet = new Tarvikeluettelo();
		this.kokonaisHinta = kh;
		type = t;
		tuntimaara = tm;
		this.tuntiHinta = th;
		kotiTalousVahennys = ktv;
		alv = al;
		tyoTyyppi = tt;
	}
	public Suoritus(BigDecimal kh, String t, int tm, float th, float ktv, float al, String tt){
		kaytetytTarvikkeet = new Tarvikeluettelo();
		this.kokonaisHinta = kh;
		type = t;
		tuntimaara = tm;
		this.tuntiHinta = th;
		kotiTalousVahennys = ktv;
		alv = al;
		tyoTyyppi = tt;
	}
	
	public HashMap<Tarvike, Integer> getTarvikkeet(){
		return kaytetytTarvikkeet.getTarvikkeet();
	}
	
	public void lisaaTarvike(Tarvike t, int kpl){
		kaytetytTarvikkeet.asetaTarvike(t, kpl);
	}
	
	public int vahennaTarvikkeita(Tarvike t, int kpl){
		return kaytetytTarvikkeet.vahennaTarvike(t, kpl);
	}

	//Setterit
	public void setId(int id) {
		this.id = id;
	}
	
	
	//Getteri
	public int getId(){
		return id;
	}
	
	public BigDecimal getKokonaisHinta() {
        return kokonaisHinta;
    }
	
	public String getType(){
		return type;
	}
	
	public int getTuntimaara(){
		return tuntimaara;
	}
	
	public float getTuntiHinta(){
		return tuntiHinta;
	}
	
	public float getKotiTalousVahennus(){
		return kotiTalousVahennys;
	}
	public float getAlv(){
		return alv;
	}
	public String getTyoTyyppi(){
		return tyoTyyppi;
	}
	
	//Setterit
	
	/* Jos tyyppi on urakka, niin tuntimaara on 
	 * 
	 */
	/*public void setType(){
		if(type.equals == urakka){
			tuntiHinta == null;
			tyoTyyppi == null;
		} else {
			
		}
	}*/
	
	    @Override
    public String toString() {
        return "******************************\n" +
				"*  TyösuoritusID: " + id + "\n" +
                "*  kokonaisHinta: " + kokonaisHinta + "\n" +
                "*  Tyyppi: " + type + "\n" +
                "*  Tuntimaara: " + tuntimaara + "\n" +
                "*  TuntiHinta: " + tuntiHinta +  "\n" +
				"*  KotiTalousVahennys: " + kotiTalousVahennys + "\n" +
                "*  Alv: " + alv + "\n" +
                "*  tyoTyyppi: " + tyoTyyppi + "\n" +
                "******************************\n";
    }


	public void setKokonaisHinta(BigDecimal kokonaisHinta) {
		this.kokonaisHinta = kokonaisHinta;
	}
}