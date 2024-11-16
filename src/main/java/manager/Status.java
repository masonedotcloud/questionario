package manager;

import functionality.DatabaseConnection;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.Serial;

/**
 * La classe Status rappresenta una servlet per il controllo dello stato di connessione al database.
 */
@WebServlet("/status")
public class Status extends HttpServlet {

    /**
     * Numero di versione seriale per la compatibilità della serializzazione.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Inizializza questa servlet. Questo metodo viene chiamato quando la servlet viene creata dal container Servlet.
     *
     * @param config la configurazione della servlet
     */
    public void init(ServletConfig config) {
    }

    /**
     * Gestisce le richieste GET, reindirizzando alla pagina di errore
     *
     * @param request  la richiesta HTTP ricevuta dal client
     * @param response la risposta HTTP da inviare al client
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        Redirect.ErrorPage(request, response);

    }

    /**
     * Gestisce le richieste POST alla servlet, verificando lo stato di connessione al database e restituendo il risultato in formato testuale.
     *
     * @param request  la richiesta HTTP ricevuta dal client
     * @param response la risposta HTTP da inviare al client
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        boolean isConnected = DatabaseConnection.checkConnection();
        String result = isConnected ? "true" : "false";
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(result);
        } catch (Exception ignored) {

        }

    }

}
