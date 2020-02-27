//package Koodit;
public class Tarvike {
    private String nimi;
    private float tukkuhinta;
    private float myyntihinta;
    private String type;
    private float alv;
    private int id;

    public Tarvike(int id, String nimi, float tHinta, float mHinta, String type, float vero) {
		this.id = id;
        this.nimi = nimi;
        tukkuhinta = tHinta;
        myyntihinta = mHinta;
        this.type = type;
        alv = vero;
        this.id = id;

    }
	
	public int getId(){
		return id;
	}
	
	public String getNimi(){
		return nimi;
	}
	
	public float getTukkuhinta(){
		return tukkuhinta;
	}
	
	public float getMyyntihinta(){
		return myyntihinta;
	}
	public String getType(){
		return type;
	}
	public float getAlv(){
		return alv;
	}

	
	@Override
	public String toString(){
		return "Nimi: " + nimi + "\n" +
				"Tukkuhinta: " + tukkuhinta + "\n" +
				"Myyntihinta: " + myyntihinta + "\n" +
				"Tyyppi: " + type + "\n" +
				"Alv: " + alv;
	}

}