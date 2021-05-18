package com.js.ms.todo.domain.todo.domain;

import com.js.ms.todo.domain.section.domain.Section;
import com.js.ms.todo.domain.todo.presentation.dto.TodoUpdateForm;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private Section section;

    @Column
    private String name;

    @Column
    private String content;

    @Column
    @Builder.Default private Integer priority = 99;

    @Enumerated(EnumType.STRING)
    @Column
    private Status status;

    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

    public void update(TodoUpdateForm dto) {
        this.name = dto.getName();
        this.content = dto.getContent();
        this.status = dto.getStatus();
        this.priority = dto.getPriority();
        this.startDate = dto.getStartDate();
        this.endDate = dto.getEndDate();
    }
}
