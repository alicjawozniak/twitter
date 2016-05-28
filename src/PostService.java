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
}
