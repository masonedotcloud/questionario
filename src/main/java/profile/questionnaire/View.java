package profile.questionnaire;


import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.Questionnaire;
import manager.Redirect;

import java.io.Serial;


/**
 * La classe View è una servlet che permette di visualizzare un questionario compilato dall'utente.
 */
@WebServlet("/profile/questionnaire/view")
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
     * Gestisce una richiesta POST per la visualizzazione di un questionario compilato dall'utente.
     *
     * @param request  La richiesta HTTP ricevuta
     * @param response La risposta HTTP da inviare
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        Redirect.OnlyLogged(request, response);

        String msg = Questionnaire.ViewSingleQuestionnaireUserCompiled(Integer.valueOf(request.getParameter("id")), Integer.valueOf(request.getSession(false).getAttribute("ID").toString()));

        Redirect.GetPageWhitMessage(request, response, "/page.jsp", msg);

    }

}
