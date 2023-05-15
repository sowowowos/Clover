package loverduck.clover.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class Funding {
    @Id
    @SequenceGenerator(name = "funding_seq", sequenceName = "funding_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "funding_seq")
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String content;
    @NotNull
    private Long targetMinAmount;
    @NotNull
    private Long targetMaxAmount;
    @NotNull
    private Long currentAmount;
    @NotNull
    private LocalDateTime startDate;
    @NotNull
    private LocalDateTime endDate;
    @NotNull
    private Double dividend;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

}


