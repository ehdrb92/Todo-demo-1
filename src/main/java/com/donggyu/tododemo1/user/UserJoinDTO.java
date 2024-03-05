package com.donggyu.tododemo1.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserJoinDTO {
    private String username;
    private String password;
    private String email;
    private String role;
}
