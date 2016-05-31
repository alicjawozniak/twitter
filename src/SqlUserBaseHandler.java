import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by alicja on 31.05.16.
 */
public class SqlUserBaseHandler {

    public User findByName(String name) {
        Connection c = null;
        Statement stmt = null;
        User user = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:/home/alicja/Desktop/twitter/users_sql");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            name = new StringBuilder().append('\'').append(name).append('\'').toString();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USER WHERE NAME IS " + name + ";");
            int id = rs.getInt("id");
            String password = rs.getString("password");
            user = new User();
            user.setId(new Long(id));
            user.setName(name);
            user.setPassword(password);

            stmt.close();
            c.commit();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Records created successfully");
        return user;
    }

    public void save(User user) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:/home/alicja/Desktop/twitter/users_sql");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String values = new StringBuilder().append("\'").append(user.getName()).append("\', \'").append(user.getPassword()).append('\'').toString();
            String sql = "INSERT INTO USER (NAME,PASSWORD) " +
                    "VALUES (" + values + ");";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Records created successfully");
    }
}
