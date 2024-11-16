package manager;

import functionality.DatabaseConnection;
import functionality.MD5;
import functionality.WebXmlParams;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe per la gestione degli utenti
 */
public class Users {

    static final String ADMIN_USERNAME = "admin";
    static final String ADMIN_PASSWORD = "admin";

    /**
     * Controlla se un nome utente è disponibile verificando la sua presenza in una tabella di un database.
     *
     * @param name    il nome del potenziale utente.
     * @param surname il cognome del potenziale utente.
     * @return 1 se il nome utente è già presente, 0 se il nome utente non è presente, -1 in caso di errore.
     */
    public static int CheckUsernameAvailable(String name, String surname) {
        try {
            String query = "SELECT * FROM users WHERE name ='" + name + "' AND surname ='" + surname + "'";
            DatabaseConnection db = new DatabaseConnection();
            ResultSet rs = db.executeQuery(query);
            rs.last();

            if (rs.getRow() != 0) {
                return 1;
            } else {
                return 0;
            }

        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Aggiunge un nuovo utente al database con il nome, cognome e password forniti.
     *
     * @param name     il nome dell'utente da aggiungere
     * @param surname  il cognome dell'utente da aggiungere
     * @param password la password dell'utente da aggiungere
     * @return true se l'utente è stato aggiunto con successo, false altrimenti
     */
    public static boolean NewUser(String name, String surname, String password) {

        String md5;
        try {
            md5 = MD5.hash(password);
        } catch (NoSuchAlgorithmException e) {
            return false;
        }

        String encodedNAME;
        String encodedSURNAME;

        try {
            encodedNAME = name.replace("'", "\\'");
            encodedSURNAME = surname.replace("'", "\\'");
        } catch (Exception ignored) {
            return false;
        }


        String query = "INSERT INTO `users`(`name`, `surname`, `password`) VALUES ('" + encodedNAME + "','" + encodedSURNAME + "','" + md5 + "')";
        DatabaseConnection db = new DatabaseConnection();
        return db.manipulationQuery(query);
    }

    /**
     * Effettua il login dell'utente nel sistema verificando le credenziali inserite.
     *
     * @param name     il nome dell'utente da verificare
     * @param surname  il cognome dell'utente da verificare
     * @param password la password dell'utente da verificare
     * @param request  la richiesta HTTP in arrivo dal client
     * @param response la risposta HTTP da inviare al client
     * @return true se l'utente è stato autenticato correttamente, false altrimenti
     */
    public static boolean Login(String name, String surname, String password, HttpServletRequest request, HttpServletResponse response) {
        try {

            String encodedNAME;
            String encodedSURNAME;

            try {
                encodedNAME = name.replace("'", "\\'");
                encodedSURNAME = surname.replace("'", "\\'");
            } catch (Exception ignored) {
                return false;
            }

            String query = "SELECT users.*, COUNT(response.id_user) AS number_forms FROM users LEFT JOIN response ON users.ID = response.id_user WHERE users.name = '" + encodedNAME + "' AND users.surname = '" + encodedSURNAME + "' AND users.password = '" + password + "' GROUP BY users.ID";

            DatabaseConnection db = new DatabaseConnection();

            ResultSet rs = db.executeQuery(query);
            rs.last();

            if (rs.getRow() == 1) {

                try {
                    Date date = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedDate = dateFormat.format(date);

                    query = "UPDATE users SET last_access = '" + formattedDate + "', total_access = '" + (Integer.parseInt(rs.getString("total_access")) + 1) + "' WHERE ID = " + rs.getString("ID");

                    db.manipulationQuery(query);

                    HttpSession session = request.getSession(true);
                    session.setAttribute("ID", rs.getString("ID"));
                    session.setAttribute("nome", rs.getString("name"));
                    session.setAttribute("cognome", rs.getString("surname"));
                    session.setAttribute("accesso", rs.getString("last_access"));
                    session.setAttribute("accessi", (Integer.parseInt(rs.getString("total_access"))));
                    session.setAttribute("questionari", (Integer.parseInt(rs.getString("number_forms"))));

                    query = "DELETE FROM sessions_cookie WHERE id_user = '" + rs.getString("ID") + "'";

                    db.manipulationQuery(query);

                    query = "INSERT INTO `sessions_cookie`(`id_session`, `id_user`) VALUES ('" + session.getId() + "','" + rs.getString("ID") + "')";

                    db.manipulationQuery(query);

                    Cookie cookie = new Cookie("session_id", session.getId());
                    cookie.setPath("/" + Redirect.web_app_name());
                    cookie.setMaxAge(60 * 60 * 24 * 30); // Setta la durata del cookie a 30 giorni
                    response.addCookie(cookie);

                } catch (Exception ignored) {
                }

                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Effettua il login dell'amministratore nel sistema verificando le credenziali inserite.
     *
     * @param username lo username dell'amministratore da verificare
     * @param password la password dell'amministratore da verificare
     * @param request  la richiesta HTTP in arrivo dal client
     * @return true se l'amministratore è stato autenticato correttamente, false altrimenti
     */
    public static boolean LoginAdmin(String username, String password, HttpServletRequest request) {
        try {

            if (username.equals(WebXmlParams.getAdminUser()) && MD5.hash(password).equals(MD5.hash(WebXmlParams.getAdminPass()))) {
                HttpSession session = request.getSession(true);
                session.setAttribute("ADMIN", "true");
                return true;
            }
        } catch (Exception ignored) {
        }
        return false;
    }


    /**
     * Cancella l'utente corrente dal database.
     *
     * @param session la sessione corrente dell'utente da cancellare
     * @return true se l'utente è stato cancellato con successo, false altrimenti
     */
    public static boolean DeleteUser(HttpSession session) {
        try {
            Integer id_user = Integer.valueOf(session.getAttribute("ID").toString());
            String query = "DELETE FROM `users` WHERE ID = " + id_user;
            DatabaseConnection db = new DatabaseConnection();
            if (db.manipulationQuery(query)) {

                try {
                    query = "UPDATE `response` SET `id_user`='0' WHERE id_user = '" + id_user + "'";
                    db.manipulationQuery(query);
                } catch (Exception ignored) {

                }


                session.invalidate();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Cancella l'utente con l'ID specificato dal database.
     *
     * @param id l'ID dell'utente da cancellare
     * @return true se l'utente è stato cancellato con successo, false altrimenti
     */
    public static boolean DeleteUser(Integer id) {
        try {
            String query = "DELETE FROM `users` WHERE ID = " + id.toString();
            DatabaseConnection db = new DatabaseConnection();

            if (db.manipulationQuery(query)) {

                try {
                    query = "UPDATE `response` SET `id_user`='0' WHERE id_user = '" + id.toString() + "'";
                    db.manipulationQuery(query);
                } catch (Exception ignored) {

                }


                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Esegue il logout dell'utente corrente invalidando la sessione corrente e rimuovendo il cookie di sessione.
     *
     * @param request  la richiesta HTTP corrente
     * @param response la risposta HTTP corrente
     * @return true se il logout è stato eseguito con successo, false altrimenti
     */
    public static boolean Logout(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();

            // Rimuovi il cookie di sessione
            Cookie temp = new Cookie("session_id", "");
            temp.setPath("/" + Redirect.web_app_name());
            response.addCookie(temp);

            return true;
        } else {
            return false;
        }
    }


    /**
     * Esegue il logout dell'utente corrente invalidando la sessione corrente e rimuovendo il cookie di sessione.
     * <p>
     * Esegue il logout dell'amministratore invalidando la sessione corrente.
     *
     * @param session la sessione corrente
     * @return true se il logout è stato eseguito con successo, false altrimenti
     */
    public static boolean LogoutAdmin(HttpSession session) {

        if (session != null) {
            session.invalidate();

            return true;
        } else {
            return false;
        }
    }

    /**
     * Crea una stringa HTML rappresentante la card di un utente per la visualizzazione nell'area amministrativa.
     *
     * @param name    Il nome dell'utente da visualizzare nella card.
     * @param surname Il cognome dell'utente da visualizzare nella card.
     * @param id      L'ID dell'utente.
     * @return La stringa HTML rappresentante la card dell'utente.
     */
    public static String CardAdmin(String name, String surname, Integer id) {
        String html = HTML.getCardAdminUsers();
        html = html.replace("{var_title}", name + " " + surname);
        html = html.replace("{var_id}", id.toString());
        return html;
    }

    /**
     * Metodo che restituisce una stringa contenente HTML delle card degli utenti per la pagina amministrativa.
     * Inizia con un titolo "Utenti" seguito dalla card di default "Utenti Anonimi" e poi mostra le card degli utenti presenti nel database.
     *
     * @return Una stringa contenente HTML delle card degli utenti
     */
    public static String CardsAdmin() {

        StringBuilder result = new StringBuilder();

        String query = "SELECT * FROM users";

        DatabaseConnection db = new DatabaseConnection();

        ResultSet rs = db.executeQuery(query);

        try {
            result.append(CardAdmin("Utenti", "Anonimi", 0));
            while (rs.next()) {

                result.append(CardAdmin(rs.getString("name"), rs.getString("surname"), Integer.valueOf(rs.getString("ID"))));

            }

            String title_page = "<h1 class=\"mb-3 text-center\">Utenti</h1>";
            result.insert(0, title_page);

        } catch (Exception ignored) {
            return "<p class=\"text-center\">Errore durante il caricamento, ritorna alla <a href=\"/Servlet/admin/\">home</a></p>";
        }

        return result.toString();

    }

    /**
     * Verifica la sessione utente a partire dai cookie della richiesta e aggiorna la sessione in caso di validità.
     *
     * @param request  l'oggetto HttpServletRequest contenente la richiesta inviata dal client
     * @param response l'oggetto HttpServletResponse contenente la risposta che la servlet invia al client
     */
    public static void CheckProfileSession(HttpServletRequest request, HttpServletResponse response) {

        String session_id = null;

        // Estrae il cookie "session_id" dalla richiesta
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("session_id")) {
                    session_id = cookie.getValue();
                    break;
                }
            }
        }

        // Cerca una corrispondenza nella tabella "sessioni" del database
        DatabaseConnection db = new DatabaseConnection();

        String query = "SELECT * FROM sessions_cookie WHERE id_session = '" + session_id + "'";

        ResultSet rs = db.executeQuery(query);

        try {
            rs.last();

            // Estrae l'ID utente associato alla sessione e cerca le informazioni dell'utente nella tabella "utenti"
            int id_u = Integer.parseInt(rs.getString("id_user"));

            String query2 = "SELECT users.*, COUNT(response.id_user) AS number_forms FROM users LEFT JOIN response ON users.ID = response.id_user WHERE users.ID = '" + id_u + "' GROUP BY users.ID;";

            ResultSet rs2 = db.executeQuery(query2);
            rs2.last();

            // Aggiorna la sessione corrente con le informazioni dell'utente
            HttpSession session = request.getSession(true);
            session.setAttribute("ID", rs2.getString("ID"));
            session.setAttribute("nome", rs2.getString("name"));
            session.setAttribute("cognome", rs2.getString("surname"));
            session.setAttribute("accesso", rs2.getString("last_access"));
            session.setAttribute("accessi", (Integer.parseInt(rs2.getString("total_access"))));
            session.setAttribute("questionari", (Integer.parseInt(rs2.getString("number_forms"))));

            // Aggiorna la tabella "sessioni" con l'ID della nuova sessione
            query = "DELETE FROM sessions_cookie WHERE id_user = '" + rs2.getString("ID") + "'";

            db.manipulationQuery(query);

            query = "INSERT INTO `sessions_cookie`(`id_session`, `id_user`) VALUES ('" + session.getId() + "','" + rs2.getString("ID") + "')";

            db.manipulationQuery(query);

            // Imposta un cookie "session_id" sulla risposta della servlet per identificare la sessione corrente e ne imposta la durata
            Cookie cookie = new Cookie("session_id", session.getId());
            cookie.setPath("/" + Redirect.web_app_name());
            cookie.setMaxAge(60 * 60 * 24 * 30);
            response.addCookie(cookie);

        } catch (Exception ignored) {
        }


    }

}
