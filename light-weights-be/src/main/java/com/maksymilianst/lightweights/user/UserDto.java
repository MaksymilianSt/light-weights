package com.maksymilianst.lightweights.user;

import lombok.Builder;
import lombok.Data;

import java.util.*;

@Data
@Builder
public class UserDto {
    private Integer id;
    private String email;
    private String nickname;
    private Set<Role> roles;
}
