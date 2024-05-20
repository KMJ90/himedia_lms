package com.ohgiraffers.lmsjpa.service;

import com.ohgiraffers.lmsjpa.menu.dto.MenuDTO;
import com.ohgiraffers.lmsjpa.menu.entity.Menu;
import com.ohgiraffers.lmsjpa.menu.repository.CategoryRepository;
import com.ohgiraffers.lmsjpa.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    private final ModelMapper modelMapper;

    private final CategoryRepository categoryRepository;

    public MenuDTO findCoffeeByCode(int menuCode) {

        Menu foundCode = menuRepository.findById(menuCode).orElseThrow(IllegalArgumentException::new);
        return modelMapper.map(foundCode, MenuDTO.class);
    }

    public List<MenuDTO> findCoffeeList() {
        List<Menu> menuList = menuRepository.findAll(Sort.by("menuCode").descending());
        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).toList();
    }
}
