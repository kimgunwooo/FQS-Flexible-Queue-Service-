package com.f4.fqs.user.dto;

import com.f4.fqs.user.model.IAMUser;
import com.f4.fqs.user.model.RootUser;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {
    private Long id;
    private String email;
    private String rootEmail;
    private String password;
    private String role;

    public static UserDto toResponse(RootUser rootUser){
        return UserDto.builder()
                .id(rootUser.getId())
                .email(rootUser.getEmail())
                .password(rootUser.getPassword())
                .role(rootUser.getRole())
                .build();
    }

    public static UserDto toResponse(IAMUser iamUser){
        return UserDto.builder()
                .id(iamUser.getId())
                .email(iamUser.getEmail())
                .rootEmail(iamUser.getGroupEmail())
                .password(iamUser.getPassword())
                .role(iamUser.getRole())
                .build();
    }

}
