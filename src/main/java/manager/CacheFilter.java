package manager;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * La classe CacheFilter è un filtro che gestisce la cache delle richieste HTTP.
 * In particolare, se l'utente è autenticato, il filtro imposta i seguenti header nella risposta:
 * Cache-Control: no-cache, no-store, must-revalidate
 * Pragma: no-cache
 * Expires: 0
 */
@WebFilter("/*")
public class CacheFilter implements Filter {

    /**
     * Inizializza il filtro.
     *
     * @param config il FilterConfig che contiene i parametri di configurazione del filtro
     * @throws ServletException se si verifica un errore durante l'inizializzazione
     */
    public void init(FilterConfig config) throws ServletException {
    }

    /**
     * Gestisce la cache delle richieste HTTP.
     *
     * @param req   la ServletRequest rappresentante la richiesta HTTP
     * @param res   la ServletResponse rappresentante la risposta HTTP
     * @param chain il FilterChain che rappresenta la successiva servlet/filter nella catena
     * @throws IOException      se si verifica un errore d'I/O durante l'esecuzione del filtro
     * @throws ServletException se si verifica un errore durante l'esecuzione del filtro
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {


        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        Users.CheckProfileSession(request, response);

        boolean isAuthenticated = request.getSession().getAttribute("ID") != null;

        if (isAuthenticated) {
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");
        }

        chain.doFilter(request, response);
    }

}
