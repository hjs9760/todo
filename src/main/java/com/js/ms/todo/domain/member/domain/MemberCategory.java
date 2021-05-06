package com.js.ms.todo.domain.member.domain;

import com.js.ms.todo.domain.category.domain.Category;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


    public static MemberCategory createMemberCategory(Category category, Member member) {
        MemberCategory memberCategory = new MemberCategory();
        memberCategory.member = member;
        memberCategory.category = category;

        return memberCategory;
    }
}
