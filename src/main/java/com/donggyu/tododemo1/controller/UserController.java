package com.donggyu.tododemo1.controller;

import com.donggyu.tododemo1.ResponseErrorDetail;
import com.donggyu.tododemo1.user.User;
import com.donggyu.tododemo1.user.UserJoinDTO;
import com.donggyu.tododemo1.service.UserService;
import com.donggyu.tododemo1.user.UserUpdateDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
* @RestController: @Controller에 @ResponseBody가 추가된 어노테이션이다. 일반적으로 Spring MVC 모델에서 사용되는 @Controller 주석은 브라우저에 표현할 페이지인 View 객체를 반환하는게 기본이다. 물론 @ResponseBody 어노테이션을 통해 Json 응답이 가능하다. RestController의 Rest는 REST API를 뜻한다. REST API는 표현할 정보를 JSON으로 응답하는 모델이기 때문에 해당 어노테이션을 활용할 수 있다.
*
* @RequestMapping: Controller 클래스 수준에서 공통 엔드포인트를 설정한다.
* */

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> createUser(@RequestBody UserJoinDTO userJoinDTO) {
        try {
            return new ResponseEntity<>(userService.createUser(userJoinDTO), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(new ResponseErrorDetail(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable(value = "id") Long id) {
        return userService.getUser(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(value = "id") Long id, @RequestBody UserUpdateDTO userUpdate) {
        try {
            boolean updateResult = userService.updateUser(id, userUpdate);
            if (updateResult) {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.set("Location", String.format("/api/v1/users/%d", id));
                return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
            }
            return new ResponseEntity<>(new ResponseErrorDetail("Database Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseErrorDetail(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseErrorDetail(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}

/*
* STUDY:
*
* Spring Layered Architecture?
*
* Controller(Presentation), Service(Business Logic), Repository(Data Access)
*
* 각 계층에 대한 역할 요약은 다음과 같다.
* Controller 계층은 HTTP 요청 및 응답 처리를 담당합니다. 클라이언트(예: 웹 브라우저, 모바일 앱)와 애플리케이션의 비즈니스 로직 사이의 중개자 역할을 합니다.
* Service 계층에는 애플리케이션의 비즈니스 로직이 포함되어 있습니다. 컨트롤러 계층과 리포지토리 계층 사이의 가교 역할을 합니다.
* Repository 계층은 데이터 액세스 및 지속성을 담당합니다. 데이터베이스 또는 기타 지속성 메커니즘과 상호 작용하여 CRUD 작업을 수행합니다.
* */

// TODO: 그렇다면 스프링 MVC 모델은?? 이에 대해 좀 더 이해가 필요하다.