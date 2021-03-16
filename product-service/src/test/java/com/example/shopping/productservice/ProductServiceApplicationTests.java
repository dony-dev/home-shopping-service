package com.example.shopping.productservice;

import com.example.shopping.productservice.app.product.domain.Product;
import com.example.shopping.productservice.app.product.repository.ProductRepository;
import com.example.shopping.productservice.app.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
class ProductServiceApplicationTests {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ProductService productService;

	@Test
	void contextLoads() {
	}

	@Test
	public void calTests() {
		calForLoop();
		calStream();
	}

	public void calForLoop() {
		List<Product> productList = productService.findAll();
		BigDecimal totalPrice = BigDecimal.valueOf(0);
		
		Long startNanoTime = System.nanoTime();
		totalPrice = productList.stream().map(Product::getProductPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
		Long time = System.nanoTime() - startNanoTime;

		System.out.println("totalPrice: " + totalPrice);	// 6221880.00
		System.out.println("time: " + time);				// 2528600
	}

	public void calStream() {
		List<Product> productList = productService.findAll();
		BigDecimal totalPrice = BigDecimal.valueOf(0);
		
		Long startNanoTime = System.nanoTime();
		for (Product product : productList) {
			totalPrice = totalPrice.add(product.getProductPrice());
		}
		Long time = System.nanoTime() - startNanoTime;

		System.out.println("totalPrice: " + totalPrice);	// 6221880.00
		System.out.println("time: " + time);				// 485900
	}

}
