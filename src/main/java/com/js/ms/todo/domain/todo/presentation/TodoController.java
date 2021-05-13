package com.js.ms.todo.domain.todo.presentation;

import com.js.ms.todo.domain.todo.application.TodoService;
import com.js.ms.todo.domain.todo.domain.Status;
import com.js.ms.todo.domain.todo.presentation.dto.TodoFindForm;
import com.js.ms.todo.domain.todo.presentation.dto.TodoSaveForm;
import com.js.ms.todo.domain.todo.presentation.dto.TodoUpdateForm;
import com.js.ms.todo.global.config.Response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    /**
     * 기능
     * 1. 할일 생성, 수정, 삭제, 조회(crud)
     * 2. 공유되고 있는 할일에 섹션이 생성, 수정, 삭제가 되었을시 알림
     */

    @GetMapping("/find/{sectionId}")
    public Response findTodoBySection(@PathVariable Long sectionId) {
        return todoService.findTodoBySection(sectionId);
    }

    @PostMapping("/save")
    public Response save(@Valid @RequestBody TodoSaveForm dto) {
        return todoService.save(dto);
    }

    @PostMapping("/update")
    public Response update(@Valid @RequestBody TodoUpdateForm dto) {
        return todoService.update(dto);
    }

    @PostMapping("/delete/{todoId}")
    public Response delete(@PathVariable Long todoId) {
        return todoService.delete(todoId);
    }

    @PostMapping("/findAll")
    public Response findTodoByStatusAndDate(@AuthenticationPrincipal Long memberId, @RequestBody TodoFindForm todoFindForm) {
        return todoService.findTodoByStatusAndDate(memberId, todoFindForm);
    }

    @GetMapping("/findByStatus/{status}")
    public Response findTodoByStatus(@AuthenticationPrincipal Long memberId, @PathVariable Status status) {
        return todoService.findTodoByStatus(memberId, status);
    }
}
