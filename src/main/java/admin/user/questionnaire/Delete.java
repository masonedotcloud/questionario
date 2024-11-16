package admin.user.questionnaire;

import functionality.DatabaseConnection;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.Redirect;

import java.io.Serial;


/**
 * Servlet che gestisce l'eliminazione di un questionario utente da parte dell'amministratore.
 */
@WebServlet("/admin/user/questionnaire/delete")
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
     * Gestisce le richieste POST, eliminando il questionario utente specificato dall'amministratore.
     *
     * @param request  la richiesta HTTP ricevuta dal client
     * @param response la risposta HTTP da inviare al client
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        String msg;

        try {
            DatabaseConnection db = new DatabaseConnection();
            String query = "DELETE FROM response WHERE ID = '" + request.getParameter("id") + "'";
            boolean success = db.manipulationQuery(query);
            if (success) {
                Redirect.ToPage(response, "admin/dashboard");
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
