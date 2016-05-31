import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alicja on 31.05.16.
 */
public class SqlPostBaseHandler {

    Connection c;

    public List<Post> query() {
        List<Post> postList = new ArrayList<>();
        Connection c = null;
        Statement stmt = null;
        User user = null;
        try {
            c = getConnection();
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM POST;");
            while (rs.next()) {
                Post post = new Post();
                int id = rs.getInt("id");
                String userName = rs.getString("username");
                String text = rs.getString("text");
                post.setId(new Long(id));
                post.setUserName(userName);
                post.setText(text);
                postList.add(post);
            }

            stmt.close();
            c.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Records created successfully");
        return postList;
    }

    public void add(Post post) {
        Connection c = null;
        PreparedStatement stmt = null;
        try {
            c = getConnection();
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            String sql = "INSERT INTO POST (USERNAME,TEXT) " +
                    "VALUES (?,?);";
            stmt = c.prepareStatement(sql);

            stmt.setString(1, post.getUserName());
            stmt.setString(2, post.getText());
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
                c = DriverManager.getConnection("jdbc:sqlite:/home/alicja/Desktop/twitter/posts_sql");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return c;
    }
}
