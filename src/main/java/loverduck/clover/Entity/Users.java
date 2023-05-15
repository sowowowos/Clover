package loverduck.clover.Entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String userid;
    @NotNull
    private String password;
    @Column(unique = true)
    @NotNull
    private String email;
    @Column(unique = true)
    @NotNull
    private String nickname;
    private Integer type;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private UserDetail userDetail;
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Wallet wallet;
    @OneToOne(fetch = FetchType.LAZY)
    private Company company;
}
