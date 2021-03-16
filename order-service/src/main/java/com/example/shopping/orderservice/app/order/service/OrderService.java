package com.example.shopping.orderservice.app.order.service;

import com.example.shopping.orderservice.app.order.domain.Order;
import com.example.shopping.orderservice.app.order.repository.OrderRepository;
import com.example.shopping.orderservice.app.order.shared.OrderProductRequest;
import com.example.shopping.orderservice.app.order.shared.OrderProductResponse;
import com.example.shopping.orderservice.app.order.shared.OrderResponse;
import com.example.shopping.orderservice.app.order.shared.Product;
import com.example.shopping.orderservice.exception.OrderServiceException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final String productServiceId = "product-service";

    private final OrderRepository orderRepository;
    private Logger log = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order doOrder(Order order) {
        return orderRepository.save(order);
    }

//    public OrderProductResponse doRestOrder(OrderProductRequest orderProductRequest) {
//
//        Order order = orderProductRequest.getOrder();
//
//        Product product = orderProductRequest.getProduct();
////        order.setProductId(product.getProductId());
//        order.getProducts().clear();
//        List<Product> productList = Arrays.asList(product);
//        order.setProducts(productList);
//        product.setOrderId(order.getOrderId());
//
//        // rest call
////        Order orderResponse = restTemplate.postForObject("http://localhost:9091/api/orders/do-order", order, Order.class);
////        String message = orderResponse.getOrderStatus().equals("success") ? "order processing successful" : "there is a failure in order, product added to cart";
//        Product productResponse = restTemplate.postForObject("http://localhost:9091/api/products/order-product", product, Product.class);
////        String message = orderResponse.getOrderStatus().equals("success") ? "order processing successful" : "there is a failure in order, product added to cart";
//
//        orderRepository.save(order);
//
//        return new OrderProductResponse(product, order.getOrderPrice(), productResponse.getOrderGlobalId(), "success");
//    }

//    public OrderProductResponse doRestOrder(OrderProductRequest orderProductRequest) {
//
//        Order order = orderProductRequest.getOrder();
////        order.getProducts().clear();
//        List<Product> products = orderProductRequest.getProducts();
//
//        // rest call
//        List<Product> productList = products.stream().map(product -> {
//            Product productRes = restTemplate.getForObject("http://localhost:9091/api/products/product/" + product.getProductId(), Product.class);
//            return new Product(productRes.getProductId(), productRes.getProductPrice(), orderProductRequest.getOrder().getOrderId());
//        }).collect(Collectors.toList());
//
//        // get total price
//        BigDecimal orderPrice = products.stream().map(Product::getProductPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
//        order.setOrderPrice(orderPrice);
//
//        // collect product IDs
//        List<Long> productIds = productList.stream().map(product -> product.getProductId()).collect(Collectors.toList());
////        order.setProductIds(productIds);
//        orderRepository.save(order);
//
//        return new OrderProductResponse(order.getOrderId(), orderPrice, productList, "success");
//    }

    // save order
    @Transactional
    public OrderProductResponse doRestOrder(OrderProductRequest orderProductRequest) {

        /* */
//        List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances(productServiceId);
//        ServiceInstance serviceInstance = serviceInstanceList.get(0);

        /* */
        ServiceInstance serviceInstance = loadBalancerClient.choose("product-service");
        String baseUrl = "http://" + serviceInstance.getServiceId();
        log.info("baseUrl: {}", baseUrl);

        Order order = orderProductRequest.getOrder();
        List<Product> products = orderProductRequest.getProducts();
        log.info("orderProductRequest: {}", orderProductRequest.toString());

        // rest call
//        List<Product> productList = products.stream().map(product -> {
////            Product productRes = restTemplate.getForObject("http://localhost:9091/api/products/product/" + product.getProductId(), Product.class);
////            Product productRes = restTemplate.getForObject("http://product-service/api/products/product/" + product.getProductId(), Product.class);
//            Product productRes = restTemplate.getForObject(baseUrl + "/api/products/product/" + product.getProductId(), Product.class);
//            return new Product(productRes.getProductId(), productRes.getProductPrice());
//        }).collect(Collectors.toList());
        List<Product> productList = new ArrayList<>();
        for (Product product : products) {
            Product productRes = restTemplate.getForObject(baseUrl + "/api/products/product/" + product.getProductId(), Product.class);
            productList.add(productRes);
        }

        // get total price
        BigDecimal orderPrice = BigDecimal.valueOf(0);
//        orderPrice = productList.stream().map(Product::getProductPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderPrice = productList.stream().map(product -> {
            return product.getProductPrice().multiply(product.getProductPrice());
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("orderPrice: {}", orderPrice);
        order.setOrderPrice(orderPrice);

        // collect product IDs
        List<String> productIdList = productList.stream().map(product -> product.getProductId().toString()).collect(Collectors.toList());
        String productIds = String.join(",", productIdList);
        order.setProductIds(productIds);

        orderRepository.save(order);

        return new OrderProductResponse(order.getOrderId(), orderPrice, productList, "success");
    }

    // multiple productIds
    @Transactional
    public OrderProductResponse doRestOrderBulk(OrderProductRequest orderProductRequest) {

        /* */
//        List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances(productServiceId);
//        ServiceInstance serviceInstance = serviceInstanceList.get(0);

        /* */
        ServiceInstance serviceInstance = loadBalancerClient.choose("product-service");
        String baseUrl = "http://" + serviceInstance.getServiceId();
        log.info("baseUrl: {}", baseUrl);


        Order order = orderProductRequest.getOrder();
        List<Product> products = orderProductRequest.getProducts();
        log.info("orderProductRequest: {}", orderProductRequest.toString());
        log.info("products: {}", products);

        // rest call
        List<Long> productRequestIds = new ArrayList<>();
        for (Product product : products) {
            productRequestIds.add(product.getProductId());
        }

        /* */
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        String queryString = String.join(",", productRequestIds.toString());
        log.info("queryString: {}", queryString);

        URI uri = UriComponentsBuilder.fromUriString(baseUrl + "/api/products/product/eureka")
                .buildAndExpand(productRequestIds)
                .toUri();
        log.info("uri: {}", uri);

        uri = UriComponentsBuilder
                .fromUri(uri)
                .queryParam("productIds", productRequestIds)
                .build()
                .toUri();
        log.info("uri: {}", uri);

//        ResponseEntity<List> productListEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, List.class);
        ResponseEntity<List<Product>> productListEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Product>>() {
        });

        // convert value
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<Product> productList = objectMapper.convertValue(productListEntity.getBody(), new TypeReference<List<Product>>() {
        });

        // get total price
        BigDecimal orderPrice = BigDecimal.valueOf(0);
        for (Product product : productList) {
            int quantity = 0;
            for (Product productRequest : products) {
                if (productRequest.getProductId() == product.getProductId()) {
                    quantity = productRequest.getQuantity();
                    product.setQuantity(quantity);
                }
            }
            orderPrice = orderPrice.add(product.getProductPrice().multiply(BigDecimal.valueOf(quantity)));
        }
        log.info("orderPrice: {}", orderPrice);
        order.setOrderPrice(orderPrice);

        // collect product IDs
        List<String> productIdList = productList.stream().map(product -> product.getProductId().toString()).collect(Collectors.toList());
        String productIds = String.join(",", productIdList);
        order.setProductIds(productIds);

        orderRepository.save(order);

        return new OrderProductResponse(order.getOrderId(), orderPrice, productList, "success");
    }

//    // get order products by orderId
//    public OrderProductResponse getAllProduct(Long orderId) {
//        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException(""));
//        List<String> productIds = Arrays.asList(order.getProductIds().split(","));
//        log.info("productIds: {}", productIds);
//
//        // rest call
//        List<Product> productList = productIds.stream().map(id -> {
////            Product productRes = restTemplate.getForObject("http://localhost:9091/api/products/product/" + id, Product.class);
//            Product productRes = restTemplate.getForObject("http://product-service/api/products/product/" + id, Product.class);
//            return new Product(productRes.getProductId(), productRes.getProductPrice());
//        }).collect(Collectors.toList());
//        log.info("productList: {}", productList);
//
//        // get total price
//        BigDecimal orderPrice = BigDecimal.valueOf(0);
//        orderPrice = productList.stream().map(Product::getProductPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        return new OrderProductResponse(order.getOrderId(), orderPrice, productList, "success");
//    }
    // get order products by orderId
    public OrderProductResponse getAllProduct(Long orderId) {
        /* */
//        List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances(productServiceId);
//        ServiceInstance serviceInstance = serviceInstanceList.get(0);

        /* */
        ServiceInstance serviceInstance = loadBalancerClient.choose("product-service");
        String baseUrl = "http://" + serviceInstance.getServiceId();
        log.info("baseUrl: {}", baseUrl);

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderServiceException("order with orderId " + orderId + " not found"));
        List<String> productIds = Arrays.asList(order.getProductIds().split(","));
        log.info("productIds: {}", productIds);

        // rest call
        List<Product> productList = productIds.stream().map(id -> {
    //            Product productRes = restTemplate.getForObject("http://localhost:9091/api/products/product/" + id, Product.class);
//            Product productRes = restTemplate.getForObject("http://product-service/api/products/product/" + id, Product.class);
            Product productRes = restTemplate.getForObject(baseUrl + "/api/products/product/" + id, Product.class);
            return new Product(productRes.getProductId(), productRes.getProductPrice());
        }).collect(Collectors.toList());
        log.info("productList: {}", productList);

        // get total price
        BigDecimal orderPrice = BigDecimal.valueOf(0);
        orderPrice = productList.stream().map(Product::getProductPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        return new OrderProductResponse(order.getOrderId(), orderPrice, productList, "success");
    }

//    public OrderProductResponse updateRestOrder(OrderProductRequest orderProductRequest, Long orderId) {
//
//        // find old order
//        Order oldOrder = orderRepository.findById(orderId).orElseThrow(() -> new OrderServiceException("order with " + orderId + " not found"));
//
//        // get new order
//        Order newOrder = orderProductRequest.getOrder();
//        List<Product> products = orderProductRequest.getProducts();
//        log.info("orderProductRequest: {}", orderProductRequest.toString());
//
//        // rest call for product info
//        List<Product> productList = products.stream().map(product -> {
//            Product productRes = restTemplate.getForObject("http://localhost:9091/api/products/product/" + product.getProductId(), Product.class);
//            return new Product(productRes.getProductId(), productRes.getProductPrice());
//        }).collect(Collectors.toList());
//
//        // get total price
//        BigDecimal orderPrice = BigDecimal.valueOf(0);
//        orderPrice = productList.stream().map(Product::getProductPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
//        log.info("orderPrice: {}", orderPrice);
//        oldOrder.setOrderPrice(orderPrice);
//
//        // collect product IDs
//        List<String> productIdList = productList.stream().map(product -> product.getProductId().toString()).collect(Collectors.toList());
//        String productIds = String.join(",", productIdList);
//        oldOrder.setProductIds(productIds);
//
//        orderRepository.save(oldOrder);
//
//        return new OrderProductResponse(oldOrder.getOrderId(), orderPrice, productList, "success");
//    }

    // update
    @Transactional
    public OrderProductResponse updateRestOrder(OrderProductRequest orderProductRequest) {
        /* */
//        List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances(productServiceId);
//        ServiceInstance serviceInstance = serviceInstanceList.get(0);

        /* */
        ServiceInstance serviceInstance = loadBalancerClient.choose("product-service");
        String baseUrl = "http://" + serviceInstance.getServiceId();
        log.info("baseUrl: {}", baseUrl);

        // find old order
        Long orderId = orderProductRequest.getOrder().getOrderId();
        log.info("orderId: {}", orderId);
        Order oldOrder = orderRepository.findById(orderId).orElseThrow(() -> new OrderServiceException("order with " + orderId + " not found"));

        // get new order
        Order newOrder = orderProductRequest.getOrder();
        List<Product> products = orderProductRequest.getProducts();
        log.info("orderProductRequest: {}", orderProductRequest.toString());

        // rest call for product info
        List<Product> productList = products.stream().map(product -> {
//            Product productRes = restTemplate.getForObject("http://localhost:9091/api/products/product/" + product.getProductId(), Product.class);
//            Product productRes = restTemplate.getForObject("http://product-service/api/products/product/" + product.getProductId(), Product.class);
            Product productRes = restTemplate.getForObject(baseUrl + "/api/products/product/" + product.getProductId(), Product.class);
            return new Product(productRes.getProductId(), productRes.getProductPrice());
        }).collect(Collectors.toList());

        // get total price
        BigDecimal orderPrice = BigDecimal.valueOf(0);
        orderPrice = productList.stream().map(Product::getProductPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("orderPrice: {}", orderPrice);
        oldOrder.setOrderPrice(orderPrice);

        // collect product IDs
        List<String> productIdList = productList.stream().map(product -> product.getProductId().toString()).collect(Collectors.toList());
        String productIds = String.join(",", productIdList);
        oldOrder.setProductIds(productIds);

        orderRepository.save(oldOrder);

        return new OrderProductResponse(oldOrder.getOrderId(), orderPrice, productList, "success");
    }

    // save json string
    public OrderProductResponse saveJsonStringOrder(OrderProductRequest orderProductRequest) {

        return null;
    }

    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderServiceException("order with " + orderId + " not found"));
        orderRepository.delete(order);
    }

    public List<OrderResponse> findAllOrders() {
        List<Order> orderList = orderRepository.findAll();
        List<OrderResponse> orderResponseList = new ArrayList<>();
        for (Order order : orderList) {
            OrderResponse orderResponse = new OrderResponse(order.getOrderId(), order.getOrderName(), order.getOrderDescription(), order.getOrderPrice(), order.getProductIds());
            orderResponseList.add(orderResponse);
        }
        return orderResponseList;
    }

    public Page<OrderResponse> findAllByPage(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Order> orderPage = orderRepository.findAll(pageRequest);

        List<OrderResponse> orderResponseList = new ArrayList<>();
        for (Order order : orderPage) {
            OrderResponse orderResponse = new OrderResponse(order.getOrderId(), order.getOrderName(), order.getOrderDescription(), order.getOrderPrice(), order.getProductIds());
            orderResponseList.add(orderResponse);
        }
        return new PageImpl<>(orderResponseList, pageRequest, orderPage.getTotalElements());
    }

}
