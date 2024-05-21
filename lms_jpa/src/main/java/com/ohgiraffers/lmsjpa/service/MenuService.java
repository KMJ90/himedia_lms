package com.ohgiraffers.lmsjpa.service;
import com.ohgiraffers.lmsjpa.menu.dto.CategoryDTO;
import com.ohgiraffers.lmsjpa.menu.dto.MenuDTO;
import com.ohgiraffers.lmsjpa.menu.entity.Category;
import com.ohgiraffers.lmsjpa.menu.entity.Menu;
import com.ohgiraffers.lmsjpa.menu.repository.CategoryRepository;
import com.ohgiraffers.lmsjpa.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    private final ModelMapper modelMapper;

    private final CategoryRepository categoryRepository;

    // 1. findById
    public MenuDTO findCoffeeByCode(int menuCode) {

        // findById()를 이용하여 menuCode 가져와서 foundCode 변수에 담고, menuRepository 에 전달
        Menu foundCode = menuRepository.findById(menuCode).orElseThrow(IllegalArgumentException::new);
        // modelMapper 를 이용하여 foundCode(엔티티)를 MenuDTO 타입으로 변환
        return modelMapper.map(foundCode, MenuDTO.class);
    }

    // 2. findAll : Sort
    public List<MenuDTO> findCoffeeList() {
        List<Menu> menuList = menuRepository.findAll(Sort.by("menuCode").descending());
        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).toList();
    }

    // 3. findAll : pageable
    public Page<MenuDTO> findCoffeeList(Pageable pageable) {
        // PageRequest 생성
        pageable = PageRequest.of(
                // 사용자가 페이지 번호를 0이하로 입력했다면, 0으로 설정하고, 그렇지 않은 경우 -1을 한 값을 사용
                pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() -1,  // 몇번째 페이지

                pageable.getPageSize(),  // 몇개의 컨텐츠를 표시할 것인가

                Sort.by("menuCode").descending()  // menuCode 필드를 기준으로 역순으로 정렬
        );
        // 지정된 페이징 및 정렬 기준에 따라 Menu 엔티티의 목록을 페이지 단위로 데이터베이스에서 가져옴
        Page<Menu> menuList = menuRepository.findAll(pageable);

        // modelMapper 를 이용하여 Menu 엔티티 객체를 MenuDTO 객체로 변환
        return menuList.map(menu -> modelMapper.map(menu, MenuDTO.class));
    }

    // 4. Query Method
    // 메뉴 가격을 데이터베이스에서 가져와서 정렬을 하고, menuList 변수에 담음, 엔티티를 MenuDTO 로 변환
    public List<MenuDTO> findByMenuPrice(Integer menuPrice) {

        List<Menu> menuList = menuRepository.findByMenuPriceGreaterThan(menuPrice, Sort.by("menuPrice").descending());

        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).toList();
    }

    // 5. JPQL or Native Query
    public List<CategoryDTO> findAllCategory() {

        List<Category> categoryList = categoryRepository.findAllCategory();

        return categoryList.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
    }

    // regist : 저장
    @Transactional
    // MenuDTO 객체를 입력으로 받아서 데이터베이스에 저장하는 메서드
    public void registMenu(MenuDTO menuDTO) {

        // modelMapper 를 이용하여 menuDTO 객체를 Menu 엔티티 타입으로 변환 후 menuRepository 를 통해 DB에 저장
        menuRepository.save(modelMapper.map(menuDTO, Menu.class));

    }
}
