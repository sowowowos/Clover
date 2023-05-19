package loverduck.clover.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.sun.istack.NotNull;

@Entity
@Getter
@NoArgsConstructor
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
    
    @NotNull
    @CreatedDate
    private LocalDateTime createdAt;
}
