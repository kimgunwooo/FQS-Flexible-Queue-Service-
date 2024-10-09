package com.f4.fqs.user.dto.ROOT;

import com.f4.fqs.user.model.RootUser;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RootUserDto {
    private Long id;
    private String groupName;
    private String groupLeaderName;
    private String email;
    private String password;
    private String role;

    public static RootUserDto toResponse(RootUser rootUser){
        return RootUserDto.builder()
                .id(rootUser.getId())
                .groupName(rootUser.getGroupName())
                .groupLeaderName(rootUser.getGroupLeaderName())
                .email(rootUser.getEmail())
                .password(rootUser.getPassword())
                .role(rootUser.getRole())
                .build();
    }

}
