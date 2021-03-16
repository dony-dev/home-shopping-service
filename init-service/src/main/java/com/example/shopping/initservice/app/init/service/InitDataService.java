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
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InitDataService {

    private final String fileLocation = "config/init_product_service.json";
    private final String productServiceUrl = "http://PRODUCT-SERVICE";
    private Logger log = LoggerFactory.getLogger(InitDataService.class);
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private LoadBalancerClient loadBalancer;

    public void initProductCategoryData() throws IOException {
        InputStream is = getStreamFromResource(fileLocation);
        JsonNode categoryNode = getCategoryNode(is);

        List<ProductCategoryRequest> productCategoryRequestList = getCategoryFromNode(categoryNode);
        log.info("productCategoryRequestList.size: {}", productCategoryRequestList.size());

        Long startTime = System.nanoTime();
        // rest call
        List<ResponseEntity<String>> resList = productCategoryRequestList.stream().map(productCategoryRequest -> {
//            ResponseEntity<String> res = restTemplate.postForEntity("http://localhost:9091/api/categories/category/add", productCategoryRequest, String.class);
            ResponseEntity<String> res = restTemplate.postForEntity(productServiceUrl + "/api/categories/category/add", productCategoryRequest, String.class);
            return res;
        }).collect(Collectors.toList());
        Long result = System.nanoTime() - startTime;
        log.info("nano time 1: {}", result);
//        log.info("res: {}", resList.toString());

        Long startTime2 = System.nanoTime();
        List<ResponseEntity<String>> resList2 = new ArrayList<>();
        for (ProductCategoryRequest productCategoryRequest : productCategoryRequestList) {
            restTemplate.postForEntity(productServiceUrl + "/api/categories/category/add", productCategoryRequest, String.class);
        }
        result = System.nanoTime();
        log.info("nano time 2: {}", result);
    }

    //    public void initProductData() throws IOException {
//        InputStream is = getStreamFromResource(fileLocation);
//        JsonNode productNode = getProductNode(is);
//
//        List<ProductRequest> productRequestList = getProductFromNode(productNode);
//        log.info("productRequestList.size: {}", productRequestList.size());
//
//        // rest call
//        List<ResponseEntity<String>> resList = productRequestList.stream().map(productRequest -> {
////            ResponseEntity<String> res = restTemplate.postForEntity("http://localhost:9091/api/products/product/create", productRequest, String.class);
//            ResponseEntity<String> res = restTemplate.postForEntity("http://PRODUCT-SERVICE/api/products/product/create", productRequest, String.class);
//            return res;
//        }).collect(Collectors.toList());
////        log.info("res: {}", resList.toString());
//    }
    public void initProductData() throws IOException {
        try (InputStream is = getStreamFromResource(fileLocation)) {
            JsonNode productNode = getProductNode(is);

            List<ProductRequest> productRequestList = getProductFromNode(productNode);
            log.info("productRequestList.size: {}", productRequestList.size());

            // rest call
            List<ResponseEntity<String>> resList = productRequestList.stream().map(productRequest -> {
//            ResponseEntity<String> res = restTemplate.postForEntity("http://localhost:9091/api/products/product/create", productRequest, String.class);
                ResponseEntity<String> res = restTemplate.postForEntity(productServiceUrl + "/api/products/product/create", productRequest, String.class);
                return res;
            }).collect(Collectors.toList());
        }
    }

//    public void initProductCommentData() throws IOException {
//        InputStream is = getStreamFromResource(fileLocation);
//        JsonNode productCommentNode = getProductCommentNode(is);
//
//        List<ProductCommentDto> productCommentDtoList = getProductCommentFromNode(productCommentNode);
//        log.info("productCommentDtoList.size: {}", productCommentDtoList.size());
//
//        // rest call
//        List<ResponseEntity<String>> resList = productCommentDtoList.stream().map(productCommentDto -> {
////            ResponseEntity<String> res = restTemplate.postForEntity("http://localhost:9091/api/products/product/create", productCategoryRequest, String.class);
//            ResponseEntity<String> res = restTemplate.postForEntity("http://PRODUCT-SERVICE/api/product/comments/comment", productCommentDto, String.class);
//            return res;
//        }).collect(Collectors.toList());
////        log.info("res: {}", resList.toString());
//    }

    public void initProductCommentData() {
        JsonNode productCommentNode = null;
        try (InputStream is = getStreamFromResource(fileLocation)) {
            productCommentNode = getProductCommentNode(is);

            List<ProductCommentDto> productCommentDtoList = getProductCommentFromNode(productCommentNode);
            log.info("productCommentDtoList.size: {}", productCommentDtoList.size());

            // rest call
            List<ResponseEntity<String>> resList = productCommentDtoList.stream().map(productCommentDto -> {
//            ResponseEntity<String> res = restTemplate.postForEntity("http://localhost:9091/api/products/product/create", productCategoryRequest, String.class);
                ResponseEntity<String> res = restTemplate.postForEntity(productServiceUrl + "/api/product/comments/comment", productCommentDto, String.class);
                return res;
            }).collect(Collectors.toList());
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    /* */
    public void initProductCommentDataDiscovery() {

        /* */
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances("product-service");
        ServiceInstance serviceInstance = serviceInstances.get(0);
//        String baseUrl = serviceInstance.getUri().toString();
        String baseUrl = "http://" + serviceInstance.getServiceId();
        log.info("serviceInstance.getInstanceId(): {}", serviceInstance.getInstanceId());
        log.info("serviceInstance.getServiceId(): {}", serviceInstance.getServiceId());
        log.info("serviceInstance.getHost(): {}", serviceInstance.getHost());
        log.info("baseUrl: {}", baseUrl);

        JsonNode productCommentNode = null;
        try (InputStream is = getStreamFromResource(fileLocation)) {
            productCommentNode = getProductCommentNode(is);

            List<ProductCommentDto> productCommentDtoList = getProductCommentFromNode(productCommentNode);
            log.info("productCommentDtoList.size: {}", productCommentDtoList.size());

            // rest call
            try {
                for (ProductCommentDto productCommentDto : productCommentDtoList) {
                    restTemplate.postForEntity(baseUrl + "/api/product/comments/comment", productCommentDto, String.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    /* */
    public void initProductCommentDataLoadBalanced() {

        /* */
        ServiceInstance serviceInstance = loadBalancer.choose("product-service");
        log.info("serviceInstance.getUri(): {}", serviceInstance.getUri());

        String baseUrl = "http://" + serviceInstance.getServiceId();
        log.info("serviceInstance.getInstanceId(): {}", serviceInstance.getInstanceId());
        log.info("serviceInstance.getServiceId(): {}", serviceInstance.getServiceId());
        log.info("serviceInstance.getHost(): {}", serviceInstance.getHost());
        log.info("baseUrl: {}", baseUrl);

        JsonNode productCommentNode = null;
        try (InputStream is = getStreamFromResource(fileLocation)) {
            productCommentNode = getProductCommentNode(is);

            List<ProductCommentDto> productCommentDtoList = getProductCommentFromNode(productCommentNode);
            log.info("productCommentDtoList.size: {}", productCommentDtoList.size());

            // rest call
            try {
                for (ProductCommentDto productCommentDto : productCommentDtoList) {
                    restTemplate.postForEntity(baseUrl + "/api/product/comments/comment", productCommentDto, String.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (IOException io) {
            io.printStackTrace();
        }
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
//    private InputStream getStreamFromResource(String fileLocation) {
//        ClassLoader classLoader = InitDataService.class.getClassLoader();
//        try (InputStream inputStream = classLoader.getResourceAsStream(fileLocation)) {
//            if (inputStream == null) {
//                throw new IllegalArgumentException("init data file \"" + fileLocation + "\" not found.");
//            } else {
//                return inputStream;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new IllegalArgumentException("init data file \"" + fileLocation + "\" not found.");
//        }
//    }

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

    //    private JsonNode getProductNode(InputStream is) throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        DataInputStream dis = new DataInputStream(is);
//        JsonNode productNode = objectMapper.readTree(dis).path("product");
//        log.info("productNode -------------- \n{}\n------------------------", productNode.toPrettyString());
//
//        return productNode;
//    }
    private JsonNode getProductNode(InputStream is) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode productNode = null;
        try (DataInputStream dis = new DataInputStream(is)) {
            productNode = objectMapper.readTree(dis).path("product");
            log.info("productNode -------------- \n{}\n------------------------", productNode.toPrettyString());
        } catch (IOException io) {
            io.printStackTrace();
        }
        return productNode;
    }

    private List<ProductRequest> getProductFromNode(JsonNode productNode) {
        ObjectMapper objectMapper = new ObjectMapper();
//        List<ProductRequest> productRequestList = objectMapper.convertValue(productNode, new TypeReference<List<ProductRequest>>() {
//        }).stream().collect(Collectors.toList());
        List<ProductRequest> productRequestList = objectMapper.convertValue(productNode, new TypeReference<List<ProductRequest>>() {
        }).stream().collect(Collectors.toList());
        log.info("productRequestList: {}", productRequestList.toString());

        return productRequestList;
    }

//    private JsonNode getProductCommentNode(InputStream is) throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        DataInputStream dis = new DataInputStream(is);
//        JsonNode productCommentNode = objectMapper.readTree(dis).path("productComment");
//        log.info("productCommentNode -------------- \n{}\n------------------------", productCommentNode.toPrettyString());
//
//        return productCommentNode;
//    }

    private JsonNode getProductCommentNode(InputStream is) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try (DataInputStream dis = new DataInputStream(is)) {
            JsonNode productCommentNode = objectMapper.readTree(dis).path("productComment");
            log.info("productCommentNode -------------- \n{}\n------------------------", productCommentNode.toPrettyString());

            return productCommentNode;
        }
    }

    private List<ProductCommentDto> getProductCommentFromNode(JsonNode productCommentNode) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductCommentDto> productCommentDtoList = objectMapper.convertValue(productCommentNode, new TypeReference<List<ProductCommentDto>>() {
        }).stream().collect(Collectors.toList());
        log.info("productCommentDtoList: {}", productCommentDtoList.toString());

        return productCommentDtoList;
    }

    /* */
    public String invokeProductService() {
        String url = productServiceUrl + "/api/products/page";
        return restTemplate.getForObject(url, String.class);
    }
}
