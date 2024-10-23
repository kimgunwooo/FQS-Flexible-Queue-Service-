package com.f4.fqs.user.dto.IAM;

import com.f4.fqs.user.model.IAMUser;
import com.f4.fqs.user.model.UserRoleEnum;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class IAMUserDto {

    private Long id;
    private Long groupId;
    private String groupName;
    private String email;
    private String name;
    private String role;

    public static IAMUserDto toResponse(IAMUser iamUser){
        return IAMUserDto.builder()
                .id(iamUser.getId())
                .groupId(iamUser.getRootUser().getId())
                .email(iamUser.getEmail())
                .name(iamUser.getName())
                .role(UserRoleEnum.IAM.getAuthority())
                .build();
    }
}
