package admin;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.Redirect;

import java.io.Serial;

/**
 * Classe servlet per gestire la richiesta GET all'indice della pagina web.
 * Redirige l'utente alla pagina index.jsp.
 */
@WebServlet("/admin")
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
     * Metodo per gestire la richiesta GET alla servlet.
     * Redirige l'utente alla pagina index.jsp.
     *
     * @param request  oggetto HttpServletRequest contenente la richiesta del client
     * @param response oggetto HttpServletResponse contenente la risposta del server
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        Redirect.GetPage(request, response, "index.jsp");

    }

}
