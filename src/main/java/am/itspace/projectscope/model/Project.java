package am.itspace.projectscope.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private Date createdDate;
    @Column
    private Date deadline;
    @ManyToOne
    private User user;
    @Column
    private double hours;
    @ManyToMany
    private List<User> member;
}
