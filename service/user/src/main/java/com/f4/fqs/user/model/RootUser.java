package com.f4.fqs.user.model;


import com.f4.fqs.user.dto.ROOT.SignUpRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "p_group")
@NoArgsConstructor
public class RootUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Column(name = "group_name",nullable = false)
    private String groupName;

    @Column(name = "group_leader_name",nullable = false)
    private String groupLeaderName;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "role")
    private UserRoleEnum role;

    @OneToMany(mappedBy = "rootUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<IAMUser> iamUsers = new ArrayList<>();

    public RootUser(String encodedPassword, SignUpRequestDto requestDto) {
        this.groupName = requestDto.getGroupName();
        this.groupLeaderName = requestDto.getGroupLeaderName();
        this.email = requestDto.getEmail();
        this.password = encodedPassword;
        this.role = UserRoleEnum.ROOT;
    }
}
