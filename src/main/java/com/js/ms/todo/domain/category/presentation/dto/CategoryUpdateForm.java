package com.js.ms.todo.domain.category.presentation.dto;

import com.js.ms.todo.domain.category.domain.Status;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
public class CategoryUpdateForm {

    @NotNull
    private Long categoryId;

    @NotBlank
    @Length(min = 1, max = 30)
    private String name;

    @NotNull
    private Status status;

    private LocalDate startDate;

    private LocalDate endDate;

}
