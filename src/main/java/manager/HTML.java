package manager;

import functionality.WebXmlParams;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HTML {

    public static String readFile(String path) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator()); // Aggiunge un separatore di riga
            }
        } catch (IOException e) {
            e.printStackTrace(); // Gestione dell'errore
            return null;
        }
        return sb.toString();
    }

    public static String getCardUserQuestionnaire() {

        return readFile(WebXmlParams.getPath() + "/static/questionnaire/CardUser.html");

    }

    public static String getCardSingleUserQuestionnaire() {
        return readFile(WebXmlParams.getPath() + "/static/questionnaire/CardSingleUser.html");
    }

    public static String getCardAdminQuestionnaire() {
        return readFile(WebXmlParams.getPath() + "/static/questionnaire/CardAdmin.html");
    }

    public static String getQuestionnaireCardAdminQuestionnaire() {
        return readFile(WebXmlParams.getPath() + "/static/questionnaire/QuestionnaireCardAdmin.html");
    }

    public static String getCardAdminUsers() {
        return readFile(WebXmlParams.getPath() + "/static/users/CardAdmin.html");
    }


}
