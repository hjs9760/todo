package com.js.ms.todo.domain.category.domain;

import com.js.ms.todo.domain.member.domain.MemberCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberCategoryRepository extends JpaRepository<MemberCategory, Long> {
    MemberCategory findByMemberIdAndCategoryId(Long memberId, Long categoryId);

    List<MemberCategory> findByMemberId(Long memberId);

    List<MemberCategory> findByCategoryId(Long id);
}
