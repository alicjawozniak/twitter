import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * Created by alicja on 25.05.16.
 */
@Entity
public class Post {
    @Id
    private Long id;

    @ManyToOne
    private User user;

    private String text;

    private LocalDateTime localDateTime;


}
