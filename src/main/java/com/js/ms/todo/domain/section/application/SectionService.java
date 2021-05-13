package com.js.ms.todo.domain.section.application;

import com.js.ms.todo.domain.category.domain.Category;
import com.js.ms.todo.domain.category.domain.MemberCategoryRepository;
import com.js.ms.todo.domain.member.domain.MemberCategory;
import com.js.ms.todo.domain.section.domain.Section;
import com.js.ms.todo.domain.section.domain.SectionRepository;
import com.js.ms.todo.domain.section.presentation.dto.SectionInfo;
import com.js.ms.todo.domain.section.presentation.dto.SectionSaveForm;
import com.js.ms.todo.domain.section.presentation.dto.SectionUpdateForm;
import com.js.ms.todo.global.config.Response.Response;
import com.js.ms.todo.global.config.exception.ErrorCode;
import com.js.ms.todo.global.config.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;
    private final MemberCategoryRepository memberCategoryRepository;

    @Transactional
    public Response save(Long memberId, SectionSaveForm dto) {
        MemberCategory memberCategory = memberCategoryRepository.findByMemberIdAndCategoryId(memberId, dto.getCategoryId());
        if (ObjectUtils.isEmpty(memberCategory)) return Response.of(ErrorCode.SECTION_SAVE_FAIL);
        Category category = memberCategory.getCategory();

        if(!ObjectUtils.isEmpty(sectionRepository.findByName(dto.getName()))) {
            return Response.of(ErrorCode.DUPLICATE_ROW, "이미 존재하는 섹션명 입니다.");
        }

        Section section = Section.builder()
                .category(category)
                .name(dto.getName())
                .build();

        if (!ObjectUtils.isEmpty(sectionRepository.save(section))) {
            return Response.of("200", "섹션이 생성 되었습니다.");
        } else {
            return Response.of(ErrorCode.SECTION_SAVE_FAIL);
        }
    }

    @Transactional
    public Response update(SectionUpdateForm dto) {
        Section section = sectionRepository.findById(dto.getSectionId()).orElseThrow(() -> new NotFoundException("해당 섹션이 존재하지 않습니다."));
        if(!ObjectUtils.isEmpty(sectionRepository.findByName(dto.getName()))) {
            return Response.of(ErrorCode.DUPLICATE_ROW, "이미 존재하는 섹션명 입니다.");
        }
        
        section.update(dto);
        if (!ObjectUtils.isEmpty(sectionRepository.save(section))) {
            return Response.of("200", "섹션이 수정 되었습니다.");
        }

        return Response.of(ErrorCode.SECTION_UPDATE_FAIL);
    }

    @Transactional
    public Response delete(Long sectionId) {
        Section section = sectionRepository.findById(sectionId).orElseThrow(() -> new NotFoundException("해당 섹션이 존재하지 않습니다."));
        sectionRepository.delete(section);

        return Response.of("200", "섹션이 삭제 되었습니다.");
    }

    @Transactional(readOnly = true)
    public Response findSectionByCategory(Long categoryId) {
        List<Section> sections = sectionRepository.findByCategoryId(categoryId);
        List<SectionInfo> sectionInfos = new ArrayList<>();

        for (Section section : sections) {
            sectionInfos.add(SectionInfo.convertFrom(section));
        }

        return Response.of("200", sectionInfos);
    }
}
