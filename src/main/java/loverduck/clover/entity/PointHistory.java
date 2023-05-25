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
    
    /**
     * 0 - 충전 (포인트 충전)
     * 1 - 사용 (기업에 펀딩 완료시)
     * 2 - 환전 (포인트 환전)
     * 3 - 배당 (펀딩 종료후 배당금 지급시)
     */
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
