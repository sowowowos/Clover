package loverduck.clover.Entity;

import javax.persistence.*;

@Entity
public class Users {
    @Id
    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "USER_SEQ", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String userid;
    private String password;
    private String nickname;
    private Integer type;
    @OneToOne
    private UserDetail userDetail;
}
