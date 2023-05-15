package loverduck.clover.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Reply {
    @Id
    @SequenceGenerator(name = "reply_seq", sequenceName = "reply_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reply_seq")
    private Long id;

    @NotNull
    private String content;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Users user;
}
