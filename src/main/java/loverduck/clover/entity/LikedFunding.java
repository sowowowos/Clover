package loverduck.clover.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikedFunding {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "liked_funding_seq")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Funding funding;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Users user;
}
