package com.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.constant.OrderStatus;
import com.ecommerce.entity.CustomerEntity;
import com.ecommerce.entity.OrderEntity;
import com.ecommerce.exception.CustomerNotExistException;
import com.ecommerce.exception.OrderNotExistException;
import com.ecommerce.repository.ICustomerRepository;
import com.ecommerce.repository.IOrderRepository;
import com.ecommerce.request.OrderRequest;
import com.ecommerce.response.OrderResponse;

@Service
public class OrderService
{
	@Autowired
	IOrderRepository orderRepository;

	@Autowired
	ICustomerRepository customerRepository;
	

	public OrderResponse acceptOrder(OrderRequest orderRequest)
	{
		System.out.println("OrderService.acceptOrder()::::::::::::::::");

		long customerId = orderRequest.getCustomerId();
		
		CustomerEntity customerEntity;

		Optional<CustomerEntity> optionalEntity = customerRepository.findById(customerId);
		if (optionalEntity.isEmpty())
		{
			throw new CustomerNotExistException("Not a Registered Customer, Please Register Before Placing Order");
		}else {
			customerEntity = optionalEntity.get();
		}

		OrderEntity orderEntity = new OrderEntity();
		
		orderEntity.setCustomer(customerEntity);	//mapping
		orderEntity.setOrderNumber(generateOrderNumber());
		orderEntity.setTotalAmount(generateAmount());
		orderEntity.setStatus(OrderStatus.randomStatus().name());
		
		OrderEntity savedOrder = orderRepository.save(orderEntity);
		
		OrderResponse orderResponse = new OrderResponse();
		orderResponse.setCustomerId(customerEntity.getId());
		orderResponse.setOrderDate(savedOrder.getOrderDate());
		orderResponse.setOrderNumber(savedOrder.getOrderNumber());
		orderResponse.setStatus(savedOrder.getStatus());
		orderResponse.setTotalAmount(savedOrder.getTotalAmount());
		
		return orderResponse;

	}
	
	public List<OrderResponse> getAllOrders(long id){
		List<OrderEntity> orderEntities = orderRepository.getAllOrdersByCustomer(id);
		
		if(orderEntities.isEmpty()) {
			throw new OrderNotExistException("No Order Records for CustomerWithId=" + id);
		}
		
		List<OrderResponse> orders = new ArrayList<OrderResponse>();
		
		for(OrderEntity entity: orderEntities) {
			OrderResponse orderResponse = new OrderResponse();
			orderResponse.setCustomerId(entity.getCustomer().getId());
			orderResponse.setOrderNumber(entity.getOrderNumber());
			orderResponse.setOrderDate(entity.getOrderDate());
			orderResponse.setTotalAmount(entity.getTotalAmount());
			orderResponse.setStatus(entity.getStatus());
			
			orders.add(orderResponse);
		}
		return orders;
		
	}

	private String generateOrderNumber()
	{
		Random random = new Random();

		int number = 10000 + random.nextInt(90000);
		return "ORDER-" + number;
	}

	private double generateAmount()
	{
		Random random = new Random();

		double amt = 100 + random.nextDouble(9000);
		return amt;
	}

}
