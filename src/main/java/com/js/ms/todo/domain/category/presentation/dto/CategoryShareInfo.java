package com.js.ms.todo.domain.category.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryShareInfo {

    private Long categoryId;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String statusName;
    private ShareMemberInfo shareMemberInfos;

}
