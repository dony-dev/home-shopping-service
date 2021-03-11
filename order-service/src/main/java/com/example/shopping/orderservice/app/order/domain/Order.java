//package com.example.shopping.orderservice.app.order.domain;
//
//import javax.persistence.*;
//import java.math.BigDecimal;
//import java.time.Instant;
//import java.util.List;
//
//@Table(name = "tb_order")
//@Entity
//public class Order {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.TABLE)
//    private Long orderId;
//    private String orderName;
//    private String orderDescription;
//    private String orderStatus;
//    private BigDecimal orderPrice;
//
//    private String orderGlobalId;
//
//    private Long productId;
//
//    public Order() {
//    }
//
//    public Order(Long orderId, String orderName, String orderDescription, String orderStatus, BigDecimal orderPrice, String orderGlobalId, Long productId) {
//        this.orderId = orderId;
//        this.orderName = orderName;
//        this.orderDescription = orderDescription;
//        this.orderStatus = orderStatus;
//        this.orderPrice = orderPrice;
//        this.orderGlobalId = orderGlobalId;
//        this.productId = productId;
//    }
//
//    public Long getOrderId() {
//        return orderId;
//    }
//
//    public void setOrderId(Long orderId) {
//        this.orderId = orderId;
//    }
//
//    public String getOrderName() {
//        return orderName;
//    }
//
//    public void setOrderName(String orderName) {
//        this.orderName = orderName;
//    }
//
//    public String getOrderDescription() {
//        return orderDescription;
//    }
//
//    public void setOrderDescription(String orderDescription) {
//        this.orderDescription = orderDescription;
//    }
//
//    public String getOrderStatus() {
//        return orderStatus;
//    }
//
//    public void setOrderStatus(String orderStatus) {
//        this.orderStatus = orderStatus;
//    }
//
//    public BigDecimal getOrderPrice() {
//        return orderPrice;
//    }
//
//    public void setOrderPrice(BigDecimal orderPrice) {
//        this.orderPrice = orderPrice;
//    }
//
//    public String getOrderGlobalId() {
//        return orderGlobalId;
//    }
//
//    public void setOrderGlobalId(String orderGlobalId) {
//        this.orderGlobalId = orderGlobalId;
//    }
//
//    public Long getProductId() {
//        return productId;
//    }
//
//    public void setProductId(Long productId) {
//        this.productId = productId;
//    }
//
//    @Override
//    public String toString() {
//        return "Order{" +
//                "orderId=" + orderId +
//                ", orderName='" + orderName + '\'' +
//                ", orderDescription='" + orderDescription + '\'' +
//                ", orderStatus='" + orderStatus + '\'' +
//                ", orderPrice=" + orderPrice +
//                ", orderGlobalId='" + orderGlobalId + '\'' +
//                ", productId=" + productId +
//                '}';
//    }
//}
package com.example.shopping.orderservice.app.order.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "tb_order")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long orderId;
    private String orderName;
    private String orderDescription;
    private BigDecimal orderPrice;

    private String productIds;

    public Order() {
    }

    public Order(Long orderId, String orderName, String orderDescription, BigDecimal orderPrice, String productIds) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.orderDescription = orderDescription;
        this.orderPrice = orderPrice;
        this.productIds = productIds;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getProductIds() {
        return productIds;
    }

    public void setProductIds(String productIds) {
        this.productIds = productIds;
    }
}
