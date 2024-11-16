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
 * Classe che gestisce la creazione di un nuovo questionario.
 */
@WebServlet("/admin/questionnaire/create")
public class Create extends HttpServlet {
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
     * Metodo che viene invocato quando l'utente richiede la pagina di creazione di un nuovo questionario.
     *
     * @param request  Oggetto HttpServletRequest che contiene la richiesta HTTP.
     * @param response Oggetto HttpServletResponse che contiene la risposta HTTP.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        Redirect.OnlyLoggedAdmin(request, response);

        Redirect.GetPage(request, response, "create.jsp");

    }

    /**
     * Metodo che viene invocato quando l'utente invia i dati per creare un nuovo questionario.
     *
     * @param request  Oggetto HttpServletRequest che contiene la richiesta HTTP con i dati del nuovo questionario.
     * @param response Oggetto HttpServletResponse che contiene la risposta HTTP.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        Redirect.OnlyLoggedAdmin(request, response);

        String msg = "";
        int result = Questionnaire.InsertQuestionnaire(request.getParameter("titolo"), request.getParameter("jsonOutput"));
        if (result == 1) {
            msg = "<p class=\"text-center\">Questionario aggiunto con successo, torna alla <a class=\"text-decoration-none\" href=\"/" + Redirect.web_app_name() + "/admin/dashboard\">dashboard</a></p>";
        } else if (result == 2) {
            msg = "<p class=\"text-center\">Questionario già esistente utilizza un altro nome, torna alla <a class=\"text-decoration-none\" href=\"/" + Redirect.web_app_name() + "/admin/dashboard\">dashboard</a></p>";
        } else {
            msg = "<p class=\"text-center\">Errore durante l'aggiunta del questionario, torna alla <a class=\"text-decoration-none\" href=\"/" + Redirect.web_app_name() + "/admin/dashboard\">dashboard</a></p>";
        }

        Redirect.GetPageWhitMessage(request, response, "/admin/page.jsp", msg);

    }

}