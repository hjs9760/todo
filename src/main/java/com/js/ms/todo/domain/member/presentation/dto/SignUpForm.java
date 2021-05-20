package com.js.ms.todo.domain.member.presentation.dto;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
public class SignUpForm {

    @NotBlank
    @Length(min = 4, max = 20)
    @Pattern(regexp = "^[ㄱ-ㅎ가=힣a-z0-9_-]{3,20}$")
    private String userId;

    @NotBlank
    private String pw;

    @NotBlank
    private String name;

    @NotNull
    private LocalDate birth;

    @NotBlank
    private String gender;

    private String profileImage;

    @Email
    @NotBlank
    private String email;

}
