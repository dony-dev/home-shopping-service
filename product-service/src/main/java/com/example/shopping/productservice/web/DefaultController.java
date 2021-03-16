package com.example.shopping.productservice.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {

//    @RequestMapping(value = "/")
//    public String home(Model model) {
//        return "application/products";
//    }

    @RequestMapping(value = "/category")
    public String category(Model model) {
        return "application/category-test";
    }

    @RequestMapping(value = "/products")
    public String products(Model model) {
        return "application/products-test";
    }

    @RequestMapping(value = "/orders")
    public String orders(Model model) {
        return "application/order-test";
    }

    @RequestMapping(value = "/product/details/{id}")
    public String productDetails(Model model) {
        return "application/product-detail";
    }
}
