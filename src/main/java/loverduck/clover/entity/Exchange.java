package loverduck.clover.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Exchange {
    @Id
    @SequenceGenerator(name = "exchange_seq", sequenceName = "exchange_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exchange_seq")
    private Long id;

    @NotNull
    private Long amount;
    @NotNull
    private Integer type;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Wallet wallet;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    public Exchange() {

    }
}
