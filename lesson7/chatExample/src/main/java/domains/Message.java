package domains;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "MESSAGE")
public class Message {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User author;
//    private User recipient;
//    private Date sentDate;

}
