package profile.questionnaire;

import functionality.DatabaseConnection;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.Redirect;

import java.io.Serial;

/**
 * Questa classe implementa la funzionalità di eliminazione di un questionario da parte dell'utente loggato.
 */
@WebServlet("/profile/questionnaire/delete")
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
     * Gestisce una richiesta POST per eliminare un questionario.
     *
     * @param request  La richiesta HTTP ricevuta
     * @param response La risposta HTTP da inviare
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        Redirect.OnlyLogged(request, response);

        String msg;

        try {
            DatabaseConnection db = new DatabaseConnection();
            String query = "DELETE FROM response WHERE ID = '" + request.getParameter("id") + "'";
            boolean success = db.manipulationQuery(query);
            // Se l'eliminazione è andata a buon fine, si reindirizza l'utente alla lista dei questionari
            if (success) {
                Redirect.ToPage(response, "profile/questionnaire/list");
            } else {
                // Se l'eliminazione non è andata a buon fine, si mostra un messaggio di errore
                msg = "<p class=\"text-center\">Errore durante l'eliminazione del questionario, torna alla <a class=\"text-decoration-none\" href=\"/" + Redirect.web_app_name() + "/\">home</a></p>";
                Redirect.GetPageWhitMessage(request, response, "/response.jsp", msg);
            }
        } catch (Exception e) {
            // In caso di errore durante l'eliminazione, si mostra un messaggio di errore
            msg = "<p class=\"text-center\">Errore durante l'eliminazione del questionario, torna alla <a class=\"text-decoration-none\" href=\"/" + Redirect.web_app_name() + "/\">home</a></p>";
            Redirect.GetPageWhitMessage(request, response, "/page.jsp", msg);
        }

    }

}
