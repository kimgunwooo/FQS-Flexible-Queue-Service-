package com.f4.fqs.user.model;


import com.f4.fqs.user.dto.ROOT.SignUpRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "group_leader_name")
    private String groupLeaderName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role = "ROOT";

    public RootUser(String encodedPassword, SignUpRequestDto requestDto) {
        this.groupName = requestDto.getGroupName();
        this.groupLeaderName = requestDto.getGroupLeaderName();
        this.email = requestDto.getEmail();
        this.password = encodedPassword;
        this.role = requestDto.getRole();
    }
}
