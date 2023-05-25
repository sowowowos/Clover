package loverduck.clover.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class Funding {
    @Id
//    @SequenceGenerator(name = "funding_seq", sequenceName = "funding_seq", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "funding_seq")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String content;
    @NotNull
    private Long targetMinAmount;
    @NotNull
    private Long targetMaxAmount;
    @NotNull
    private Long currentAmount;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startDate;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime endDate;
    
    
    /*
    @NotNull
    @CreationTimestamp
    private Date endDate;
    @NotNull
    @CreationTimestamp
    private Date startDate; */
    
    @NotNull
    private Double dividend;
    //CreationTimeStamp
    //Date사용
    
    /**
     * 펀드 기업 승인 상태 
     * 0 : 대기
     * 1 : 승인
     * 2 : 거절 
     */
    @NotNull
    private Integer status;
    
    
    @NotNull
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Company company;
    
    public Funding(Long id) {
    	this.id=id;
    	
    }
    

}


