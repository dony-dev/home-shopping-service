package com.example.shopping.initservice.app.init.service;

import com.example.shopping.initservice.app.init.shared.ProductRequest;
import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class InitDataServiceGson {

    private final String fileLocation = "config/init_product_service.json";
    private Logger log = LoggerFactory.getLogger(InitDataServiceGson.class);

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    public void executeWithForLoop() {
        Long startTime = System.nanoTime();
        initProductWithForLoop();
        Long res = System.nanoTime() - startTime;
        log.info("execute product time - for loop: {}", res);   // for loop: 1290462900
    }

    public void executeWithStream() {
        Long startTime = System.nanoTime();
        initProductWithStream();
        Long res = System.nanoTime() - startTime;
        log.info("execute product time - stream: {}", res);     // stream: 4855600
    }

    private void initProductWithForLoop() {
        String res = readJsonFile(fileLocation);

        List<ProductRequest> productRequestList = getProductRequestList(res);
        log.info("productRequestList.toString(): {}", productRequestList.toString());

        ServiceInstance serviceInstance = loadBalancerClient.choose("product-service");
        String baseUrl = "http://" + serviceInstance.getServiceId();

        for (ProductRequest productRequest : productRequestList) {
            restTemplate.postForEntity(baseUrl + "/api/products/product/create", productRequest, String.class);
        }
    }

    private void initProductWithStream() {
        String res = readJsonFile(fileLocation);

        List<ProductRequest> productRequestList = getProductRequestList(res);
        log.info("productRequestList.toString(): {}", productRequestList.toString());

        ServiceInstance serviceInstance = loadBalancerClient.choose("product-service");
        String baseUrl = "http://" + serviceInstance.getServiceId();

        productRequestList.stream().map(productRequest -> {
            restTemplate.postForEntity(baseUrl + "/api/products/product/create", productRequest, String.class);
            return "";
        });
    }

    private String readJsonFile(String fileLocation) {
        StringBuilder sb = new StringBuilder();
//        Resource resource = new ClassPathResource(fileLocation);
        try (InputStream inputStream = new ClassPathResource(fileLocation).getInputStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("sb.toString(): {}", sb.toString());

        return sb.toString();
    }

    public List<ProductRequest> getProductRequestList(String jsonString) {

        JsonObject jsonObject = new Gson().fromJson(jsonString, JsonObject.class);
        JsonElement prodElement = jsonObject.get("product");

        JsonArray prodElementAsJsonArray = prodElement.getAsJsonArray();
        log.info("prodElementAsJsonArray.toString(): {}", prodElementAsJsonArray.toString());

        List<ProductRequest> productRequestList = new ArrayList<>();
        for (int i = 0; i < prodElementAsJsonArray.size(); i++) {
            JsonObject prodObject = prodElementAsJsonArray.get(i).getAsJsonObject();

            ProductRequest productRequest = new ProductRequest();
            productRequest.setProductName(prodObject.get("productName").toString());
            productRequest.setProductDescription(prodObject.get("productDescription").toString());
            productRequest.setProductPrice(prodObject.get("productPrice").getAsBigDecimal());
            productRequest.setCategoryId(prodObject.get("categoryId").getAsLong());
            productRequestList.add(productRequest);
        }
        log.info("productRequestList.toString(): {}", productRequestList.toString());

        return productRequestList;
    }
}
