package loverduck.clover.Entity;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @OneToOne(fetch = FetchType.LAZY)
    private FinanceReport financeReport;
}
