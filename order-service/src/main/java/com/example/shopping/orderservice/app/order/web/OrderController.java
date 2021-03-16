package com.example.shopping.orderservice.app.order.web;

import com.example.shopping.orderservice.app.order.domain.Order;
import com.example.shopping.orderservice.app.order.service.OrderService;
import com.example.shopping.orderservice.app.order.shared.OrderProductRequest;
import com.example.shopping.orderservice.app.order.shared.OrderProductResponse;
import com.example.shopping.orderservice.app.order.shared.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/test")
    public ResponseEntity<String> testApiCall() {
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    @PostMapping(value = "/do-order")
    public ResponseEntity<OrderProductResponse> doOrderProduct(@RequestBody OrderProductRequest orderProductRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.doRestOrder(orderProductRequest));
    }
//    @PostMapping(value = "/do-order")
//    public ResponseEntity<Order> doOrder(@RequestBody Order order) {
//        return ResponseEntity.status(HttpStatus.OK).body(orderService.doOrder(order));
//    }

    @PutMapping(value = "/update-order")
    public ResponseEntity<OrderProductResponse> updateOrderProduct(@RequestBody OrderProductRequest orderProductRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.updateRestOrder(orderProductRequest));
    }
//    @PutMapping(value = "/update-order")
//    public ResponseEntity<OrderProductResponse> updateOrderProduct(@RequestBody OrderProductRequest orderProductRequest,
//                                                                   @PathVariable("orderId") Long orderId) {
//        return ResponseEntity.status(HttpStatus.OK).body(orderService.updateRestOrder(orderProductRequest, orderId));
//    }

    @GetMapping(value = "/all/{orderId}")
    public ResponseEntity<OrderProductResponse> getAllProductsByOrderId(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllProduct(orderId));
    }

    @DeleteMapping(value = "/delete/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable("orderId") Long orderId) {
        orderService.deleteOrder(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Get multiple products
    @PostMapping(value = "/do-multiple-order")
    public ResponseEntity<OrderProductResponse> doMultipleOrderProduct(@RequestBody OrderProductRequest orderProductRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.doRestOrderBulk(orderProductRequest));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAllOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findAllOrders());
    }

    @GetMapping(value = "/page")
    public Page<OrderResponse> getOrdersByPagination(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                    @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return orderService.findAllByPage(page, size);
    }

}
