import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by alicja on 31.05.16.
 */
public class SqlUserBaseHandler {
    private Connection c;

    public User findByName(String name) {
        Connection c = null;
        PreparedStatement stmt = null;
        User user = null;
        try {
            c = getConnection();
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.prepareStatement("SELECT * FROM USER WHERE NAME IS ? ;");
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            int id = rs.getInt("id");
            name = rs.getString("name");
            byte[] hashedPassword = rs.getBytes("hashedpassword");
            byte[] salt = rs.getBytes("salt");
            user = new User();
            user.setId(id);
            user.setName(name);
            user.setHashedPassword(hashedPassword);
            user.setSalt(salt);

            stmt.close();
            c.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Records created successfully");
        return user;
    }

    public void save(User user) {
        Connection c = null;
        PreparedStatement stmt = null;
        try {
            c = getConnection();
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");


            String sql = "INSERT INTO USER (NAME, HASHEDPASSWORD, SALT) " +
                    "VALUES (?,?,?);";
            stmt = c.prepareStatement(sql);
            stmt.setString(1, user.getName());
            stmt.setBytes(2, user.getHashedPassword());
            stmt.setBytes(3, user.getSalt());

            stmt.executeUpdate();

            stmt.close();
            c.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Records created successfully");
    }

    private Connection getConnection() {
        if (c == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:/home/alicja/Desktop/twitter/users_sql");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return c;
    }

    public void update(User user) {
        Connection c = null;
        PreparedStatement stmt = null;
        try {
            c = getConnection();
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");


            String sql = "UPDATE USER SET HASHEDPASSWORD = ?," +
                    "SALT = ?" +
                    "WHERE ID IS ?";
            stmt = c.prepareStatement(sql);
            stmt.setBytes(1, user.getHashedPassword());
            stmt.setBytes(2, user.getSalt());
            stmt.setInt(3, user.getId());
            stmt.executeUpdate();

            stmt.close();
            c.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Records created successfully");
    }
}
