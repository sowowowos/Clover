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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinanceReport {
    @SequenceGenerator(name = "FINANCIAL_SEQ", sequenceName = "FINANCIAL_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "FINANCIAL_SEQ", strategy = javax.persistence.GenerationType.SEQUENCE)
    @Id
    private Long id;
    /**
     * 대표자
     */
    private String ceo;
    /**
     * 설립일
     */
    private String establishmentDate;
    /**
     * 기업규모
     */
    private String companyScale;
    /**
     * 상장형태
     */
    private String listingType;
    /**
     * 매출액
     */
    private String sales;
    /**
     * 영업이익
     */
    private String operatingProfit;
    /**
     * 당기손익
     */
    private String netIncome;
    /**
     * 신용등급
     */
    private String creditRating;
    /**
     * 사원수
     */
    private String employees;
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Company company;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

