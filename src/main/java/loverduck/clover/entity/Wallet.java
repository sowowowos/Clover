package loverduck.clover.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Setter
    @NotNull
    private Long amount;
    
    @JsonIgnore
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Users user;
}
