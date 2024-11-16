package admin.login;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.Redirect;

import java.io.Serial;

/**
 * La classe View gestisce la visualizzazione della pagina di login per l'amministratore.
 */
@WebServlet("/admin/login/view")
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
     * Gestisce la richiesta GET per la visualizzazione della pagina di login per l'amministratore.
     *
     * @param request  la richiesta HTTP
     * @param response la risposta HTTP
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        Redirect.OnlyNotLoggedAdmin(request, response);

        Redirect.GetPage(request, response, "view.jsp");

    }

}
