package loverduck.clover.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
    @Id
    @SequenceGenerator(name = "users_seq", sequenceName = "users_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    private Long id;

    @NotNull
    @Column(unique = true)
    @NotEmpty
    private String userid;
    @NotNull
    @NotEmpty
    private String password;
    @Column(unique = true)
    @NotNull
    @NotEmpty
    private String email;
    @NotNull
    @NotEmpty
    private String name;

    /**
     * 투자자 : 닉네임
     * 기업 : 기업명
     */
    @Column(unique = true)
    @NotNull
    private String nickname;

    /**
     * 투자자 : 프로필 사진
     * 기업 : 로고 이미지
     */
    private String imgProfile;

    /**
     * 1 : 투자자
     * 0 : 기업
     */
    private Integer type;


    private String phone;
    private String postalCode;
    private String address;
    private String detailAddress;


    @ManyToOne(fetch = FetchType.LAZY)
    private Wallet wallet;
    @OneToOne(fetch = FetchType.LAZY)
    private Company company;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
