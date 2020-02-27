

import java.sql.*;


public class ConnectDB {

    private static final String PROTOKOLA = "jdbc:postgresql";
    private static final String PALVELIN = "dbstud2.sis.uta.fi";
    private static final int PORTTI = 5432;
    private static final String TIETOKANTA = "tiko2019r21";
    private static final String PASS = "salainenSana";
    private static final String USER = "tv428479";

    private Connection con1;



    public ConnectDB() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(PROTOKOLA + "://" + PALVELIN + ':' + PORTTI + "/" + TIETOKANTA, USER, PASS);
            con1 = con;

        } catch (Exception e) {
            System.out.println("Connection failed. Error: " + e);

        }
    }

    public Connection getCon1() {
        return con1;
    }

    private boolean closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                return true;
            } catch (Exception e) {
                System.out.println("Could not close connection. Error: " + e);
                return false;
            }
        }
        return true;
    }
}
