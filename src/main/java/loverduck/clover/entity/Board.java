package loverduck.clover.entity;

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
    @SequenceGenerator(name = "board_seq", sequenceName = "board_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq")
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
