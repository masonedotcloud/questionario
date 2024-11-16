package functionality;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Gestione della password sicura
 */
public class MD5 {

    /**
     * Hash dell'input
     *
     * @param input testo in chiaro
     * @return testo criptato
     * @throws NoSuchAlgorithmException errore
     */
    public static String hash(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(input.getBytes());
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }
}


