import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

/**
 * Created by alicja on 28.05.16.
 */
public class PostService {
    static private SqlPostBaseHandler sqlPostBaseHandler = init();

    static public void addPost(String userName, String text) {
        Post post = new Post();
        post.setUserName(userName);
        post.setText(text);
        sqlPostBaseHandler.add(post);

    }

    static public List<Post> query() {
        return sqlPostBaseHandler.query();
    }

    static public boolean checkIfContainsWhitespaces(String s) {
        for (int i = 0; i < s.length(); ++i) {
            if (Character.isWhitespace(s.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    static public String convertSpecialSigns(String s) {
        return escapeHtml4(s);
    }

    static public boolean checkIfContainsNewLine(String s) {
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == '\n') {
                return true;
            }
        }
        return false;
    }

    static public SqlPostBaseHandler init() {
        SqlPostBaseHandler sqlPostBaseHandler = new SqlPostBaseHandler();
        SQLiteConfig config = new SQLiteConfig();
        SQLiteDataSource dataSource = new SQLiteDataSource(config);
        dataSource.setUrl("jdbc:sqlite:/home/alicja/Desktop/twitter/posts_sql");
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE POST " +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " USERNAME           TEXT    NOT NULL, " +
                    " TEXT            TEXT   NOT NULL)";
            statement.executeUpdate(sql);
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sqlPostBaseHandler;
    }
}
