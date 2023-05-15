package loverduck.clover.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
