package domains;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "USER")
public class User {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private UUID id;
//    private String surname;

    @Column(name = "NAME")
    private String name;
//    private String fatherName;
//    private Date birthDate;
//    private String text;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "user")
    private List<Message> messages;
}
