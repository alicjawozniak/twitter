import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by alicja on 28.05.16.
 */
public class PostService {
    static private PostBaseHandler postBaseHandler = new PostBaseHandler();

    static public void addPost(String userName, String text) {
        Post post = new Post();
        post.setLocalDateTime(LocalDateTime.now());
        post.setUserName(userName);
        post.setId(postBaseHandler.findLastId() + 1);
        post.setText(text);
        postBaseHandler.add(post);

    }

    static public List<Post> query() {
        return postBaseHandler.query();
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
        String temp = "";
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c == '&') {
                temp += "&amp;";
            } else if (c == '<') {
                temp += "&lt;";
            } else if (c == '>') {
                temp += "&gt;";
            } else if (c == '`') {
                temp += "&#x60;";
            } else if (c == '\'') {
                temp += "&#x27;";
            } else if (c == '\"') {
                temp += "&quot;";
            } else if (c == '/') {
                temp += "&#x2F;";
            } else {
                temp += c;
            }
        }
        return temp;
    }

    static public boolean checkIfContainsNewLine(String s) {
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == '\n') {
                return true;
            }
        }
        return false;
    }
}
