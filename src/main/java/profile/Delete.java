package profile;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.Redirect;
import manager.Users;

import java.io.Serial;

/**
 * Questa classe gestisce le richieste relative all'eliminazione dell'account dell'utente.
 */
@WebServlet("/profile/delete")
public class Delete extends HttpServlet {
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
     * Il metodo effettua l'eliminazione del suo account attraverso l'ID nella sua sessione
     *
     * @param request  La richiesta HTTP ricevuta
     * @param response La risposta HTTP da inviare
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        Redirect.OnlyLogged(request, response);

        String msg;

        if (Users.DeleteUser(request.getSession(false))) {
            msg = "<p class=\"text-center\">Account eliminato con successo tutti i tuoi questionari ora risulteranno anonimi, vai alla <a class=\"text-decoration-none\" href=\"/" + Redirect.web_app_name() + "/\">home</a></p>";
        } else {
            msg = "<p class=\"text-center\">Errore durante l'eliminazione del profilo, torna al tuo <a class=\"text-decoration-none\" href=\"/" + Redirect.web_app_name() + "/profile/view\">profilo</a></p>";

        }

        Redirect.GetPageWhitMessage(request, response, "/page.jsp", msg);

    }

}
