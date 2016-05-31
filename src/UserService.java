import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alicja on 25.05.16.
 */
public class UserService {
    static private SqlUserBaseHandler sqlUserBaseHandler = init();

    public boolean isLogged() {
        return true;
    }

    static public void changePassword(User user, String password) {
        user.setPassword(encrypt(password));
        sqlUserBaseHandler.save(user);

    }

    static public User checkPassword(String name, String password) {
        User user = sqlUserBaseHandler.findByName(name);
        if (user != null && encrypt(password).equals(user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }

    static public String encrypt(String text) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(text.getBytes());
            String encryptedString = new String(messageDigest.digest());
            return encryptedString;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return text;
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
                    " PASSWORD            TEXT   NOT NULL)";
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
            user.setPassword(encrypt(password));
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

    static public String tripleEncrypt(String s) {
        return encrypt(encrypt(encrypt(s)));
    }
}