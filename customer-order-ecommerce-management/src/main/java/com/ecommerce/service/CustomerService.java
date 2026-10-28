package com.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.entity.CustomerEntity;
import com.ecommerce.entity.OrderEntity;
import com.ecommerce.exception.CustomerAlreadyExistsException;
import com.ecommerce.exception.CustomerNotExistException;
import com.ecommerce.repository.ICustomerRepository;
import com.ecommerce.request.CustomerRequest;
import com.ecommerce.response.CustomerOrderResponse;
import com.ecommerce.response.CustomerResponse;
import com.ecommerce.response.OrderResponse;

@Service
public class CustomerService
{
	@Autowired
	ICustomerRepository customerRepository;

	public CustomerResponse registerCustomer(CustomerRequest customerRequest)
	{
		System.out.println("CustomerService.registerCustomer()::::::::::::::::::::::::::::::");
		
		//TODO: check name and email if both matches --> discard the registration ==> implemented on 27 August
		String name = customerRequest.getName();
		String email = customerRequest.getEmail();
		
		if(customerRepository.existsByNameAndEmail(name, email)) {
			throw new CustomerAlreadyExistsException("Customer Already Exists With This Name = '" + name + "' And Email = '" + email + "'");
		}

		CustomerEntity customerEntity = new CustomerEntity();

		customerEntity.setName(name);
		customerEntity.setEmail(email);

		CustomerEntity savedCustomer = customerRepository.save(customerEntity);

		CustomerResponse customerResponse = new CustomerResponse();

		customerResponse.setId(savedCustomer.getId());
		customerResponse.setName(savedCustomer.getName());
		customerResponse.setEmail(savedCustomer.getEmail());

		return customerResponse;

	}
	
	

	// N+1 problem
	public List<CustomerOrderResponse> getAllCustomersWithOrders()
	{
		// 1 query for all customers
		Iterable<CustomerEntity> customers = customerRepository.findAll();

		List<CustomerOrderResponse> list = new ArrayList<CustomerOrderResponse>();

		for (CustomerEntity customer : customers)
		{
			CustomerOrderResponse customerResponse = new CustomerOrderResponse();
			customerResponse.setId(customer.getId());
			customerResponse.setName(customer.getName());
			customerResponse.setEmail(customer.getEmail());

			// n+1 problem --> 1 query for fetching parent records and 1 query for each parent(suppose N parent) then N + 1 --> Queries

			List<OrderEntity> orders = customer.getOrders(); // hitting child entity --> lazy loading(when requested)

			customerResponse.setTotalOrders(orders.size());

			List<OrderResponse> orderss = new ArrayList<OrderResponse>();
			for (OrderEntity entity : orders)
			{

				OrderResponse orderResponse = new OrderResponse();
				orderResponse.setCustomerId(customerResponse.getId());
				orderResponse.setOrderNumber(entity.getOrderNumber());
				orderResponse.setOrderDate(entity.getOrderDate());
				orderResponse.setStatus(entity.getStatus());
				orderResponse.setTotalAmount(entity.getTotalAmount());

				orderss.add(orderResponse);

			}
			customerResponse.setOrders(orderss);

			list.add(customerResponse);
		}

		return list;
	}

	// solving N+1 problem
	public List<CustomerOrderResponse> getAllCustomersWithOrdersNew()
	{
		// 1 query for all customers
		List<CustomerEntity> customers = customerRepository.readAllCustomers();

		List<CustomerOrderResponse> list = new ArrayList<CustomerOrderResponse>();

		for (CustomerEntity customer : customers)
		{
			CustomerOrderResponse customerResponse = new CustomerOrderResponse();
			customerResponse.setId(customer.getId());
			customerResponse.setName(customer.getName());
			customerResponse.setEmail(customer.getEmail());

			List<OrderEntity> orders = customer.getOrders();

			customerResponse.setTotalOrders(orders.size());

			List<OrderResponse> orderss = new ArrayList<OrderResponse>();
			for (OrderEntity entity : orders)
			{

				OrderResponse orderResponse = new OrderResponse();
				orderResponse.setCustomerId(customerResponse.getId());
				orderResponse.setOrderNumber(entity.getOrderNumber());
				orderResponse.setOrderDate(entity.getOrderDate());
				orderResponse.setStatus(entity.getStatus());
				orderResponse.setTotalAmount(entity.getTotalAmount());

				orderss.add(orderResponse);

			}
			customerResponse.setOrders(orderss);

			list.add(customerResponse);
		}

		return list;
	}

	public List<CustomerResponse> getAllCustomers()
	{
		Iterable<CustomerEntity> allCustomers = customerRepository.findAll();
		List<CustomerResponse> customers = new ArrayList<CustomerResponse>();
		for (CustomerEntity customerEntity : allCustomers)
		{
			CustomerResponse customerResponse = new CustomerResponse();
			customerResponse.setId(customerEntity.getId());
			customerResponse.setEmail(customerEntity.getEmail());
			customerResponse.setName(customerEntity.getName());

			customers.add(customerResponse);
		}
		return customers;
	}

	public CustomerResponse readCustomerById(long id)
	{
		Optional<CustomerEntity> optionalCustomer = customerRepository.findById(id);
		CustomerEntity customerEntity;
		
		if (optionalCustomer.isEmpty())
		{
			throw new CustomerNotExistException("No Customer Found With ID=" + id);
		} else
		{
			customerEntity = optionalCustomer.get();
		}

		CustomerResponse customerResponse = new CustomerResponse();
		customerResponse.setId(customerEntity.getId());
		customerResponse.setEmail(customerEntity.getEmail());
		customerResponse.setName(customerEntity.getName());

		return customerResponse;
	}
	
	public CustomerOrderResponse getCustomerOrdersById(long id) {
		
//		Optional<CustomerEntity> optionalCustomer = customerRepository.findById(id);	//1 query for customer 
//		CustomerEntity customerEntity ;
//		if(optionalCustomer.isEmpty()) {
//			throw new CustomerNotExistException("No Orders: Customer With This Id="+ id + " Not Exists");
//		}else {
//			customerEntity = optionalCustomer.get();
//		}
		
		CustomerEntity customerEntity = customerRepository.getOrdersByCustomer(id);	//only 1 query is fired
		
		if(customerEntity == null) {
			throw new CustomerNotExistException("No Orders: Customer With This Id="+ id + " Not Exists");
		}
				
		CustomerOrderResponse customerOrderResponse = new CustomerOrderResponse();
		customerOrderResponse.setId(customerEntity.getId());
		customerOrderResponse.setName(customerEntity.getName());
		customerOrderResponse.setEmail(customerEntity.getEmail());
		
		List<OrderEntity> orderEntities = customerEntity.getOrders();
		
		List<OrderResponse> orders = new ArrayList<OrderResponse>();
		
		for(OrderEntity entity: orderEntities) {
			OrderResponse orderResponse = new OrderResponse();
			orderResponse.setCustomerId(entity.getCustomer().getId());
			orderResponse.setOrderNumber(entity.getOrderNumber());
			orderResponse.setOrderDate(entity.getOrderDate());
			orderResponse.setTotalAmount(entity.getTotalAmount());
			orderResponse.setStatus(entity.getStatus());
			
			orders.add(orderResponse);
			
			customerOrderResponse.setOrders(orders);
		}
		customerOrderResponse.setTotalOrders(customerEntity.getOrders().size());	//1 query for orders 
		
		return customerOrderResponse;
		
	}
	
		//testing EAGER
		public void findAllCustomers() {
			System.out.println("CustomerService.findAllCustomers():::::::::::::::::START");
			Iterable<CustomerEntity> customers = customerRepository.findAll();
			
			for(CustomerEntity entity: customers) {	//here we don't want to load customers Orders but due to Eager Fetch type, queries to load orders fired as well 
				System.out.println(entity.getEmail());
				System.out.println(entity.getId());
				System.out.println(entity.getName());
				
			}
			
			System.out.println("CustomerService.findAllCustomers():::::::::::::::::END");
			
		}
		
		@Transactional 		///TransactionRequiredException: No active transaction for update or delete query
		public String updateCustomerEmail(long id, String newEmail) {
			int rowAffected = customerRepository.updateEmail(id, newEmail);
			
			if(rowAffected == 0) {
				throw new CustomerNotExistException("Customer Not Found with id = " + id);
			}
			
			CustomerEntity customerEntity = customerRepository.findById(id).get();
			String name = customerEntity.getName();
			String changedEmail = customerEntity.getEmail();

			
			String message = "Email Updated Successfully \n "
					+ "Customer [ Id: "+ id + ", Name : " + name + ", New-Email : " + changedEmail + "]";
			
			return message;
		}

}
