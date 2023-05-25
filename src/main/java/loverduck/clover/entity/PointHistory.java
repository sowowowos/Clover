package loverduck.clover.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    @SequenceGenerator(name = "point_history_seq", sequenceName = "point_history_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "point_history_seq")
    private Long id;
    @NotNull
    private Long amount;
    @NotNull
    private Integer type;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Wallet wallet;

    @ManyToOne(fetch = FetchType.LAZY)
    private Exchange exchange;
    @ManyToOne(fetch = FetchType.LAZY)
    private Funding funding;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
