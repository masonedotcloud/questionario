package functionality;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Gestioni connessione al database
 */
public class DatabaseConnection {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private Connection conn;

    /**
     * Costruttore
     */
    public DatabaseConnection() {

        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(WebXmlParams.getDbUrl() + WebXmlParams.getDbName(), WebXmlParams.getUser(), WebXmlParams.getPass());

        } catch (Exception ignored) {

        }
    }

    /**
     * Verifica la connessione al database
     *
     * @return true se la connessione è attiva, false altrimenti
     */
    public static boolean checkConnection() {

        Connection check_conn;
        try {
            Class.forName(JDBC_DRIVER);

            check_conn = DriverManager.getConnection(WebXmlParams.getDbUrl() + WebXmlParams.getDbName(), WebXmlParams.getUser(), WebXmlParams.getPass());
            check_conn.close();
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    /**
     * Esecuzione query generiche
     *
     * @param query stringa di query
     * @return risultati
     */
    public ResultSet executeQuery(String query) {
        ResultSet rs = null;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
        } catch (Exception ignored) {

        }
        return rs;
    }

    /**
     * Modifica di valore nel database
     *
     * @param query stringa di query
     * @return vero o falso in base all'esecuzione
     */
    public boolean manipulationQuery(String query) {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(query);

            return stmt.getUpdateCount() > 0;
        } catch (Exception e) {
            return false;
        }

    }


}
