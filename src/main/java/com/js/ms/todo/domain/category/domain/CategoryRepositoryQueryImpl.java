/**
 * querydsl 적용 예
 */

//package com.js.ms.todo.domain.category.domain;
//
//import com.js.ms.todo.domain.category.presentation.dto.CategoryShareInfo;
//import com.js.ms.todo.domain.member.domain.QMember;
//import com.js.ms.todo.domain.member.domain.QMemberCategory;
//import com.querydsl.core.types.Projections;
//import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
//
//import java.util.List;
//
//public class CategoryRepositoryQueryImpl extends QuerydslRepositorySupport implements CategoryRepositoryQuery{
//
//    public CategoryRepositoryQueryImpl() {
//        super(Category.class);
//    }
//
//
//    @Override
//    public List<CategoryShareInfo> findShareList(Long memberId) {
//        QCategory qCategory = QCategory.category;
//        QMemberCategory qMemberCaegory = QMemberCategory.memberCategory;
//        QMember qMember = QMember.member;
//
//        List<CategoryShareInfo> CategoryShareInfos = from(qMemberCaegory)
//                .join(qMember).on(qMember.id.eq(qMemberCaegory.member.id))
//                .join(qCategory).on(qCategory.id.eq(qMemberCaegory.category.id))
//                .orderBy(qCategory.name.asc())
//                .select(Projections.constructor(CategoryShareInfo.class, qCategory.id, qCategory.name, qCategory.startDate, qCategory.endDate, qCategory.status, qMember.name.as("memberName")))
//                .fetch();
//
//        return CategoryShareInfos;
//    }
//}
