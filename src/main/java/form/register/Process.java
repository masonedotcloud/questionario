package form.register;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.Redirect;
import manager.Users;
import manager.WaitAction;

import java.io.Serial;

/**
 * Classe che gestisce la richiesta POST proveniente dalla pagina di registrazione, controlla la validità dei dati e registra l'utente nel database.
 */
@WebServlet("/form/register/process")
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
     * Gestisce la richiesta POST proveniente dalla pagina di registrazione.
     * Controlla la validità dei dati e registra l'utente nel database.
     *
     * @param request  la richiesta HTTP.
     * @param response la risposta HTTP.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        Redirect.OnlyNotLogged(request, response);

        String msg;

        if (WaitAction.check(request.getRemoteAddr())) {
            switch (Users.CheckUsernameAvailable(request.getParameter("nome"), request.getParameter("cognome"))) {
                case 0:
                    if (request.getParameter("password").equals(request.getParameter("password-check"))) {
                        if (Users.NewUser(request.getParameter("nome"), request.getParameter("cognome"), request.getParameter("password"))) {
                            msg = "<p class=\"text-center\">Registrazione effettuata con successo, <a href=\"/" + Redirect.web_app_name() + "/form/login/view\">accedi</a></p>";
                        } else {
                            msg = "<p class=\"text-center\">Errore durante il salvataggio dell'account, <a href=\"/" + Redirect.web_app_name() + "/form/register/view\">riprova</a></p>";
                        }
                    } else {
                        msg = "<p class=\"text-center\">Controlla le password, <a href=\"/" + Redirect.web_app_name() + "/form/register/view\">riprova</a></p>";
                    }
                    break;
                case 1:
                    msg = "<p class=\"text-center\">Utente già registrato, <a href=\"/" + Redirect.web_app_name() + "/form/login/view\">accedi</a></p>";
                    break;
                default:
                    msg = "<p class=\"text-center\">Errore durante la registrazione, <a href=\"/" + Redirect.web_app_name() + "/form/register/view\">riprova</a></p>";
                    break;
            }
        } else {
            msg = "<p class=\"text-center\">Devi attendere prima di creare un altro account, torna alla <a href=\"/" + Redirect.web_app_name() + "\">home</a></p>";
        }

        Redirect.GetPageWhitMessage(request, response, "/page.jsp", msg);

    }
}

