package loverduck.clover.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyKeyword {
    @Id
    @SequenceGenerator(name = "company_keyword_seq", sequenceName = "company_keyword_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_keyword_seq")
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Company company;
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Keyword keyword;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
