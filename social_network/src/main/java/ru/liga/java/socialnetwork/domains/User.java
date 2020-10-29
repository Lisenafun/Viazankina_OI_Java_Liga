package ru.liga.java.socialnetwork.domains;

import lombok.*;
import ru.liga.java.socialnetwork.domains.enums.Gender;

import javax.persistence.*;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="FIRST_NAME", length=50, nullable=false)
    private String firstName;

    @Column(name="LAST_NAME", length=50, nullable=false)
    private String lastName;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length=1045)
    private String interests;

    @Column(length=50)
    private String town;

    @OneToMany(mappedBy = "friend", fetch = FetchType.LAZY)
    private Set<Friendship> friends;
}
