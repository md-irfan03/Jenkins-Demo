package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.request.OrderRequest;
import com.ecommerce.response.OrderResponse;
import com.ecommerce.service.OrderService;

@RestController
@RequestMapping("/ecommerce/customer/order")
public class OrderController
{
	@Autowired
	OrderService orderService;
	
	@PostMapping("/create")
	public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest){
		System.out.println("OrderController.createOrder()");
		
		OrderResponse orderResponse = orderService.acceptOrder(orderRequest);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
	}
	
	@GetMapping("/searchByCustomerid/{id}")
	public List<OrderResponse> getOrdersByCustomer(@PathVariable long id){
		return orderService.getAllOrders(id);
	}

}
