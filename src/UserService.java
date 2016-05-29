import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alicja on 25.05.16.
 */
public class UserService {
    static private UserBaseHandler userBaseHandler = new UserBaseHandler();
    static private SecretKey key = init();

    public boolean isLogged() {
        return true;
    }

    static public void changePassword(User user, String password) {
        user.setPassword(tripleEncrypt(password));
        userBaseHandler.save(user);

    }

    static public User checkPassword(String name, String password) {
        User user = userBaseHandler.findByName(name);
        if (user != null && tripleEncrypt(password).equals(user.getPassword())) {
            return user;
        } else {
            return null;
        }
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

    static public SecretKey init() {
        SecretKey tempKey = null;
        try {
            String filePathToStore = "/home/alicja/Desktop/twitter/key";
            File file = new File(filePathToStore);
            KeyStore ks = KeyStore.getInstance("JCEKS");

            if (file.exists()) {
                //load
                InputStream readStream = new FileInputStream(filePathToStore);
                ks.load(readStream, null);
                tempKey = (SecretKey) ks.getKey("keyAlias", "pass".toCharArray());
                readStream.close();
            } else {
                //generate and store
                tempKey = KeyGenerator.getInstance("DES").generateKey();
                ks.load(null, null);
                ks.setKeyEntry("keyAlias", tempKey, "pass".toCharArray(), null);
                OutputStream writeStream = new FileOutputStream(filePathToStore);
                ks.store(writeStream, "pass".toCharArray());
                writeStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempKey;
    }

    static public User register(String name, String password) {
        if (userBaseHandler.findByName(name) == null) {
            User user = new User();
            user.setName(name);
            user.setPassword(tripleEncrypt(password));
            user.setId(userBaseHandler.findLastId() + 1);
            userBaseHandler.save(user);
            return user;
        } else {
            return null;
        }
    }

    static public boolean checkPasswordEntropy(String password) {
        //check if entropy at least 3
        Map<String, Integer> map = new HashMap<String, Integer>();
        Double result = 0.0;
        char[] chars = password.toCharArray();
        for (char c : chars) {
            String s = "" + c;
            if (!map.containsKey(s)) {
                map.put(s, 0);
            }
            map.put(s, map.get(s) + 1);
        }
        for (String sequence : map.keySet()) {
            Double frequency = (double) map.get(sequence) / password.length();
            result -= frequency * (Math.log(frequency) / Math.log(2));
        }
        return result > 3;
    }

    static public String tripleEncrypt(String s) {
        return encrypt(encrypt(encrypt(s)));
    }
}