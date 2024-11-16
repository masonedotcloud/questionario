package admin.user.questionnaire;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.Questionnaire;
import manager.Redirect;

import java.io.IOException;
import java.io.Serial;

/**
 * Servlet che gestisce l'elenco dei questionari utente da parte dell'amministratore.
 */
@WebServlet("/admin/user/questionnaire/list")
public class List extends HttpServlet {
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
     * Gestisce le richieste POST, mostrando l'elenco dei questionari utente all'amministratore.
     *
     * @param request  la richiesta HTTP ricevuta dal client
     * @param response la risposta HTTP da inviare al client
     * @throws IOException se si verifica un errore d'I/O durante la gestione della richiesta
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Redirect.OnlyLoggedAdmin(request, response);

        String msg = Questionnaire.ListQuestionnairesUserAdmin(Integer.valueOf(request.getParameter("id")));

        Redirect.GetPageWhitMessage(request, response, "/admin/page.jsp", msg);


    }
}
