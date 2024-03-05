package com.donggyu.tododemo1.todo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TodoUpdateDTO {
    private String content;
    private Date deadline;
}
