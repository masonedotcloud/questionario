package questionnaire;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import functionality.DatabaseConnection;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import manager.Redirect;
import manager.WaitAction;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.*;
import java.sql.ResultSet;
import java.util.List;

/**
 * Servlet per processare i dati del questionario ricevuti dal form e inserirli nel database
 */
@WebServlet("/questionnaire/process")
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
     * Gestisce le richieste POST, processando i dati del questionario ricevuti dal form e inserendoli nel database
     *
     * @param request  La richiesta HTTP ricevuta
     * @param response La risposta HTTP da inviare
     * @throws IOException In caso di errore d'I/O
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Redirect.GetPageWhitMessage(request, response, "/page.jsp", process(request));

    }

    /**
     * Estrae l'ID del questionario e la sua definizione JSON dal database, deserializza il JSON
     * in una lista di domande e recupera le risposte dell'utente per poi aggiungerle alle domande del questionario.
     * Infine, salva la risposta dell'utente nel database.
     *
     * @param request la richiesta HTTP contenente i parametri della risposta
     * @return una stringa HTML con il messaggio di conferma o di errore
     */
    private String process(HttpServletRequest request) {
        String jsonString;
        int id_questionnaire;
        DatabaseConnection db;


        if (!WaitAction.check(request.getRemoteAddr())) {
            return "<p class=\"text-center\">Devi attendere prima di compilare un altro questionario, torna alla <a href=\"/" + Redirect.web_app_name() + "\">home</a></p>";
        }

        // tenta di estrarre l'ID del questionario e la sua definizione JSON dal database
        try {
            db = new DatabaseConnection();

            id_questionnaire = Integer.parseInt(request.getParameter("id"));

            String query = "SELECT * FROM form WHERE ID ='" + id_questionnaire + "'";

            ResultSet rs = db.executeQuery(query);
            rs.last();

            jsonString = rs.getString("json");
        } catch (Exception ignored) {
            // se ci sono errori durante l'estrazione, ritorna un messaggio di errore
            return "<p class=\"text-center\">Errore durante il caricamento, ritorna alla <a href=\"/" + Redirect.web_app_name() + "\">home</a></p>";
        }

        // tenta di deserializzare il JSON del questionario in una lista di domande e di recuperare le risposte dell'utente
        try {
            Reader jsonReader = new BufferedReader(new StringReader(jsonString));
            List<Domanda> domande = new Gson().fromJson(jsonReader, new TypeToken<List<Domanda>>() {
            }.getType());

            RispostaUtente[] risposteUtente = new RispostaUtente[domande.size()];
            for (int i = 0; i < domande.size(); i++) {
                String id = "domanda" + i;
                String[] risposte = request.getParameterValues(id);
                risposteUtente[i] = new RispostaUtente(risposte);
            }

            // aggiunge le risposte dell'utente alle domande del questionario
            Gson gson = new Gson();
            JsonArray jsonArray = gson.toJsonTree(domande).getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                jsonObject.add("risposteUtente", gson.toJsonTree(risposteUtente[i].risposte));
            }

            String risposta_json = StringEscapeUtils.unescapeJava(gson.toJson(jsonArray)).replace("'", "\\'");


            // Inserisce all'interno del database il questionario compilato
            HttpSession session = request.getSession(false);
            int id_user = (session != null && session.getAttribute("ID") != null) ? Integer.parseInt(session.getAttribute("ID").toString()) : (0);

            String q = "INSERT INTO `response`(`id_form`, `json`, `id_user`) VALUES ('" + id_questionnaire + "','" + risposta_json + "', '" + id_user + "')";
            db.manipulationQuery(q);

            // Aumenta il numero di questionari dell'utente se ha effettuato l'acceoss
            try {

                if (session != null && session.getAttribute("questionari") != null) {
                    Integer n_act = Integer.parseInt(session.getAttribute("questionari").toString()) + 1;
                    session.setAttribute("questionari", n_act);
                }

            } catch (Exception ignored) {
            }

            // messaggio di successo
            return "<p class=\"text-center\">Risposta salvata correttamente, ritorna alla <a href=\"/" + Redirect.web_app_name() + "\">home</a></p>";
        } catch (Exception ignored1) {
            // se ci sono errori durante l'estrazione, ritorna un messaggio di errore
            return "<p class=\"text-center\">Errore durante il caricamento, ritorna alla <a href=\"/" + Redirect.web_app_name() + "\">home</a></p>";
        }
    }

    /**
     * Classe per rappresentare un oggetto Domanda del questionario in formato JSON
     */
    public class Domanda {
        private String tipo;
        private String testo;
        private List<String> risposte;
        private boolean required;
        private String rispostaUtente;
    }

    /**
     * Classe per rappresentare un oggetto RispostaUtente in formato JSON
     */
    public class RispostaUtente {
        private final String[] risposte;

        public RispostaUtente(String[] risposte) {
            this.risposte = risposte;
        }
    }

}

