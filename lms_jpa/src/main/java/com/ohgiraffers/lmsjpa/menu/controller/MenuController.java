package com.ohgiraffers.lmsjpa.menu.controller;
import com.ohgiraffers.lmsjpa.menu.dto.MenuDTO;
import com.ohgiraffers.lmsjpa.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/coffee")
@RequiredArgsConstructor
@Slf4j
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/{menuCode}")
    public String findCoffeeByCode(@PathVariable int menuCode, Model model) {

        MenuDTO foundMenu = menuService.findCoffeeByCode(menuCode);
        model.addAttribute("coffee", foundMenu);

        return "menu/detail";
    }

    @GetMapping("/list")
    public String findCoffeeList(Model model) {
        List<MenuDTO> menuList = menuService.findCoffeeList();
        model.addAttribute("menuList", menuList);
        return "menu/list";
    }

}
