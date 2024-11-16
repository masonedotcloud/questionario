package manager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import functionality.DatabaseConnection;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.sql.ResultSet;
import java.util.List;

/**
 * Classe per la gestione dei questionari
 */
public class Questionnaire {

    /**
     * Questo metodo restituisce una stringa HTML che rappresenta una lista di questionari completati da un singolo utente.
     * La stringa HTML include un'intestazione e una serie di card HTML che rappresentano i questionari completati.
     *
     * @param id l'ID dell'utente di cui si vogliono visualizzare i questionari completati
     * @return una stringa HTML che rappresenta una lista di questionari completati dall'utente specificato
     */
    public static String ViewListForSingleUser(Integer id) {
        String title_page = "<h1 class=\"mb-3 text-center\">Questionari</h1>";
        StringBuilder htmlBuilder = new StringBuilder();

        try {
            DatabaseConnection db = new DatabaseConnection();

            String query = "SELECT response.date AS date, response.ID AS ID, form.title AS title, response.json AS json FROM response response LEFT JOIN form form ON form.ID = response.id_form WHERE response.id_user = '" + id + "'";
            ResultSet rs = db.executeQuery(query);

            while (rs.next()) {

                htmlBuilder.append(CardSingleUser(rs.getString("date"), rs.getString("title"), Integer.valueOf(rs.getString("ID"))));

            }

            htmlBuilder.insert(0, title_page);

        } catch (Exception e) {
            htmlBuilder.append("<p>Errore durante il caricamento, ritorna alla <a href=\"/" + Redirect.web_app_name() + "\">home</a></p>");
        }
        return htmlBuilder.toString();
    }

    /**
     * Questo metodo restituisce una stringa HTML che rappresenta una lista di tutti i questionari disponibili.
     * La stringa HTML include un'intestazione e una serie di card HTML che rappresentano i questionari disponibili.
     *
     * @return una stringa HTML che rappresenta una lista di tutti i questionari disponibili
     */
    public static String ViewListForUser() {
        StringBuilder htmlBuilder = new StringBuilder();

        try {
            DatabaseConnection db = new DatabaseConnection();

            String query = "SELECT * FROM form";
            ResultSet rs = db.executeQuery(query);

            while (rs.next()) {

                htmlBuilder.append(CardUser(rs.getString("title"), Integer.valueOf(rs.getString("ID"))));

            }
            String title_page = "<h1 class=\"mb-3 text-center\">Questionari</h1>";
            htmlBuilder.insert(0, title_page);

        } catch (Exception e) {
            htmlBuilder.append("<p class=\"text-center\">Errore durante il caricamento, ritorna alla <a href=\"/" + Redirect.web_app_name() + "\">home</a></p>");
        }
        return htmlBuilder.toString();
    }

    /**
     * Questo metodo restituisce una stringa HTML che rappresenta una card per un questionario disponibile.
     *
     * @param title il titolo del questionario
     * @param id    l'ID del questionario
     * @return una stringa HTML che rappresenta una card per il questionario specificato
     */
    static private String CardUser(String title, Integer id) {
        String html = HTML.getCardUserQuestionnaire();
        html = html.replace("{var_title}", title);
        html = html.replace("{var_id}", id.toString());
        return html;
    }

    /**
     * Questo metodo restituisce una stringa HTML che rappresenta una card per un questionario visualizzato da un singolo utente.
     *
     * @param date  la data in cui il questionario è stato compilato
     * @param title il titolo del questionario
     * @param id    l'ID del questionario
     * @return una stringa HTML che rappresenta una card per il questionario specificato
     */
    static private String CardSingleUser(String date, String title, Integer id) {
        String html = HTML.getCardSingleUserQuestionnaire();
        html = html.replace("{var_title}", date + ": " + title);
        html = html.replace("{var_id}", id.toString());
        return html;
    }


    /**
     * Questo metodo restituisce una stringa HTML che rappresenta l'anteprima di un questionario.
     *
     * @param jsonString la stringa JSON che rappresenta il questionario
     * @param readonly   un flag che indica se il questionario deve essere visualizzato come readonly
     * @return una stringa HTML che rappresenta l'anteprima del questionario specificato
     */
    public static String ViewPreviewQuestionnaire(String jsonString, boolean readonly) {

        return HTMLQuestionnaireEmpty(jsonString, 0, readonly, "");

    }


    /**
     * Questo metodo restituisce la rappresentazione HTML di un singolo questionario vuoto,
     * identificato dall'ID specificato, da visualizzare nella pagina di anteprima.
     *
     * @param id       l'ID del questionario da visualizzare
     * @param readonly true se il questionario deve essere visualizzato in sola lettura, false altrimenti
     * @return la rappresentazione HTML del questionario vuoto
     */
    public static String ViewSingleQuestionnaireEmpty(Integer id, boolean readonly) {

        String title_page;
        String jsonString;

        try {
            DatabaseConnection db = new DatabaseConnection();
            String query = "SELECT * FROM form WHERE ID ='" + id + "'";

            ResultSet rs = db.executeQuery(query);
            rs.last();

            jsonString = rs.getString("json");
            title_page = "<h1 class=\"mb-3 text-center\">" + rs.getString("title") + "</h1>";

        } catch (Exception e) {
            return "<p class=\"text-center\">Errore durante il caricamento, ritorna alla <a href=\"/" + Redirect.web_app_name() + "\">home</a></p>";
        }

        return HTMLQuestionnaireEmpty(jsonString, id, readonly, title_page);
    }


    /**
     * Metodo che restituisce la rappresentazione HTML di un questionario compilato da un utente.
     *
     * @param id      l'ID della risposta del questionario compilato
     * @param id_user l'ID dell'utente che ha compilato il questionario
     * @return una stringa rappresentante la pagina HTML del questionario compilato
     */
    public static String ViewSingleQuestionnaireUserCompiled(Integer id, Integer id_user) {
        String title_page;
        String jsonString;

        try {
            DatabaseConnection db = new DatabaseConnection();
            String query = "SELECT response.date AS date, response.ID AS ID, form.title AS title, response.json AS json FROM response response LEFT JOIN form form ON form.ID = response.id_form WHERE response.ID = '" + id + "' AND response.id_user = '" + id_user + "'";
            ResultSet rs = db.executeQuery(query);
            rs.last();
            jsonString = rs.getString("json");
            title_page = "<h1 class=\"mb-0 text-center\">" + rs.getString("title") + "</h1><h6 class=\"mb-3 text-center\">" + rs.getString("date") + "</h6>";
        } catch (Exception e) {
            return "<p class=\"text-center\">Errore durante il caricamento, ritorna alla <a href=\"/" + Redirect.web_app_name() + "\">home</a></p>";
        }

        return HTMLQuestionnaireCompiled(jsonString, title_page);
    }

    /**
     * Questo metodo restituisce la rappresentazione HTML di un singolo questionario compilato da un utente per gli amministratori.
     *
     * @param id l'ID del questionario compilato da visualizzare
     * @return la rappresentazione HTML del questionario compilato
     */
    public static String ViewSingleQuestionnaireUserCompiledAdmin(Integer id) {
        String title_page;
        String jsonString;
        int id_user = id;

        try {
            DatabaseConnection db = new DatabaseConnection();
            String query = "SELECT response.id_user AS id_user, response.date AS date, response.ID AS ID, form.title AS title, response.json AS json FROM response response LEFT JOIN form form ON form.ID = response.id_form WHERE response.ID = '" + id + "'";
            ResultSet rs = db.executeQuery(query);
            rs.last();
            jsonString = rs.getString("json");
            id_user = Integer.parseInt(rs.getString("id_user"));

            String nome;
            String cognome;

            if (id_user != 0) {
                String query2 = "SELECT * FROM users WHERE ID = '" + id_user + "'";
                ResultSet rs2 = db.executeQuery(query2);
                rs2.last();
                nome = rs2.getString("name");
                cognome = rs2.getString("surname");
            } else {
                nome = "Utenti";
                cognome = "Anonimi";
            }

            title_page = "<h1 class=\"mb-0 text-center\">" + nome + " " + cognome + "</h1><h6 class=\"mb-3 text-center\">Questionario " + rs.getString("date") + ": " + rs.getString("title") + "</h6>";

        } catch (Exception e) {
            return "<p class=\"text-center\">Errore durante il caricamento, ritorna alla <a href=\"/" + Redirect.web_app_name() + "\">home</a></p>";
        }

        return HTMLQuestionnaireCompiled(jsonString, title_page);
    }


    /**
     * Genera un questionario in formato HTML a partire da una stringa JSON contenente le informazioni sulle domande e le risposte.
     *
     * @param jsonString una stringa JSON contenente le informazioni sulle domande e le risposte possibili
     * @param title_page il titolo della pagina HTML
     * @return una stringa contenente il codice HTML per visualizzare il questionario
     */
    private static String HTMLQuestionnaireCompiled(String jsonString, String title_page) {
        StringBuilder htmlBuilder = new StringBuilder();
        try {
            Reader jsonReader = new BufferedReader(new StringReader(jsonString));
            java.util.List<DomandaCompiled> domande = new Gson().fromJson(jsonReader, new TypeToken<java.util.List<DomandaCompiled>>() {
            }.getType());

            htmlBuilder.append("<form readonly>");

            for (int i = 0; i < domande.size(); i++) {
                DomandaCompiled domanda = domande.get(i);
                htmlBuilder.append("<div class=\"mb-3\">");
                htmlBuilder.append("<label class=\"form-label\" for=\"domanda").append(i).append("\">");
                if (domanda.isRequired()) {
                    htmlBuilder.append("<b>");
                }
                htmlBuilder.append(domanda.getTesto());
                if (domanda.isRequired()) {
                    htmlBuilder.append("</b>");
                }
                htmlBuilder.append("</label>");
                switch (domanda.getTipo()) {
                    case "text":
                        htmlBuilder.append("<input class=\"form-control\" type=\"text\" value=\"").append(domanda.risposteUtente.get(0)).append("\" id=\"domanda").append(i).append("\" name=\"domanda").append(i).append("\"");
                        if (domanda.isRequired()) {
                            htmlBuilder.append(" required");
                        }
                        htmlBuilder.append(">");
                        break;
                    case "select":
                        htmlBuilder.append("<select class=\"form-select\" id=\"domanda\"").append(i).append("\" name=\"domanda").append(i).append("\">");
                        if (domanda.isRequired()) {
                            htmlBuilder.append(" required");
                        }
                        htmlBuilder.append(">");
                        for (String risposta : domanda.getRisposte()) {
                            boolean find = false;
                            for (String ru : domanda.risposteUtente) {
                                if (ru.equals(risposta)) {
                                    htmlBuilder.append("<option selected value=\"").append(risposta).append("\">").append(risposta).append("</option>");
                                    find = true;
                                    break;
                                }
                            }
                            if (!find) {
                                htmlBuilder.append("<option value=\"").append(risposta).append("\">").append(risposta).append("</option>");
                            }
                        }
                        htmlBuilder.append("</select>");
                        break;
                    case "radio":
                        for (String risposta : domanda.getRisposte()) {
                            htmlBuilder.append("<div class=\"form-check\">");
                            htmlBuilder.append("<input class=\"form-check-input\" id=\"radio").append(i).append("\" value=\"").append(risposta).append("\" type=\"radio\" name=\"domanda").append(i).append("\"");
                            if (domanda.isRequired()) {
                                htmlBuilder.append(" required");
                            }
                            for (String ru : domanda.risposteUtente) {
                                if (ru.equals(risposta)) {
                                    htmlBuilder.append(" checked");
                                    break;
                                }
                            }
                            htmlBuilder.append(">");
                            htmlBuilder.append("<label class=\"form-check-label\" for=radio\"").append(i).append("\">").append(risposta).append("</label>");
                            htmlBuilder.append("</div>");

                        }
                        break;
                    case "checkbox":

                        for (String risposta : domanda.getRisposte()) {
                            htmlBuilder.append("<div class=\"form-check\">");
                            htmlBuilder.append("<input class=\"form-check-input\" id=\"checkbox").append(i).append("\" value=\"").append(risposta).append("\" type=\"checkbox\" name=\"domanda").append(i).append("\"");
                            if (domanda.isRequired()) {
                                htmlBuilder.append(" required");
                            }
                            for (String ru : domanda.risposteUtente) {
                                if (ru.equals(risposta)) {
                                    htmlBuilder.append(" checked");
                                    break;
                                }
                            }
                            htmlBuilder.append(">");
                            htmlBuilder.append("<label class=\"form-check-label\" for=checkbox\"").append(i).append("\">").append(risposta).append("</label>");
                            htmlBuilder.append("</div>");

                        }
                        break;
                    case "textarea":
                        htmlBuilder.append("<textarea class=\"form-control\" name=\"domanda").append(i).append("\"").append("id=\"textarea\" rows=\"4\" ");
                        if (domanda.isRequired()) {
                            htmlBuilder.append(" required");
                        }
                        htmlBuilder.append(">").append(domanda.risposteUtente.get(0)).append("</textarea>");
                        break;
                }
            }
            htmlBuilder.append("</form>");
            htmlBuilder.insert(0, title_page);
        } catch (Exception e) {
            return "<p class=\"text-center\">Errore durante il caricamento, ritorna alla <a href=\"/" + Redirect.web_app_name() + "\">home</a></p>";
        }
        return htmlBuilder.toString();
    }


    /**
     * Metodo che genera una stringa HTML che rappresenta un questionario vuoto, a partire da una stringa JSON.
     *
     * @param jsonString la stringa JSON che rappresenta il questionario vuoto
     * @param id         l'identificatore del questionario
     * @param readonly   flag che indica se il questionario deve essere visualizzato in sola lettura
     * @param title_page il titolo della pagina HTML generata
     * @return una stringa HTML che rappresenta il questionario vuoto
     */
    private static String HTMLQuestionnaireEmpty(String jsonString, Integer id, boolean readonly, String title_page) {
        StringBuilder htmlBuilder = new StringBuilder();
        try {
            Reader jsonReader = new BufferedReader(new StringReader(jsonString));
            List<DomandaEmpty> domande = new Gson().fromJson(jsonReader, new TypeToken<List<DomandaEmpty>>() {
            }.getType());

            if (readonly) {
                htmlBuilder.append("<form readonly>");
            } else {
                htmlBuilder.append("<form action=\"process\" method=\"POST\">");
            }

            htmlBuilder.append("<input type=\"hidden\" name=\"id\" value=\"").append(id).append("\">");

            for (int i = 0; i < domande.size(); i++) {
                DomandaEmpty domanda = domande.get(i);
                htmlBuilder.append("<div class=\"mb-3\">");
                htmlBuilder.append("<label class=\"form-label\" for=\"domanda").append(i).append("\">");
                if (domanda.isRequired()) {
                    htmlBuilder.append("<b>");
                }
                htmlBuilder.append(domanda.getTesto());
                if (domanda.isRequired()) {
                    htmlBuilder.append("</b>");
                }
                htmlBuilder.append("</label>");
                switch (domanda.getTipo()) {
                    case "text":
                        htmlBuilder.append("<input class=\"form-control\" type=\"text\" id=\"domanda").append(i).append("\" name=\"domanda").append(i).append("\"");
                        if (domanda.isRequired()) {
                            htmlBuilder.append(" required");
                        }
                        htmlBuilder.append(">");
                        break;
                    case "select":
                        htmlBuilder.append("<select class=\"form-select\" id=\"domanda\"").append(i).append("\" name=\"domanda").append(i).append("\">");
                        if (domanda.isRequired()) {
                            htmlBuilder.append(" required");
                        }
                        htmlBuilder.append(">");
                        for (String risposta : domanda.getRisposte()) {
                            htmlBuilder.append("<option value=\"").append(risposta).append("\">").append(risposta).append("</option>");
                        }
                        htmlBuilder.append("</select>");
                        break;
                    case "radio":
                        for (String risposta : domanda.getRisposte()) {
                            htmlBuilder.append("<div class=\"form-check\">");
                            htmlBuilder.append("<input class=\"form-check-input\" id=\"radio").append(i).append("\" value=\"").append(risposta).append("\" type=\"radio\" name=\"domanda").append(i).append("\"");
                            if (domanda.isRequired()) {
                                htmlBuilder.append(" required");
                            }
                            htmlBuilder.append(">");
                            htmlBuilder.append("<label class=\"form-check-label\" for=radio\"").append(i).append("\">").append(risposta).append("</label>");
                            htmlBuilder.append("</div>");

                        }

                        break;
                    case "checkbox":

                        for (String risposta : domanda.getRisposte()) {
                            htmlBuilder.append("<div class=\"form-check\">");
                            htmlBuilder.append("<input class=\"form-check-input\" id=\"checkbox").append(i).append("\" value=\"").append(risposta).append("\" type=\"checkbox\" name=\"domanda").append(i).append("\"");
                            if (domanda.isRequired()) {
                                htmlBuilder.append(" required");
                            }
                            htmlBuilder.append(">");
                            htmlBuilder.append("<label class=\"form-check-label\" for=checkbox\"").append(i).append("\">").append(risposta).append("</label>");
                            htmlBuilder.append("</div>");

                        }
                        break;
                    case "textarea":
                        htmlBuilder.append("<textarea class=\"form-control\" name=\"domanda").append(i).append("\"").append("id=\"textarea\" rows=\"4\" ");
                        if (domanda.isRequired()) {
                            htmlBuilder.append(" required");
                        }
                        htmlBuilder.append("></textarea>");
                        break;
                }
                htmlBuilder.append("</div>");
            }

            if (!readonly) {
                htmlBuilder.append("<button type=\"submit\" class=\"btn btn-primary\">Invia</button>");
            }

            htmlBuilder.append("</form>");
            htmlBuilder.insert(0, title_page);
        } catch (Exception e) {
            return "<p class=\"text-center\">Errore durante il caricamento, ritorna alla <a href=\"/" + Redirect.web_app_name() + "\">home</a></p>";
        }

        return htmlBuilder.toString();
    }

    /**
     * Metodo che restituisce una stringa contenente una lista di form, rappresentati attraverso delle card, da visualizzare nella pagina di amministrazione.
     *
     * @return la stringa contenente la lista di form, rappresentati attraverso delle card
     */
    static public String ViewListAdmin() {
        StringBuilder result = new StringBuilder();

        String query = "SELECT * FROM form";

        DatabaseConnection db = new DatabaseConnection();

        ResultSet rs = db.executeQuery(query);

        try {
            while (rs.next()) {

                result.append(CardAdmin(rs.getString("title"), Integer.valueOf(rs.getString("ID"))));

            }

        } catch (Exception ignored) {
            return "<p class=\"text-center\">Errore durante il caricamento, ritorna alla <a href=\"/" + Redirect.web_app_name() + "/admin/\">home</a></p>";
        }

        return result.toString();
    }

    /**
     * La seguente funzione CardAdmin costruisce e restituisce una stringa HTML rappresentante una card contenente il titolo e l'ID di un form e due bottoni per visualizzare e cancellare il form.
     *
     * @param title il titolo del form
     * @param id    l'ID del form
     * @return una stringa HTML rappresentante la card del form
     */
    static private String CardAdmin(String title, Integer id) {
        String html = HTML.getCardAdminQuestionnaire();
        html = html.replace("{var_title}", title);
        html = html.replace("{var_id}", id.toString());
        return html;
    }

    /**
     * Metodo che restituisce una lista di questionari compilati da un utente amministratore.
     *
     * @param id_to_find ID dell'utente di cui si vogliono trovare i questionari compilati.
     * @return Stringa contenente l'elenco dei questionari compilati dall'utente, formattati come card.
     */
    public static String ListQuestionnairesUserAdmin(Integer id_to_find) {
        StringBuilder result = new StringBuilder();

        Integer id_user = id_to_find;

        String query = "SELECT response.id_user AS id_user, response.date AS date, response.ID AS ID, form.title AS title, response.json AS json FROM response response LEFT JOIN form form ON form.ID = response.id_form WHERE response.id_user = '" + id_to_find + "'";

        DatabaseConnection db = new DatabaseConnection();

        ResultSet rs = db.executeQuery(query);

        try {
            while (rs.next()) {

                result.append(QuestionnaireCardAdmin(rs.getString("title"), rs.getString("date"), Integer.valueOf(rs.getString("ID"))));

            }

            rs.last();
            if (rs.getRow() == 0) {
                result.append("<p class=\"text-center\">Nessun questionario compilato</p>");
            }

            String nome;
            String cognome;

            if (id_user != null && id_user != 0) {
                String query2 = "SELECT * FROM users WHERE ID = '" + id_user + "'";
                ResultSet rs2 = db.executeQuery(query2);
                rs2.last();
                nome = rs2.getString("name");
                cognome = rs2.getString("surname");
            } else {
                nome = "Utenti";
                cognome = "Anonimi";
            }

            String title_page = "<h1 class=\"mb-0 text-center\">" + nome + " " + cognome + "</h1><h6 class=\"mb-3 text-center\">Questionari</h6>";
            result.insert(0, title_page);

        } catch (Exception ignored) {

            return "<p class=\"text-center\">Errore durante il caricamento, ritorna alla <a href=\"/" + Redirect.web_app_name() + "/admin/dashboard\">dashboard</a></p>";

        }

        return result.toString();
    }

    /**
     * Questo metodo genera HTML per visualizzare una singola card di un questionario nella pagina admin.
     * La card include il titolo e la data del questionario, oltre ai pulsanti per visualizzarlo e cancellarlo.
     *
     * @param title il titolo del questionario
     * @param date  la data in cui il questionario è stato compilato
     * @param id    l'ID del questionario
     * @return una stringa contenente HTML della card del questionario
     */
    public static String QuestionnaireCardAdmin(String title, String date, Integer id) {
        String html = HTML.getQuestionnaireCardAdminQuestionnaire();
        html = html.replace("{var_title}", date + ": " + title);
        html = html.replace("{var_id}", id.toString());
        return html;
    }

    /**
     * Inserisce un nuovo questionario nel database.
     *
     * @param title      il titolo del questionario da inserire
     * @param jsonString la rappresentazione JSON del questionario da inserire
     * @return un intero che indica il successo dell'operazione: - 1 se il questionario è stato inserito correttamente, - 2 se un questionario con lo stesso titolo è già presente nel database, - 0 se si è verificato un errore durante l'operazione
     */
    public static Integer InsertQuestionnaire(String title, String jsonString) {
        DatabaseConnection db = new DatabaseConnection();

        String encodedJSON;
        String encodedTITLE;
        try {
            encodedTITLE = title.replace("'", "\\'");
            encodedJSON = jsonString.replace("'", "\\'");
        } catch (Exception ignored) {
            return 0;
        }

        String query = "SELECT * FROM `form` WHERE title = '" + encodedTITLE + "'";

        ResultSet rs = db.executeQuery(query);

        try {
            rs.last();

            if (rs.getRow() != 0) {
                return 2;
            }
        } catch (Exception e) {
            return 0;
        }

        query = "INSERT INTO `form`(`json`, `title`) VALUES ('" + "[" + encodedJSON + "]" + "','" + encodedTITLE + "')";

        if (db.manipulationQuery(query)) {
            return 1;
        } else {
            return 0;
        }

    }

    /**
     * Rappresenta una domanda vuota, senza risposte, utilizzata per creare
     * domande di diversi tipi. Contiene il tipo di domanda, il testo della domanda,
     * una lista di risposte (vuota) e un flag che indica se la domanda è obbligatoria.
     */
    public static class DomandaEmpty {
        private String tipo;
        private String testo;
        private List<String> risposte;
        private boolean required;

        public String getTipo() {
            return tipo;
        }

        public String getTesto() {
            return testo;
        }

        public List<String> getRisposte() {
            return risposte;
        }

        public boolean isRequired() {
            return required;
        }
    }

    /**
     * Rappresenta una domanda compilata, contenente il tipo di domanda, il testo della domanda,
     * una lista di risposte possibili, una flag indicante se la domanda è obbligatoria, e una lista
     * contenente le risposte date dall'utente.
     */
    public class DomandaCompiled {
        private String tipo;
        private String testo;
        private java.util.List<String> risposte;
        private boolean required;
        private java.util.List<String> risposteUtente;

        public String getTipo() {
            return tipo;
        }

        public String getTesto() {
            return testo;
        }

        public java.util.List<String> getRisposte() {
            return risposte;
        }

        public boolean isRequired() {
            return required;
        }
    }

}
