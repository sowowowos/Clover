package loverduck.clover.Entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Reply {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String content;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Users user;
}
