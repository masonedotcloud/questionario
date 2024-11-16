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
 * Questa classe HttpServlet gestisce la visualizzazione della lista dei questionari per gli amministratori.
 */
@WebServlet({"/admin/questionnaire/list", "/admin/questionnaire/"})
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
     * Questo metodo viene chiamato quando l'utente accede alla pagina dei questionari e si occupa della
     * visualizzazione della lista dei questionari per gli amministratori. Il metodo verifica che l'utente
     * sia un amministratore caricato prima di consentire l'accesso alla lista dei questionari. Successivamente,
     * utilizza il metodo ViewListAdmin della classe Questionnaire per ottenere la lista dei questionari e restituisce
     * la lista come messaggio al client all'interno della pagina amministrativa con il metodo GetPageWhitMessage
     * della classe Redirect.
     *
     * @param request  l'oggetto HttpServletRequest che contiene la richiesta HTTP dal client
     * @param response l'oggetto HttpServletResponse che viene utilizzato per inviare la risposta HTTP al client
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Redirect.OnlyLoggedAdmin(request, response);
        String msg = Questionnaire.ViewListAdmin();
        Redirect.GetPageWhitMessage(request, response, "/admin/page.jsp", msg);

    }

}
