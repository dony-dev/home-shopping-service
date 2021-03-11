package com.example.shopping.initservice.app.init.service;

import com.example.shopping.initservice.app.init.shared.ProductCategoryRequest;
import com.example.shopping.initservice.app.init.shared.ProductCommentDto;
import com.example.shopping.initservice.app.init.shared.ProductRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InitDataService {

    private Logger log = LoggerFactory.getLogger(InitDataService.class);

    private final String fileLocation = "config/init_product_service.json";

    @Autowired
    private RestTemplate restTemplate;

    public void initProductCategoryData() throws IOException {
        InputStream is = getStreamFromResource(fileLocation);
        JsonNode categoryNode = getCategoryNode(is);

        List<ProductCategoryRequest> productCategoryRequestList = getCategoryFromNode(categoryNode);
        log.info("productCategoryRequestList.size: {}", productCategoryRequestList.size());

        // rest call
        List<ResponseEntity<String>> resList = productCategoryRequestList.stream().map(productCategoryRequest -> {
//            ResponseEntity<String> res = restTemplate.postForEntity("http://localhost:9091/api/categories/category/add", productCategoryRequest, String.class);
            ResponseEntity<String> res = restTemplate.postForEntity("http://PRODUCT-SERVICE/api/categories/category/add", productCategoryRequest, String.class);
            return res;
        }).collect(Collectors.toList());
//        log.info("res: {}", tfList.toString());
    }

    public void initProductData() throws IOException {
        InputStream is = getStreamFromResource(fileLocation);
        JsonNode productNode = getProductNode(is);

        List<ProductRequest> productRequestList = getProductFromNode(productNode);
        log.info("productRequestList.size: {}", productRequestList.size());

        // rest call
        List<ResponseEntity<String>> resList = productRequestList.stream().map(productRequest -> {
//            ResponseEntity<String> res = restTemplate.postForEntity("http://localhost:9091/api/products/product/create", productRequest, String.class);
            ResponseEntity<String> res = restTemplate.postForEntity("http://PRODUCT-SERVICE/api/products/product/create", productRequest, String.class);
            return res;
        }).collect(Collectors.toList());
//        log.info("res: {}", resList.toString());
    }

    public void initProductCommentData() throws IOException {
        InputStream is = getStreamFromResource(fileLocation);
        JsonNode productCommentNode = getProductCommentNode(is);

        List<ProductCommentDto> productCommentDtoList = getProductCommentFromNode(productCommentNode);
        log.info("productCommentDtoList.size: {}", productCommentDtoList.size());

        // rest call
        List<ResponseEntity<String>> resList = productCommentDtoList.stream().map(productCommentDto -> {
//            ResponseEntity<String> res = restTemplate.postForEntity("http://localhost:9091/api/products/product/create", productCategoryRequest, String.class);
            ResponseEntity<String> res = restTemplate.postForEntity("http://PRODUCT-SERVICE/api/product/comments/comment", productCommentDto, String.class);
            return res;
        }).collect(Collectors.toList());
//        log.info("res: {}", resList.toString());
    }

    private InputStream getStreamFromResource(String fileLocation) {
        ClassLoader classLoader = InitDataService.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileLocation);

        if (inputStream == null) {
            throw new IllegalArgumentException("init data file \"" + fileLocation + "\" not found.");
        } else {
            return inputStream;
        }
    }

    private JsonNode getCategoryNode(InputStream is) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        DataInputStream dis = new DataInputStream(is);
        JsonNode categoryNode = objectMapper.readTree(dis).path("productCategory");
        log.info("categoryNode -------------- \n{}\n------------------------", categoryNode.toPrettyString());

        return categoryNode;
    }

    private List<ProductCategoryRequest> getCategoryFromNode(JsonNode categoryNode) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductCategoryRequest> productCategoryRequests = objectMapper.convertValue(categoryNode, new TypeReference<List<ProductCategoryRequest>>() {
        }).stream().collect(Collectors.toList());
        log.info("productCategoryRequests: {}", productCategoryRequests.toString());

        return productCategoryRequests;
    }

    private JsonNode getProductNode(InputStream is) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        DataInputStream dis = new DataInputStream(is);
        JsonNode productNode = objectMapper.readTree(dis).path("product");
        log.info("productNode -------------- \n{}\n------------------------", productNode.toPrettyString());

        return productNode;
    }

    private List<ProductRequest> getProductFromNode(JsonNode productNode) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductRequest> productRequestList = objectMapper.convertValue(productNode, new TypeReference<List<ProductRequest>>() {
        }).stream().collect(Collectors.toList());
        log.info("productRequestList: {}", productRequestList.toString());

        return productRequestList;
    }

    private JsonNode getProductCommentNode(InputStream is) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        DataInputStream dis = new DataInputStream(is);
        JsonNode productCommentNode = objectMapper.readTree(dis).path("productComment");
        log.info("productCommentNode -------------- \n{}\n------------------------", productCommentNode.toPrettyString());

        return productCommentNode;
    }

    private List<ProductCommentDto> getProductCommentFromNode(JsonNode productCommentNode) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductCommentDto> productCommentDtoList = objectMapper.convertValue(productCommentNode, new TypeReference<List<ProductCommentDto>>() {
        }).stream().collect(Collectors.toList());
        log.info("productCommentDtoList: {}", productCommentDtoList.toString());

        return productCommentDtoList;
    }

}
