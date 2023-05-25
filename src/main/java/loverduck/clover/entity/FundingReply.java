package loverduck.clover.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class FundingReply {
    @Id
    @SequenceGenerator(name = "funding_reply_seq", sequenceName = "funding_reply_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "funding_reply_seq")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Funding funding;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
