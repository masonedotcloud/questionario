import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.Redirect;

import java.io.Serial;

/**
 * Questa classe rappresenta la pagina principale del sito web. Quando un utente accede alla radice del sito (ad esempio http://www.miosito.com/), questa servlet viene chiamata per gestire la richiesta.
 */
@WebServlet("/")
public class Index extends HttpServlet {
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
     * Gestisce le richieste HTTP GET per questa servlet. Questo metodo viene chiamato quando un utente accede alla radice del sito.
     *
     * @param request  la richiesta HTTP ricevuta dal client
     * @param response la risposta HTTP da inviare al client
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        // Reindirizza alla pagina di errore generico
        Redirect.ErrorPage(request, response);

    }

}
