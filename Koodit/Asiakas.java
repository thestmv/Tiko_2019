package Koodit;
public class Asiakas {
    private int asiakasid;
    private String puhelinnumero;
    private String osoite;
    private String nimi;


    public Asiakas(int id, String pn, String o, String n) {
        asiakasid = id;
        puhelinnumero = pn;
        osoite = o;
        nimi = n;
    }

    public int getAsiakasid() {
        return asiakasid;
    }

    public String getNimi() {
        return nimi;
    }

    public String getOsoite() {
        return osoite;
    }

    public String getPuhelinnumero() {
        return puhelinnumero;
    }

    @Override
    public String toString() {
        return "******************************\n" +
                "*  Nimi: " + nimi + "\n" +
                "*  Osoite: " + osoite + "\n" +
                "*  Puhelinnumero: " + puhelinnumero + "\n" +
                "*  Tunnus: " + asiakasid +  "\n" +
                "******************************\n";
    }
}
