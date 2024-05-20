package com.ohgiraffers.lmsjpa.menu.repository;
import com.ohgiraffers.lmsjpa.menu.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "SELECT category_code, category_name, ref_category_code FROM tbl_category ORDER BY category_code",
            nativeQuery = true)

    List<Category> findAllCategory();
}
