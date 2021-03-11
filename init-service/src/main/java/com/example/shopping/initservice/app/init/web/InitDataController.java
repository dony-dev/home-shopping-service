package com.example.shopping.initservice.app.init.web;

import com.example.shopping.initservice.app.init.service.InitDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/init")
public class InitDataController {

    private final InitDataService initDataService;

    public InitDataController(InitDataService initDataService) {
        this.initDataService = initDataService;
    }

    @GetMapping("/test")
    public ResponseEntity<String> testApiCall() {
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    @RequestMapping(value = "/category")
    public ResponseEntity<Void> initProductCategoryData() throws IOException {
        initDataService.initProductCategoryData();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/product")
    public ResponseEntity<Void> initProductData() throws IOException {
        initDataService.initProductData();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/comment")
    public ResponseEntity<Void> initProductComment() throws IOException {
        initDataService.initProductCommentData();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
