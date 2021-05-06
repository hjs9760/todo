package com.js.ms.todo.domain.category.presentation.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ShareMemberInfo {

    private List<String> name;
    private Integer shareCount;
}
