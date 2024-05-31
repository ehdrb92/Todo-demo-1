package com.donggyu.tododemo1.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

// TODO: Jakarta와 Jackson 패키지에 대한 좀 더 자세한 학습

@Entity(name = "users") // STUDY: 엔터티(테이블) 클래스 구성
@Getter
@Setter
public class User {
    @Id // id 열로 지점
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 값 자동 생성
    private Long id;

    /*
    * STUDY:
    * Jakarta 열 지정 주석
    *
    * name: 열 이름
    * nullable: null 가능 여부
    * length: 데이터의 최대 길(varchar와 같은 유형의 데이터에 사용)
    * unique: 고유 데이터 여부
    * */
    @Column(name = "username", nullable = false, length = 255, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    @JsonIgnore // 특정 속성 수준에서 Json 직렬화 또는 역직렬화할 때 해당 속성을 제외하도록 설정한다.
    private String password;

    /*
    * STUDY:
    * @JsonIgnore와 유사한 기능을 하는 주석
    *
    * @JsonIgnoreProperties({ "property1", "property2" }): 클래스 수준에서 특정 속성들을 무시하고 싶을 때 사용된다.
    * @JsonInclude(Include.NON_NULL): 클래스 수준에서 특정 속성들을 포함하는 조건을 설정한다. 예시는 NULL을 제외한다.
    * @JsonProperty: 속성 수준에서 직렬 및 역직렬화에서 속성의 이름을 설정한다.
    * */

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Date createdAt;
}
