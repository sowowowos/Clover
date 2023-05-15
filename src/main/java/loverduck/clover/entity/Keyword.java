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
public class Keyword {
    @Id
    @SequenceGenerator(name = "keyword_seq", sequenceName = "keyword_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "keyword_seq")
    private Long id;
    @Column(unique = true, nullable = false)
    private String keyword;
}
