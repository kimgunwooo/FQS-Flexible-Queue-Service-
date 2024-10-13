package com.f4.fqs.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "p_single")
@NoArgsConstructor
public class IAMUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private RootUser rootUser;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role = "IAM";

    @Builder
    public IAMUser(RootUser rootUser, String email, String password, String role, String name, String groupName) {
        this.rootUser = rootUser;
        this.email = email;
        this.name = name;
        this.groupName = groupName;
        this.password = password;
        this.role = role;
    }

}
