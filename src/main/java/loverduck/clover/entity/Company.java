package loverduck.clover.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String no;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String homepage;
    private String description;
    private String logo;
    private Integer type;
    private String sector;

    @OneToMany(mappedBy = "company")
    private List<CompanyKeyword> companyKeywords;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private FinanceReport financeReport;

    @ManyToOne(fetch = FetchType.LAZY)
    private CompanyMapData companyMapData;
    
    @OneToMany(mappedBy = "company")
    private List<Funding> funds;
}
