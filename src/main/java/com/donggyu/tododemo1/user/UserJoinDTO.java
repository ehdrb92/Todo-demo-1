package com.donggyu.tododemo1.user;

import lombok.Getter;
import lombok.Setter;

/*
* STUDY:
* DTO 클래스는 어디서 사용하는 건가?
*
* 레이어 혹은 서비스 간에 데이터를 효율적으로 전송하는데 사용된다.
* 일반적으로 필드, 게터, 세터, 생성자만 포함하며, 비즈니스 로직이나 동작은 포함하지 않는다.
* 네트워크를 통해 데이터를 전송할 때와 같이 데이터를 직렬화, 역직렬화 하는 시나리오(예: REST API)에서 자주 사용한다.
* */

@Getter
@Setter
public class UserJoinDTO {
    private String username;
    private String password;
    private String email;
    private String role;
}
