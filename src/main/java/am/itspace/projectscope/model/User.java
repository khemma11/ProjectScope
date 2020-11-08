package am.itspace.projectscope.model;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String email;

    @Column
    private String phoneNumber;

    @Column
    private String password;

    @Column
    private String profilePicture;

    @Enumerated(value = EnumType.STRING)
    private UserType usertype;

}
