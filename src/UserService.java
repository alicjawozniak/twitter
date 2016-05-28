import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.KeyStore;
import java.util.Arrays;
/**
 * Created by alicja on 25.05.16.
 */
public class UserService {
    static private UserBaseHandler userBaseHandler = new UserBaseHandler();
    static private SecretKey key;

    public boolean isLogged() {
        return true;
    }

    public void changePassword() {

    }

    static public boolean checkPassword(String name, String password) {
        User user = userBaseHandler.findByName(name);
        return encrypt(password).equals(user.getPassword());
    }

    static public String encrypt(String text) {
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

    static public String decrypt(String text) {
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

    static public void init() {
        try {
            String filePathToStore = "/home/alicja/Desktop/twitter/key";
            File file = new File(filePathToStore);
            KeyStore ks = KeyStore.getInstance("JCEKS");

            if (file.exists()) {
                //load
                InputStream readStream = new FileInputStream(filePathToStore);
                ks.load(readStream, null);
                key = (SecretKey) ks.getKey("keyAlias", "pass".toCharArray());
                readStream.close();
            } else {
                //generate and store
                key = KeyGenerator.getInstance("DES").generateKey();
                ks.load(null, null);
                ks.setKeyEntry("keyAlias", key, "pass".toCharArray(), null);
                OutputStream writeStream = new FileOutputStream(filePathToStore);
                ks.store(writeStream, "pass".toCharArray());
                writeStream.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public User register(String name, String password) {
        if (userBaseHandler.findByName(name) == null) {
            User user = new User();
            user.setName(name);
            user.setPassword(encrypt(password));
            user.setId(userBaseHandler.findLastId() + 1);
            userBaseHandler.save(user);
            return user;
        } else {
            return null;
        }
    }
}