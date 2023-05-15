package loverduck.clover.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Users user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    private Funding funding;
}
