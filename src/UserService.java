import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
/**
 * Created by alicja on 25.05.16.
 */
public class UserService {
    private UserBaseHandler userBaseHandler = new UserBaseHandler();
    private SecretKey key;

    public boolean isLogged() {
        return true;
    }

    public void changePassword() {

    }

    public String encrypt(String text) {
        try {
            Cipher ecipher = Cipher.getInstance("DES");
            ecipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] utf8 = text.getBytes("UNICODE");
            byte[] enc = ecipher.doFinal(utf8);

            return new String(enc, "UNICODE");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }

    public String decrypt(String text) {
        try {
            Cipher dcipher = Cipher.getInstance("DES");
            dcipher.init(Cipher.DECRYPT_MODE, key);
            byte[] bytes = text.getBytes("UNICODE");
            bytes = Arrays.copyOfRange(bytes, 2, bytes.length);
            byte[] ud = dcipher.doFinal(bytes);

            return new String(ud, "UNICODE");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }

    public void init() {
        try {
            key = KeyGenerator.getInstance("DES").generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}