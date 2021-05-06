package com.js.ms.todo.domain.category.presentation.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class CategoryShareForm {

    @NotNull
    private Long categoryId;

    @NotBlank
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;


}
