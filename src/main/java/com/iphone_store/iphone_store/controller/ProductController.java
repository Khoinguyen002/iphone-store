package com.iphone_store.iphone_store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.iphone_store.iphone_store.dto.OptionItem;
import com.iphone_store.iphone_store.dto.ProductFilter;
import com.iphone_store.iphone_store.entity.Product;
import com.iphone_store.iphone_store.service.CategoryService;
import com.iphone_store.iphone_store.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("products/list");
        mav.addObject("products", productService.getFirst10Products());
        return mav;
    }

    @GetMapping("/new")
    public ModelAndView createForm() {
        ModelAndView mav = new ModelAndView("products/form");
        mav.addObject("product", new Product());
        mav.addObject("categories", categoryService.getAllCategories());
        return mav;
    }

    @PostMapping("/new")
    public ModelAndView save(@ModelAttribute Product product) {
        productService.saveProduct(product);
        return new ModelAndView("redirect:/products");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("products/form");
        mav.addObject("product", productService.getProductById(id));
        mav.addObject("categories", categoryService.getAllCategories());
        return mav;
    }

    @PostMapping("/update/{id}")
    public ModelAndView update(@PathVariable Long id, @ModelAttribute Product product) {
        product.setId(id);
        productService.saveProduct(product);
        return new ModelAndView("redirect:/products");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ModelAndView("redirect:/products");
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam(defaultValue = "") String keyword,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(required = false) Long categoryId) {
        ModelAndView mav = new ModelAndView("search");
    
        // Gọi service với điều kiện lọc (giả sử bạn có method mới)
        var productPage = productService.searchByKeywordAndCategoryPaged(keyword, categoryId, page, size);

        ProductFilter filter = new ProductFilter();
        filter.setCategoryId(categoryId);
        filter.setKeyword(keyword);
        filter.setSize(size);

        List<OptionItem> pageSizes = List.of(
            new OptionItem(5, "5"),
            new OptionItem(10, "10"),
            new OptionItem(20, "20"),
            new OptionItem(50, "50")
        );
    
        mav.addObject("products", productPage.getContent());
        mav.addObject("totalPages", productPage.getTotalPages());
        mav.addObject("currentPage", page);
        mav.addObject("keyword", keyword);
        mav.addObject("size", size);
        mav.addObject("selectedCategoryId", categoryId);
        mav.addObject("categories", categoryService.getAllCategories());
        mav.addObject("productFilter", filter);
        mav.addObject("pageSizes", pageSizes);
    
        return mav;
    }
}
