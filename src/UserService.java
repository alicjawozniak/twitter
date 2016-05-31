import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by alicja on 25.05.16.
 */
public class UserService {
    static private SqlUserBaseHandler sqlUserBaseHandler = init();

    static public void changePassword(User user, String password) {
        user = sqlUserBaseHandler.findByName(user.getName());
        byte[] newPassword = hash(password.toCharArray(), user.getSalt());
        user.setHashedPassword(newPassword);
        sqlUserBaseHandler.update(user);

    }

    static public User checkPassword(String name, String password) {
        User user = sqlUserBaseHandler.findByName(name);
        if (user != null) {
            byte[] pass = hash(password.toCharArray(), user.getSalt());
            if (Arrays.equals(pass, user.getHashedPassword())) {
                return user;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    static public SqlUserBaseHandler init() {
        SqlUserBaseHandler sqlUserBaseHandler = new SqlUserBaseHandler();
        SQLiteConfig config = new SQLiteConfig();
        SQLiteDataSource dataSource = new SQLiteDataSource(config);
        dataSource.setUrl("jdbc:sqlite:/home/alicja/Desktop/twitter/users_sql");
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE USER " +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " NAME           TEXT    NOT NULL, " +
                    "HASHEDPASSWORD BLOB," +
                    "SALT BLOB)";
            statement.executeUpdate(sql);
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sqlUserBaseHandler;
    }

    static public User register(String name, String password) {
        if (sqlUserBaseHandler.findByName(name) == null) {
            User user = new User();
            user.setName(name);
            byte[] salt = getNextSalt();
            user.setHashedPassword(hash(password.toCharArray(), salt));
            user.setSalt(salt);
            sqlUserBaseHandler.save(user);
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

    private static final Random RANDOM = new SecureRandom();
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    public static byte[] getNextSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }

    /**
     * Returns a salted and hashed password using the provided hash.<br>
     * Note - side effect: the password is destroyed (the char[] is filled with zeros)
     *
     * @param password the password to be hashed
     * @param salt     a 16 bytes salt, ideally obtained with the getNextSalt method
     * @return the hashed password with a pinch of salt
     */
    public static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }
}