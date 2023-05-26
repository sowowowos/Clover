package loverduck.clover.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
    @NotEmpty
    private String bank;
    
    @NotNull
    @NotEmpty
    private String accountHolder;	//예금주
    
    @NotNull
    @NotEmpty
    private String account;	//계좌번호
    
    @NotNull
    @NotEmpty
    private Long amount;	//환전 신청 금액
    
    /**
     * 환전 신청 대기 
     * 0(default) : 대기,
     * 1 : 환전 승인/완료
     * 2 : 거절 
     */
    @NotNull
    @NotEmpty
    private Long status; //환전 신청 대기 0(default), 완료 1, 거절 2
    
    /**
     * 1 : 투자자
     * 0 : 기업
     */
    @NotNull
    @NotEmpty
    private Integer type;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Wallet wallet;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Exchange() {

    }
}
