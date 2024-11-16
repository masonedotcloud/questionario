package profile;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.Redirect;

import java.io.Serial;

/**
 * Questa classe rappresenta un servlet che si occupa di visualizzare il profilo dell'utente che ha effettuato l'accesso.
 */
@WebServlet("/profile/view")
public class View extends HttpServlet {
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
     * Metodo che risponde alle richieste GET per la visualizzazione del profilo dell'utente che ha effettuato l'accesso.
     *
     * @param request  oggetto HttpServletRequest che contiene le informazioni sulla richiesta HTTP.
     * @param response oggetto HttpServletResponse che contiene le informazioni sulla risposta HTTP.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        Redirect.OnlyLogged(request, response);

        Redirect.GetPage(request, response, "view.jsp");

    }

}
