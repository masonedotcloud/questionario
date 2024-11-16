package admin.user;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.Redirect;
import manager.Users;

/**
 * Classe servlet per gestire la richiesta POST di eliminazione di un account utente.
 * Riceve l'id dell'utente da eliminare tramite il parametro della richiesta "id".
 * Se la cancellazione va a buon fine, mostra un messaggio di successo sulla dashboard dell'amministratore.
 * Altrimenti, mostra un messaggio di errore sulla dashboard dell'amministratore.
 */
@WebServlet("/admin/user/delete")
public class Delete extends HttpServlet {

    /**
     * Metodo per gestire la richiesta POST alla servlet.
     * Riceve l'id dell'utente da eliminare tramite il parametro della richiesta "id".
     * Se la cancellazione va a buon fine, mostra un messaggio di successo sulla dashboard dell'amministratore.
     * Altrimenti, mostra un messaggio di errore sulla dashboard dell'amministratore.
     *
     * @param request  oggetto HttpServletRequest contenente la richiesta del client
     * @param response oggetto HttpServletResponse contenente la risposta del server
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        String msg;
        if (Users.DeleteUser(Integer.valueOf(request.getParameter("id")))) {

            msg = "<p class=\"text-center\">Account eliminato con successo tutti i questionari del profilo ora risulteranno anonimi, ritorna alla <a class=\"text-decoration-none\" href=\"/" + Redirect.web_app_name() + "/admin/dashboard\">dashboard</a></p>";
        } else {
            msg = "<p class=\"text-center\">Errore durante l'eliminazione del profilo, torna alla <a class=\"text-decoration-none\" href=\"/" + Redirect.web_app_name() + "/admin/dashboard\">dashboard</a></p>";

        }

        Redirect.GetPageWhitMessage(request, response, "/admin/page.jsp", msg);

    }

}
