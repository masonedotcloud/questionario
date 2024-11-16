package admin.questionnaire;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.Questionnaire;
import manager.Redirect;

import java.io.Serial;

/**
 * Questa classe HttpServlet gestisce la visualizzazione anteprima del questionario per gli amministratori.
 */
@WebServlet("/admin/questionnaire/preview")
public class Preview extends HttpServlet {
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
     * Questo metodo gestisce la richiesta POST inviata dal client al server e utilizza il parametro "jsonOutput"
     * per generare l'anteprima del questionario. Inoltre, verifica che l'utente sia un amministratore caricato prima
     * di consentire l'accesso alla visualizzazione dell'anteprima del questionario.
     * Infine, il messaggio contenente l'anteprima del questionario viene restituito al client all'interno della
     * pagina amministrativa utilizzando il metodo GetPageWhitMessage della classe Redirect.
     *
     * @param request  l'oggetto HttpServletRequest che contiene la richiesta HTTP dal client
     * @param response l'oggetto HttpServletResponse che viene utilizzato per inviare la risposta HTTP al client
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        Redirect.OnlyLoggedAdmin(request, response);

        String msg = Questionnaire.ViewPreviewQuestionnaire("[" + request.getParameter("jsonOutput") + "]", true);

        Redirect.GetPageWhitMessage(request, response, "/admin/page.jsp", msg);

    }

}