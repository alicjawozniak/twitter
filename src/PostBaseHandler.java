import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alicja on 28.05.16.
 */
public class PostBaseHandler {
    private String fileName = "/home/alicja/Desktop/twitter/posts";

    //pattern: "id userName localDateTime text\n"


    public void add(Post post) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(fileName, true));
            String line = post.getId() + " " + post.getUserName() + " " + post.getLocalDateTime() + " " + post.getText() + "\n";
            bw.append(line);
        } catch (Exception e) {
            return;
        } finally {
            try {
                if (bw != null)
                    bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public List<Post> query() {
        BufferedReader br = null;
        List<Post> postList = new ArrayList<Post>();
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                String[] splited = line.split("\\s+");
                Post post = new Post();
                post.setId(new Long(splited[0]));
                post.setUserName(splited[1]);
                post.setLocalDateTime(LocalDateTime.parse(splited[2]));
                int index = splited[0].length() + 1 + splited[1].length() + 1 + splited[2].length() + 1;
                post.setText(line.substring(index));

                postList.add(post);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return postList;
    }

    public Long findLastId() {
        Long lastId = 0L;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                String[] splited = line.split("\\s+");
                Long l = new Long(splited[0]);
                if (l > lastId) {
                    lastId = l;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lastId;
    }
}
