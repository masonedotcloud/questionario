package admin.login;

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
 * Processa la richiesta di login admin, verificando la correttezza delle credenziali inserite.
 * Se i dati inseriti sono corretti, l'utente viene reindirizzato alla dashboard dell'amministratore.
 * In caso contrario viene visualizzato un messaggio di errore e l'utente viene riportato alla pagina di login.
 */
@WebServlet("/admin/login/process")
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
        Redirect.ErrorPage(request, response);
    }

    /**
     * Verifica la correttezza delle credenziali inserite dall'utente e, se corrette, reindirizza l'utente alla dashboard dell'admin.
     * In caso contrario, visualizza un messaggio di errore e riporta l'utente alla pagina di login.
     *
     * @param request  La richiesta HTTP del client contenente i dati di login.
     * @param response La risposta HTTP del server.
     * @throws IOException Se si verifica un errore di I/O durante la comunicazione con il server.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            if (Users.LoginAdmin(request.getParameter("username"), request.getParameter("password"), request)) {
                response.sendRedirect("/" + Redirect.web_app_name() + "/admin/dashboard");
            } else {
                String msg = "<p class=\"text-center\">Qualcosa è andato storto prova a ricontrollare i dati, ritorna all'<a class=\"text-decoration-none\" href=\"/" + Redirect.web_app_name() + "/admin/login/view\">accesso</a></p>";
                Redirect.GetPageWhitMessage(request, response, "/admin/page.jsp", msg);
            }

        } catch (Exception e) {
            String msg = "<p class=\"text-center\">Qualcosa è andato storto prova a ricontrollare i dati, ritorna all'<a class=\"text-decoration-none\" href=\"/" + Redirect.web_app_name() + "/admin/login/view\">accesso</a></p>";
            Redirect.GetPageWhitMessage(request, response, "/admin/page.jsp", msg);
        }

    }
}


