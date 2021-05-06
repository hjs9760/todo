package com.js.ms.todo.domain.member.domain;

import com.js.ms.todo.domain.notice.domain.Notice;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @Builder.Default private List<MemberCategory> memberCategories= new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @Builder.Default private List<Notice> notices = new ArrayList<>();


    @Column(unique = true)
    @Length(min = 4, max = 20)
    @Pattern(regexp = "^[ㄱ-ㅎ가=힣a-z0-9_-]{3,20}$")
    private String userId;

    @Column
    private String pw;

    @Column
    @NotBlank
    private String name;

    @Column
    private String oauthId;

    @Column
    private String providerName;

    @Column
    private String accessToken;

    @Column
    private String refreshToken;

    @Enumerated(EnumType.STRING)
    @Column
    @Builder.Default private Role role = Role.ROLE_USER;

    @Column
    private LocalDate birth;

    @Column
    private String gender;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String profileImage;

    @Column(unique = true)
    @NotBlank
    private String email;

    @Column
    private String emailCheckToken;

    @Column
    private boolean emailCheck;

    @NotNull
    private LocalDateTime joinAt;

    public void convertEmailCheck(boolean state) {
        this.emailCheck = state;
    }

    public Member(String email, String oauthId, String name, String providerName, String accessToken, String refreshToken) {

        this.email = email;
        this.oauthId = oauthId;
        this.name = name;
        this.providerName = providerName;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.joinAt = LocalDateTime.now();
    }

    public void updateOauth2Info(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
