package loverduck.clover.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
    @Id
    @SequenceGenerator(name = "users_seq", sequenceName = "users_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    private Long id;

    @NotNull
    @Column(unique = true)
    @NotEmpty
    private String userid;
    @NotNull
    @NotEmpty
    private String password;
    @Column(unique = true)
    @NotNull
    @NotEmpty
    private String email;
    @Column(unique = true)
    @NotNull
    private String nickname;
    private Integer type;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserDetail userDetail;
    @ManyToOne(fetch = FetchType.LAZY)
    private Wallet wallet;
    @OneToOne(fetch = FetchType.LAZY)
    private Company company;
}
