package form.login;

import functionality.MD5;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.Redirect;
import manager.Users;

import java.io.IOException;
import java.io.Serial;

/**
 * Classe che gestisce la richiesta POST proveniente dalla pagina di login, controlla la validità dei dati.
 */
@WebServlet("/form/login/process")
public class Process extends HttpServlet {
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
        Redirect.GetPage(request, response, "/error.jsp");

    }

    /**
     * Gestisce la richiesta POST proveniente dalla pagina di login.
     * Controlla la validità dei dati nel database.
     *
     * @param request  la richiesta HTTP.
     * @param response la risposta HTTP.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Redirect.OnlyNotLogged(request, response);

        String msg;
        try {
            if (Users.Login(request.getParameter("nome"), request.getParameter("cognome"), MD5.hash(request.getParameter("password")), request, response)) {
                response.sendRedirect("/" + Redirect.web_app_name());
            } else {
                msg = "<p class=\"text-center\">Account non trovato <a href=\"/" + Redirect.web_app_name() + "/form/register/view\">registrati</a> o riprova l'<a href=\"/" + Redirect.web_app_name() + "/form/login/view\">accesso</a></p>";
                Redirect.GetPageWhitMessage(request, response, "/page.jsp", msg);
            }
        } catch (Exception e) {
            msg = "<p class=\"text-center\">Qualcosa è andato storto prova a ricontrollare i dati, ritorna all'<a href=\"/" + Redirect.web_app_name() + "/form/login/view\">accesso</a></p>";
            Redirect.GetPageWhitMessage(request, response, "/page.jsp", msg);
        }
    }
}


