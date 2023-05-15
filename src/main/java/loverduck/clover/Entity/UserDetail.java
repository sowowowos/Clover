package loverduck.clover.Entity;

import javax.persistence.*;

@Entity
public class UserDetail {
    @Id
    @SequenceGenerator(name = "USER_DETAIL_SEQ", sequenceName = "USER_DETAIL_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_DETAIL_SEQ")
    private Long id;
    private String email;
    private String name;
    private String phone;
    private String postalCode;
    private String address;
    private String detailAddress;
}
