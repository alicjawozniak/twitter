import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by alicja on 24.05.16.
 */
@Entity
public class User {
    @Id
    private Long id;

    private String name;

    private String password;
}
