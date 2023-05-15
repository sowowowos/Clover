package loverduck.clover.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikedCompany {
    @Id
    @SequenceGenerator(name = "liked_company_seq", sequenceName = "liked_company_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "liked_company_seq")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Company company;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Users user;
}
