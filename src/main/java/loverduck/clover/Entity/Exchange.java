package loverduck.clover.Entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Exchange {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long amount;
    @NotNull
    private Integer type;

    @ManyToOne(optional = false)
    private Wallet wallet;

    @ManyToOne
    private Company company;

    public Exchange() {

    }
}
