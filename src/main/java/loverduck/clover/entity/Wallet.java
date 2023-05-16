package loverduck.clover.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Wallet {
    @Id
    @SequenceGenerator(name = "wallet_seq", sequenceName = "wallet_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wallet_seq")
    private Long id;
    @NotNull
    private Long amount;
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Users user;
}
