package loverduck.clover.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

    @Id
    @SequenceGenerator(name = "company_seq", sequenceName = "company_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_seq")
    private Long id;
    private String no;               //사업자 등록번호 *
    private String name;             //회사명 *
    private String address;          //주소
    private String detailAddress;    //상세주소
    private String phone;       
    private String email;
    private String homepage;       //기업 url 
    private String description;    // 기업 상세?
    @Setter
    private String logo;          //로고 이미지 이름
    /**
     * 기업승인 상태
     *  0 : 대기 (가입만 한 상태)
     */
    private Integer type;         
    private String sector;        //산업 선택 *

    @OneToMany(mappedBy = "company")
    private List<CompanyKeyword> companyKeywords;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private FinanceReport financeReport;

    @ManyToOne(fetch = FetchType.LAZY)
    private CompanyMapData companyMapData;
    
    @OneToMany(mappedBy = "company")
    private List<Funding> funds;
    
    public void setLogoPath(String logo) {
        this.logo = logo;
    }
}
