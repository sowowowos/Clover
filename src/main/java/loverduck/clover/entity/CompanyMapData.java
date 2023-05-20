package loverduck.clover.entity;

import javax.persistence.*;

@Entity
public class CompanyMapData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Company company;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CrawlingData crawlingData;
}
