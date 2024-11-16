package admin;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.Redirect;
import manager.Users;

import java.io.Serial;

/**
 * Classe servlet per gestire la richiesta GET alla dashboard dell'amministratore.
 * Redirige l'utente alla pagina della dashboard dell'amministratore.
 */
@WebServlet("/admin/dashboard")
public class Dashboard extends HttpServlet {
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
     * Verifica che l'utente sia un amministratore loggato, quindi mostra la dashboard dell'amministratore
     * contenente le informazioni sulle carte disponibili e sui prestiti effettuati.
     *
     * @param request  oggetto HttpServletRequest contenente la richiesta del client
     * @param response oggetto HttpServletResponse contenente la risposta del server
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        Redirect.OnlyLoggedAdmin(request, response);
        String msg = Users.CardsAdmin();
        Redirect.GetPageWhitMessage(request, response, "/admin/page.jsp", msg);

    }

}