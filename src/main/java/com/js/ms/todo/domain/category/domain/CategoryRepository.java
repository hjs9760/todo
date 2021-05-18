package com.js.ms.todo.domain.category.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}

/**
 * querydsl 적용 예
 */
//public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryQuery {
//}
