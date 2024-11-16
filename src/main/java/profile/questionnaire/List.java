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
 * Classe che rappresenta la servlet per visualizzare la lista dei questionari di un utente.
 */
@WebServlet("/profile/questionnaire/list")
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
     * Verifica che l'utente sia collegato e visualizza la lista dei questionari di quell'utente.
     *
     * @param request  la richiesta HTTP ricevuta dal client
     * @param response la risposta HTTP da inviare al client
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        Redirect.OnlyLogged(request, response);

        String msg = Questionnaire.ViewListForSingleUser(Integer.parseInt(request.getSession(false).getAttribute("ID").toString()));

        Redirect.GetPageWhitMessage(request, response, "/page.jsp", msg);

    }

}


