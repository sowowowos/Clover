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
public class UserDetail {
    @Id
    @SequenceGenerator(name = "user_detail_seq", sequenceName = "user_detail_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_detail_seq")
    private Long id;
    private String name;
    private String phone;
    private String postalCode;
    private String address;
    private String detailAddress;
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Users user;
}
