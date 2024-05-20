package com.ohgiraffers.lmsjpa.menu.repository;

import com.ohgiraffers.lmsjpa.menu.entity.Menu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    // 파라미터로 전달받은 가격을 초과하는 메뉴 목록 조회
    List<Menu> findByMenuPriceGreaterThan(Integer menuPrice);

    // 파라미터로 전달 받은 가격을 초과하는 메뉴 목록을 가격순으로 조회
    List<Menu> findByMenuPriceGreaterThanOrderByMenuPrice(Integer menuPrice);

    // 파라미터로 전달 받은 가격을 메뉴 목록을 전달 받은 정렬 기준으로 조회
    List<Menu> findByMenuPriceGreaterThan(Integer menuPrice, Sort sort);
}
