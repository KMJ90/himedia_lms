package com.ohgiraffers.lmsjpa.menu.controller;
import com.ohgiraffers.lmsjpa.common.Pagenation;
import com.ohgiraffers.lmsjpa.common.PagingButton;
import com.ohgiraffers.lmsjpa.menu.dto.CategoryDTO;
import com.ohgiraffers.lmsjpa.menu.dto.MenuDTO;
import com.ohgiraffers.lmsjpa.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/coffee")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    // findById
    @GetMapping("/{menuCode}")
    public String findCoffeeByCode(@PathVariable int menuCode, Model model) {

        MenuDTO foundMenu = menuService.findCoffeeByCode(menuCode);
        model.addAttribute("coffee", foundMenu);

        return "menu/detail";
    }

    // findAll : sort , pageable
    @GetMapping("/list")
    public String findCoffeeList(Model model, @PageableDefault Pageable pageable) {

        Page<MenuDTO> menuList = menuService.findCoffeeList(pageable);
        model.addAttribute("menuList", menuList);

        PagingButton paging = Pagenation.getPagingButtonInfo(menuList);
        model.addAttribute("paging", paging);

        return "menu/list";
    }

    // query method
    @GetMapping("/querymethod")
    public String  querymethodPage() {
        return "menu/querymethod";
    }

    @GetMapping("/search")
    public String findByMenuPrice(@RequestParam Integer menuPrice, Model model) {

        List<MenuDTO> menuList = menuService.findByMenuPrice(menuPrice);

        model.addAttribute("menuList", menuList);
        model.addAttribute("menuPrice", menuPrice);

        return "menu/searchResult";
    }

    // JPQL or Native Query
    @GetMapping("/regist")
    public String registPage() {

        return "/menu/regist";
    }
    @GetMapping("/category")
    @ResponseBody
    public List<CategoryDTO> findCategoryList() {

        return menuService.findAllCategory();
    }
    @PostMapping("/regist")
    public String registNewMenu(MenuDTO newMenu) {

        menuService.registMenu(newMenu);

        return "redirect:/menu/list";
    }

}
