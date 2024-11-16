package manager;

import java.util.HashMap;
import java.util.Map;

/**
 * Questa classe fornisce un metodo statico per controllare se è possibile creare un nuovo account in base all'indirizzo IP del dispositivo che effettua la richiesta.
 */
public class WaitAction {
    /**
     * Tempo minimo (in millisecondi) da attendere tra la creazione di due account dallo stesso dispositivo.
     */
    private static final long MIN_TIME_BETWEEN_ACCOUNTS = 5 * 60 * 1000;
    /**
     * Mappa che memorizza l'indirizzo IP del dispositivo e il timestamp dell'ultimo account creato.
     */
    private static final Map<String, Long> lastAccountCreatedMap = new HashMap<>();

    /**
     * Controlla se è possibile creare un nuovo account per il dispositivo con l'indirizzo IP specificato.
     *
     * @param ipAddress l'indirizzo IP del dispositivo che effettua la richiesta.
     * @return true se è possibile creare un nuovo account, false altrimenti.
     */
    public static boolean check(String ipAddress) {
        try {
            // Recupera il timestamp corrente
            long currentTime = System.currentTimeMillis();

            // Recupera il timestamp dell'ultimo account creato per questo indirizzo IP
            Long lastAccountCreatedTime = lastAccountCreatedMap.get(ipAddress);

            if (lastAccountCreatedTime == null || currentTime - lastAccountCreatedTime > MIN_TIME_BETWEEN_ACCOUNTS) {

                // Aggiorna il timestamp dell'ultimo account creato per questo indirizzo IP
                lastAccountCreatedMap.put(ipAddress, currentTime);

                return true;

            } else {

                return false;

            }
        } catch (Exception ignored) {
            return true;
        }

    }

}
