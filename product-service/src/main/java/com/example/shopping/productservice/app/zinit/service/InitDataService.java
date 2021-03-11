package com.example.shopping.productservice.app.zinit.service;

import com.example.shopping.productservice.app.category.dto.ProductCategoryRequest;
import com.example.shopping.productservice.app.category.service.ProductCategoryService;
import com.example.shopping.productservice.app.product.dto.ProductCommentDto;
import com.example.shopping.productservice.app.product.dto.ProductRequest;
import com.example.shopping.productservice.app.product.service.ProductCommentService;
import com.example.shopping.productservice.app.product.service.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InitDataService {

    private Logger log = LoggerFactory.getLogger(InitDataService.class);

    private final String fileLocation = "config/init_product_service.json";
    private final ProductCategoryService productCategoryService;
    private final ProductService productService;
    private final ProductCommentService productCommentService;

    public InitDataService(ProductCategoryService productCategoryService, ProductService productService, ProductCommentService productCommentService) {
        this.productCategoryService = productCategoryService;
        this.productService = productService;
        this.productCommentService = productCommentService;
    }

    @Transactional
    public void initProductCategoryData() throws IOException {
        InputStream is = getStreamFromResource(fileLocation);
        JsonNode categoryNode = getCategoryNode(is);

        List<ProductCategoryRequest> productCategoryRequestList = getCategoryFromNode(categoryNode);
        log.info("productCategoryRequestList.size: {}", productCategoryRequestList.size());
        productCategoryRequestList.stream().forEach(productCategoryRequest -> {
            productCategoryService.createProductCategory(productCategoryRequest);
        });
    }

    @Transactional
    public void initProductData() throws IOException {
        InputStream is = getStreamFromResource(fileLocation);
        JsonNode productNode = getProductNode(is);

        List<ProductRequest> productRequestList = getProductFromNode(productNode);
        log.info("productRequestList.size: {}", productRequestList.size());
        productRequestList.stream().forEach(productRequest -> {
            productService.createProduct(productRequest);
        });
    }

    @Transactional
    public void initProductCommentData() throws IOException {
        InputStream is = getStreamFromResource(fileLocation);
        JsonNode productCommentNode = getProductCommentNode(is);

        List<ProductCommentDto> productCommentDtoList = getProductCommentFromNode(productCommentNode);
        log.info("productCommentDtoList.size: {}", productCommentDtoList.size());
        productCommentDtoList.stream().forEach(productCommentDto -> {
            productCommentService.createProductComment(productCommentDto);
        });
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


}
