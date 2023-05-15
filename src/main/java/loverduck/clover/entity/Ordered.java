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
public class Ordered {
    @Id
    @SequenceGenerator(name = "ordered_seq", sequenceName = "ordered_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ordered_seq")
    private Long id;
    @NotNull
    private Long amount;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Funding funding;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Users user;
}
