package com.example.shopping.initservice.app.init.service;

import com.example.shopping.initservice.app.init.shared.ProductCategoryRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.*;

@Service
public class InitDataServiceSimpleJson {

    private final String fileLocation = "config/init_product_service.json";
    private Logger log = LoggerFactory.getLogger(InitDataServiceSimpleJson.class);

    @Autowired
    private RestTemplate restTemplate;

    public void executeWithForLoop() {
        Long startTime = System.nanoTime();
        initProductCategoryWithForLoop();
        Long res = System.nanoTime() - startTime;
        log.info("execute category time - for loop: {}", res);
    }

    public void executeWithStream() {
        Long startTime = System.nanoTime();
        initProductCategoryWithStream();
        Long res = System.nanoTime() - startTime;
        log.info("execute category time - stream: {}", res);
    }

    private String readJsonFile(String fileLocation) {
        StringBuilder sb = new StringBuilder();
        try {
            Resource resource = new ClassPathResource(fileLocation);
            InputStream inputStream = resource.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private void initProductCategoryWithForLoop() {

        String res = readJsonFile(fileLocation);
        log.info(res);

        JSONParser jsonParser = new JSONParser();
        try {
            Object obj = jsonParser.parse(res);
            Map map = (Map) obj;
//            log.info("map.toString(): " + map.toString());

            List<ProductCategoryRequest> productCategoryRequestList = new ArrayList<>();
            JSONArray jsonArray = (JSONArray) map.get("productCategory");
//            log.info("jsonArray: {}", jsonArray);

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Map map1 = (Map) jsonObject;

                ProductCategoryRequest productCategoryRequest = new ProductCategoryRequest();
                productCategoryRequest.setCategoryName(map1.get("categoryName").toString());
                productCategoryRequest.setCategoryDescription(map1.get("categoryDescription").toString());
                productCategoryRequestList.add(productCategoryRequest);
            }
//            log.info("productCategoryRequestList: {}", productCategoryRequestList);

            for (ProductCategoryRequest productCategoryRequest : productCategoryRequestList) {
                restTemplate.postForEntity("http://PRODUCT-SERVICE/api/categories/category/add", productCategoryRequest, String.class);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void initProductCategoryWithStream() {

        String res = readJsonFile(fileLocation);
        log.info(res);

        JSONParser jsonParser = new JSONParser();
        try {
            Object obj = jsonParser.parse(res);
            Map map = (Map) obj;
//            log.info("map.toString(): " + map.toString());

            List<ProductCategoryRequest> productCategoryRequestList = new ArrayList<>();
            JSONArray jsonArray = (JSONArray) map.get("productCategory");
//            log.info("jsonArray: {}", jsonArray);

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Map map1 = (Map) jsonObject;

                ProductCategoryRequest productCategoryRequest = new ProductCategoryRequest();
                productCategoryRequest.setCategoryName(map1.get("categoryName").toString());
                productCategoryRequest.setCategoryDescription(map1.get("categoryDescription").toString());
                productCategoryRequestList.add(productCategoryRequest);
            }
//            log.info("productCategoryRequestList: {}", productCategoryRequestList);

            productCategoryRequestList.stream().map(productCategoryRequest -> {
                restTemplate.postForEntity("http://PRODUCT-SERVICE/api/categories/category/add", productCategoryRequest, String.class);
                return "";
            });

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
