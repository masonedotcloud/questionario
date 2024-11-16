package manager;

import functionality.WebXmlParams;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * La seguente classe, denominata Redirect, contiene diversi metodi utili per il redirect e la visualizzazione di pagine web. La costante WEB_APP_NAME rappresenta il nome dell'applicazione web utilizzata nei percorsi.
 */
public class Redirect {

    /**
     * Metodo statico che reindirizza l'utente a una pagina interna del sito.
     *
     * @param response oggetto che gestisce la risposta del server all'utente.
     * @param path     percorso della pagina a cui l'utente viene reindirizzato.
     */
    static public void ToPage(HttpServletResponse response, String path) {
        try {

            response.sendRedirect("/" + Redirect.web_app_name() + "/" + path);

        } catch (Exception ignored) {
        }
    }

    /**
     * Questo metodo permette di visualizzare una pagina JSP con un messaggio di stato.
     *
     * @param request  l'oggetto HttpServletRequest associato alla richiesta corrente
     * @param response l'oggetto HttpServletResponse associato alla risposta corrente
     * @param path     la stringa contenente il percorso relativo della pagina JSP da visualizzare
     * @param message  la stringa contenente il messaggio di stato da visualizzare nella pagina JSP
     */
    static public void GetPageWhitMessage(HttpServletRequest request, HttpServletResponse response, String path, String message) {
        try {

            request.setAttribute("msg_stato", message);
            RequestDispatcher dispatcher = request.getRequestDispatcher(path);
            dispatcher.forward(request, response);

        } catch (Exception ignored) {
        }

    }

    /**
     * Inoltra la richiesta alla pagina specificata.
     *
     * @param request  l'oggetto HttpServletRequest che rappresenta la richiesta HTTP in corso.
     * @param response l'oggetto HttpServletResponse che rappresenta la risposta HTTP in corso.
     * @param path     il percorso della pagina a cui inoltrare la richiesta.
     */
    public static void GetPage(HttpServletRequest request, HttpServletResponse response, String path) {
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher(path);
            dispatcher.forward(request, response);

        } catch (Exception ignored) {
        }

    }

    /**
     * Verifica che l'utente non sia già autenticato e, in caso contrario, lo reindirizza alla home page.
     *
     * @param request  l'oggetto HttpServletRequest associato alla richiesta
     * @param response l'oggetto HttpServletResponse associato alla risposta
     */
    public static void OnlyNotLogged(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(false);
        try {

            if (session.getAttribute("ID") != null) {
                response.sendRedirect("/" + Redirect.web_app_name());

            }
        } catch (Exception ignored) {

            try {
                response.sendRedirect("/" + Redirect.web_app_name());

            } catch (Exception ignored1) {
            }

        }
    }

    /**
     * Questo metodo controlla se l'utente è loggato come amministratore e, in caso affermativo,
     * lo reindirizza alla pagina di amministrazione. Invece, se l'utente non è connesso come
     * amministratore, non fa nulla.
     *
     * @param request  la richiesta HTTP dal client
     * @param response la risposta HTTP da inviare al client
     */
    public static void OnlyNotLoggedAdmin(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(false);

        try {

            if (session.getAttribute("ADMIN") != null) {
                response.sendRedirect("/" + Redirect.web_app_name() + "/admin");

            }

        } catch (Exception ignored) {
            try {

                response.sendRedirect("/" + Redirect.web_app_name());

            } catch (Exception ignored1) {
            }
        }
    }

    /**
     * Questo metodo controlla se l'utente è connesso, se non è connesso lo reindirizza alla pagina di login.
     *
     * @param request  l'oggetto HttpServletRequest che contiene la richiesta HTTP
     * @param response l'oggetto HttpServletResponse che contiene la risposta HTTP
     */
    public static void OnlyLogged(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(false);
        try {
            if (session == null || session.getAttribute("ID") == null) {
                response.sendRedirect("/" + Redirect.web_app_name() + "/form/login/view");

            }

        } catch (Exception ignored) {
            try {
                response.sendRedirect("/" + Redirect.web_app_name());

            } catch (Exception ignored1) {
            }

        }
    }

    /**
     * Controlla se l'utente ha effettuato l'accesso come amministratore.
     * Se l'utente ha effettuato l'accesso, viene reindirizzato alla pagina di amministrazione.
     * In caso contrario, viene reindirizzato alla pagina di accesso dell'amministratore.
     *
     * @param request  l'oggetto HttpServletRequest associato alla richiesta del client
     * @param response l'oggetto HttpServletResponse associato alla risposta da inviare al client
     */
    public static void OnlyLoggedAdmin(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(false);
        try {

            if (session == null || session.getAttribute("ADMIN") == null) {
                response.sendRedirect("/" + Redirect.web_app_name() + "/admin/login/view");

            }

        } catch (Exception ignored) {
            try {
                response.sendRedirect("/" + Redirect.web_app_name() + "/admin");

            } catch (Exception ignored1) {
            }

        }
    }

    /**
     * Genera una pagina di errore con un messaggio specificato.
     *
     * @param request  L'oggetto HttpServletRequest che contiene le informazioni sulla richiesta HTTP.
     * @param response L'oggetto HttpServletResponse che contiene le informazioni sulla risposta HTTP.
     */
    public static void ErrorPage(HttpServletRequest request, HttpServletResponse response) {
        String msg = "<p class=\"text-center\">Pagina non trovata, ritorna da dove sei <a class=\"text-decoration-none\" href=\"/" + Redirect.web_app_name() + "\">venuto</a></p>";
        GetPageWhitMessage(request, response, "/page.jsp", msg);
    }

    public static String web_app_name() {
        return WebXmlParams.getNameServlet();
    }
}
