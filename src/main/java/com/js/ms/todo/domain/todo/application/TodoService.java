package com.js.ms.todo.domain.todo.application;

import com.js.ms.todo.domain.category.domain.Category;
import com.js.ms.todo.domain.category.domain.MemberCategoryRepository;
import com.js.ms.todo.domain.member.domain.MemberCategory;
import com.js.ms.todo.domain.section.domain.Section;
import com.js.ms.todo.domain.section.domain.SectionRepository;
import com.js.ms.todo.domain.todo.domain.Status;
import com.js.ms.todo.domain.todo.domain.Todo;
import com.js.ms.todo.domain.todo.domain.TodoRepository;
import com.js.ms.todo.domain.todo.presentation.dto.TodoFindForm;
import com.js.ms.todo.domain.todo.presentation.dto.TodoInfo;
import com.js.ms.todo.domain.todo.presentation.dto.TodoSaveForm;
import com.js.ms.todo.domain.todo.presentation.dto.TodoUpdateForm;
import com.js.ms.todo.global.config.Response.Response;
import com.js.ms.todo.global.config.exception.ErrorCode;
import com.js.ms.todo.global.config.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final SectionRepository sectionRepository;
    private final MemberCategoryRepository memberCategoryRepository;

    @Transactional
    public Response save(TodoSaveForm dto) {
        Section section = sectionRepository.findById(dto.getSectionId()).orElseThrow(() -> new NotFoundException("해당 섹션이 존재하지 않습니다."));
        Todo todo = Todo.builder()
                .section(section)
                .name(dto.getName())
                .content(dto.getContent())
                .priority(dto.getPriority())
                .status(dto.getStatus())
                .startDate(dto.getStartDate())
                .endDate((dto.getEndDate()))
                .build();

        if (!ObjectUtils.isEmpty(todoRepository.save(todo))) {
            return Response.of("200", "할일이 생성 되었습니다.");
        } else {
            return Response.of(ErrorCode.TODO_SAVE_FAIL);
        }
    }

    @Transactional
    public Response update(TodoUpdateForm dto) {
        Todo todo = todoRepository.findById(dto.getTodoId()).orElseThrow(() -> new NotFoundException("해당 할일이 존재하지 않습니다."));
        Todo updateCompareTodo = todoRepository.findByName(dto.getName());

        if (!ObjectUtils.isEmpty(updateCompareTodo) && todo.getId().longValue() != updateCompareTodo.getId().longValue()) {
            return Response.of(ErrorCode.DUPLICATE_ROW, "이미 존재하는 할일 명 입니다.");
        }

        todo.update(dto);
        if (!ObjectUtils.isEmpty(todoRepository.save(todo))) {
            return Response.of("200", todo.getSection().getId(), "할일이 수정 되었습니다.");
        }

        return Response.of(ErrorCode.TODO_UPDATE_FAIL);
    }

    @Transactional
    public Response delete(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new NotFoundException("해당 할일이 존재하지 않습니다."));
        todoRepository.delete(todo);

        return Response.of("200", "할일이 삭제 되었습니다.");
    }

    @Transactional(readOnly = true)
    public Response find(Long sectionId) {
        List<Todo> todos = todoRepository.findBySectionId(sectionId);
        List<TodoInfo> todoInfos = new ArrayList<>();

        for (Todo todo : todos) {
            todoInfos.add(TodoInfo.convertFrom(todo));
        }

        return Response.of("200", todoInfos);
    }

    @Transactional(readOnly = true)
    public Response findTodoByStatusAndDate(Long memberId, TodoFindForm todoFindForm) {
        List<MemberCategory> memberCategories = memberCategoryRepository.findByMemberId(memberId);
        List<TodoInfo> todoInfos = new ArrayList<>();


        if (!ObjectUtils.isEmpty(todoFindForm.getStatus())) {
            for (MemberCategory memberCategory : memberCategories) {
                Category category = memberCategory.getCategory();
                List<Section> sections = sectionRepository.findByCategoryId(category.getId());
                for (Section section : sections) {
                    List<Todo> todos = todoRepository.findBySectionIdAndStatusAndEndDateBetween(section.getId(), todoFindForm.getStatus(), todoFindForm.getStartDate(), todoFindForm.getEndDate());
                    for (Todo todo : todos) {
                        todoInfos.add(TodoInfo.convertFrom(todo));
                    }
                }
            }
        } else {
            for (MemberCategory memberCategory : memberCategories) {
                Category category = memberCategory.getCategory();
                List<Section> sections = sectionRepository.findByCategoryId(category.getId());
                for (Section section : sections) {
                    List<Todo> todos = todoRepository.findBySectionIdAndEndDateBetween(section.getId(), todoFindForm.getStartDate(), todoFindForm.getEndDate());
                    for (Todo todo : todos) {
                        todoInfos.add(TodoInfo.convertFrom(todo));
                    }
                }
            }
        }

        return Response.of("200", todoInfos);
    }

    public Response findTodoByStatus(Long memberId, Status status) {
        List<MemberCategory> memberCategories = memberCategoryRepository.findByMemberId(memberId);
        List<TodoInfo> todoInfos = new ArrayList<>();

        if (String.valueOf(status).equals("ALL")) {
            for (MemberCategory memberCategory : memberCategories) {
                Category category = memberCategory.getCategory();
                List<Section> sections = sectionRepository.findByCategoryId(category.getId());
                for (Section section : sections) {
                    List<Todo> todos = todoRepository.findBySectionId(section.getId());
                    for (Todo todo : todos) {
                        todoInfos.add(TodoInfo.convertFrom(todo));
                    }
                }
            }
        } else {
            for (MemberCategory memberCategory : memberCategories) {
                Category category = memberCategory.getCategory();
                List<Section> sections = sectionRepository.findByCategoryId(category.getId());
                for (Section section : sections) {
                    List<Todo> todos = todoRepository.findBySectionIdAndStatus(section.getId(), status);
                    for (Todo todo : todos) {
                        todoInfos.add(TodoInfo.convertFrom(todo));
                    }
                }
            }
        }


        return Response.of("200", todoInfos);
    }

}
