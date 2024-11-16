package admin.questionnaire;

import functionality.DatabaseConnection;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.Redirect;

import java.io.Serial;


/**
 * Questa classe HttpServlet gestisce la cancellazione di un questionario per gli amministratori.
 */
@WebServlet("/admin/questionnaire/delete")
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
     * Il metodo doPost viene chiamato quando l'utente conferma la cancellazione del questionario. Il metodo verifica che
     * l'utente sia un amministratore loggato prima di consentire la cancellazione del questionario. Successivamente,
     * utilizza la classe DatabaseConnection per eliminare il questionario dal database tramite una query SQL. Se l'eliminazione
     * ha successo, l'utente viene reindirizzato alla pagina dei questionari con il metodo ToPage della classe Redirect.
     * Altrimenti, viene mostrato un messaggio di errore all'interno della pagina amministrativa con il metodo GetPageWhitMessage
     * della classe Redirect.
     *
     * @param request  l'oggetto HttpServletRequest che contiene la richiesta HTTP dal client
     * @param response l'oggetto HttpServletResponse che viene utilizzato per inviare la risposta HTTP al client
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        Redirect.OnlyLoggedAdmin(request, response);

        String msg;

        try {
            DatabaseConnection db = new DatabaseConnection();
            String query = "DELETE FROM form WHERE ID = '" + request.getParameter("id") + "'";
            boolean success = db.manipulationQuery(query);
            if (success) {
                Redirect.ToPage(response, "admin/questionnaire/");
            } else {
                msg = "<p class=\"text-center\">Errore durante l'eliminazione del questionario, torna alla <a class=\"text-decoration-none\" href=\"/" + Redirect.web_app_name() + "/admin/dashboard\">dashboard</a></p>";
                Redirect.GetPageWhitMessage(request, response, "/admin/page.jsp", msg);
            }
        } catch (Exception e) {
            msg = "<p class=\"text-center\">Errore durante l'eliminazione del questionario, torna alla <a class=\"text-decoration-none\" href=\"/" + Redirect.web_app_name() + "/admin/dashboard\">dashboard</a></p>";
            Redirect.GetPageWhitMessage(request, response, "/admin/page.jsp", msg);
        }

    }

}
