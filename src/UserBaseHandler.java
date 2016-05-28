import java.io.*;

/**
 * Created by alicja on 25.05.16.
 */

public class UserBaseHandler {
    private String fileName = "/home/alicja/Desktop/twitter/users";

    //pattern: "id name password\n"

    public User findByName(String name) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                String[] splited = line.split("\\s+");
                if (name.equals(splited[1])) {
                    User user = new User();
                    user.setId(new Long(splited[0]));
                    user.setName(splited[1]);
                    user.setPassword(splited[2]);
                    return user;
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
        return null;
    }

    public User findById(Long id) {
        return new User();
    }

    public void save(User user) {

        String tmpFileName = "/home/alicja/Desktop/twitter/tmp_users";
        boolean found = false;

        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            bw = new BufferedWriter(new FileWriter(tmpFileName));
            String line;
            while ((line = br.readLine()) != null) {
                String[] splited = line.split("\\s+");
                if (user.getName().equals(splited[1])) {
                    found = true;
                    line = user.getId() + " " + user.getName() + " " + user.getPassword();
                }
                bw.write(line + "\n");
            }
            if (!found) {
                line = user.getId() + " " + user.getName() + " " + user.getPassword();
                bw.write(line + "\n");
            }
        } catch (Exception e) {
            return;
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                //
            }
            try {
                if (bw != null)
                    bw.close();
            } catch (IOException e) {
                //
            }
        }
        // Once everything is complete, delete old file..
        File oldFile = new File(fileName);
        oldFile.delete();

        // And rename tmp file's name to old file name
        File newFile = new File(tmpFileName);
        newFile.renameTo(oldFile);
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
