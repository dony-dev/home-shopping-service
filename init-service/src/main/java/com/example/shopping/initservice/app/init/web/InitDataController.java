package com.example.shopping.initservice.app.init.web;

import com.example.shopping.initservice.app.init.service.InitDataService;
import com.example.shopping.initservice.app.init.service.InitDataServiceGson;
import com.example.shopping.initservice.app.init.service.InitDataServiceSimpleJson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/init")
public class InitDataController {

    private final InitDataService initDataService;
    private final InitDataServiceSimpleJson initDataServiceSimpleJson;
    private final InitDataServiceGson initDataServiceGson;

    public InitDataController(InitDataService initDataService, InitDataServiceSimpleJson initDataServiceSimpleJson, InitDataServiceGson initDataServiceGson) {
        this.initDataService = initDataService;
        this.initDataServiceSimpleJson = initDataServiceSimpleJson;
        this.initDataServiceGson = initDataServiceGson;
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
//        initDataService.initProductCommentData();
//        initDataService.initProductCommentDataDiscovery();
        initDataService.initProductCommentDataLoadBalanced();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/category/simple")
    public ResponseEntity<Void> initProductCategoryDataSimple() {
        initDataServiceSimpleJson.executeWithForLoop();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/category/simple2")
    public ResponseEntity<Void> initProductCategoryDataSimple2() {
        initDataServiceSimpleJson.executeWithStream();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/product/gson")
    public ResponseEntity<Void> initProductDataGson() {
        initDataServiceGson.executeWithForLoop();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/product/gson2")
    public ResponseEntity<Void> initProductDataGsonStream() {
        initDataServiceGson.executeWithStream();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /* */
    @GetMapping(value = "/invoke")
    public ResponseEntity<String> invokeProductService() {
        String result = initDataService.invokeProductService();
        return ResponseEntity.status(HttpStatus.OK).body("invoked \n" + result);
    }
}
