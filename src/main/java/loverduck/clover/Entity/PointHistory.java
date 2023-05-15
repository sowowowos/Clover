package loverduck.clover.Entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class PointHistory {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long amount;
    @NotNull
    private Integer Type;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Wallet wallet;

    @ManyToOne(fetch = FetchType.LAZY)
    private Exchange exchange;
    @ManyToOne(fetch = FetchType.LAZY)
    private Funding funding;
}
