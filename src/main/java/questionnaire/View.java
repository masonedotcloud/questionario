package questionnaire;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.Questionnaire;
import manager.Redirect;

import java.io.Serial;

/**
 * Questa servlet viene utilizzata per visualizzare il contenuto di un questionario.
 */
@WebServlet("/questionnaire/view")
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
     * Gestisce le richieste GET, reindirizzando alla pagina di errore
     *
     * @param request  la richiesta HTTP ricevuta dal client
     * @param response la risposta HTTP da inviare al client
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        // Reindirizza alla pagina di errore generico
        Redirect.ErrorPage(request, response);

    }

    /**
     * Gestisce le richieste HTTP POST per questa servlet. Questo metodo viene chiamato quando un utente invia il form di visualizzazione del questionario.
     *
     * @param request  la richiesta HTTP ricevuta dal client
     * @param response la risposta HTTP da inviare al client
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        // Ottiene l'ID del questionario dalla richiesta
        int questionnaireId = Integer.parseInt(request.getParameter("id"));

        // Ottiene il contenuto del questionario dal database
        String msg = Questionnaire.ViewSingleQuestionnaireEmpty(questionnaireId, false);

        // Reindirizza alla pagina di visualizzazione del questionario con un messaggio
        Redirect.GetPageWhitMessage(request, response, "/page.jsp", msg);

    }

}

