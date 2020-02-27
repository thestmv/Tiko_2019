import java.math.BigDecimal;

public class Tarvike {
    private String nimi;
    private float tukkuhinta;
    private float myyntihinta;
    private String type;
    private BigDecimal alv;
    private int id;

    public Tarvike(int id, String nimi, float tHinta, float mHinta, String type, BigDecimal vero) {
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
	public BigDecimal getAlv(){
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)  return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Tarvike other = (Tarvike) obj;
		if (id == (other).getId()) return true;
		return false;
	}

}