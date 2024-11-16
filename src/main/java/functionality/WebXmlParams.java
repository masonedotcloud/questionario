package functionality;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Classe utilizzata per leggere i parametri di connessione al database e password di login per l'amministrazione dal file web.xml.
 * Implementa l'interfaccia ServletContextListener per poter essere notificata quando il contesto
 * dell'applicazione viene inizializzato.
 */
@WebListener
public class WebXmlParams implements ServletContextListener {
    private static String realPath;
    private static String dbUrl;
    private static String dbName;
    private static String user;
    private static String pass;
    private static String adminUser;
    private static String adminPass;
    private static String nameServlet;

    /**
     * Restituisce URL del database.
     *
     * @return l'URL del database
     */
    public static String getDbUrl() {
        return dbUrl;
    }

    /**
     * Restituisce il nome del database.
     *
     * @return il nome del database
     */
    public static String getDbName() {
        return dbName;
    }

    /**
     * Restituisce il nome utente per la connessione al database.
     *
     * @return il nome utente per la connessione al database
     */
    public static String getUser() {
        return user;
    }

    /**
     * Restituisce la password per la connessione al database.
     *
     * @return la password per la connessione al database
     */
    public static String getPass() {
        return pass;
    }

    /**
     * Restituisce il nome utente dell'amministratore del sistema.
     *
     * @return il nome utente dell'amministratore del sistema
     */
    public static String getAdminUser() {
        return adminUser;
    }

    /**
     * Restituisce la password dell'amministratore del sistema.
     *
     * @return la password dell'amministratore del sistema
     */
    public static String getAdminPass() {
        return adminPass;
    }

    /**
     * Restituisce il nome della Servlet
     *
     * @return nome della servlet
     */
    public static String getNameServlet() {
        return nameServlet;
    }

    /**
     * Metodo chiamato quando il contesto dell'applicazione viene inizializzato.
     * Legge i parametri di connessione al database e di amministrazione dal file web.xml e li salva in variabili statiche.
     *
     * @param event l'evento che ha causato la chiamata del metodo
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();

        dbUrl = context.getInitParameter("DB_URL");
        dbName = context.getInitParameter("DB_NAME");
        user = context.getInitParameter("USER");
        pass = context.getInitParameter("PASS");
        adminUser = context.getInitParameter("ADMIN_USER");
        adminPass = context.getInitParameter("ADMIN_PASS");
        nameServlet = context.getInitParameter("NAME_SERVLET");
        realPath = context.getRealPath("") + "/WEB-INF/";


    }

    public static String getPath() {
        return  realPath;
    }
}
