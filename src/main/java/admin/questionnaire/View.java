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
 * Questa classe gestisce la visualizzazione di un singolo questionario da parte di un amministratore.
 * Viene richiamata tramite una richiesta POST al path "/admin/questionnaire/view".
 * Riceve come parametro l'ID del questionario da visualizzare e, utilizzando il metodo ViewSingleQuestionnaireEmpty della
 * classe Questionnaire, genera una stringa contenente il codice HTML per la visualizzazione del questionario vuoto.
 * Infine, la stringa viene passata alla pagina "/admin/page.jsp" tramite il metodo GetPageWhitMessage della classe Redirect.
 */
@WebServlet("/admin/questionnaire/view")
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
        Redirect.ErrorPage(request, response);
    }

    /**
     * Metodo richiamato alla ricezione di una richiesta POST.
     * Riceve come parametro l'ID del questionario da visualizzare.
     * Utilizza il metodo ViewSingleQuestionnaireEmpty della classe Questionnaire per generare il codice HTML
     *
     * @param request  la richiesta HTTP ricevuta dal client
     * @param response la risposta HTTP da inviare al client
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        String msg = Questionnaire.ViewSingleQuestionnaireEmpty(Integer.valueOf(request.getParameter("id")), true);

        Redirect.GetPageWhitMessage(request, response, "/admin/page.jsp", msg);

    }

}

