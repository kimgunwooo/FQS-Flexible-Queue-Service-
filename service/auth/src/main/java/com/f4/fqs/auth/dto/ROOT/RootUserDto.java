package com.f4.fqs.auth.dto.ROOT;

import com.f4.fqs.auth.UserRoleEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RootUserDto {
    private String groupName;
    private String groupLeaderName;
    private Long id;
    private String email;
    private String role;
}
