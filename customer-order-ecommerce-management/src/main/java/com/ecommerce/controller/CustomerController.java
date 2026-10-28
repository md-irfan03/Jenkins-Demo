package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.request.CustomerRequest;
import com.ecommerce.response.CustomerOrderResponse;
import com.ecommerce.response.CustomerResponse;
import com.ecommerce.service.CustomerService;

@RestController
@RequestMapping("/ecommerce/customer")
public class CustomerController
{

	@Autowired
	CustomerService customerService;

	@PostMapping("/create")
	public ResponseEntity<CustomerResponse> registerNewCustomer(@RequestBody CustomerRequest customerRequest)
	{
		System.out.println("CustomerController.registerNewCustomer():::::::::::::::::::::::::");

		CustomerResponse response = customerService.registerCustomer(customerRequest);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/showAllCustomersWithOrders/n+1")
	public List<CustomerOrderResponse> getAllCustomersWithOrders()
	{
		System.out.println("CustomerController.getAllCustomers()");

		return customerService.getAllCustomersWithOrders();
	}

	@GetMapping("/showAllCustomersWithOrders")
	public List<CustomerOrderResponse> getAllCustomersWithOrdersNew()
	{
		System.out.println("CustomerController.getAllCustomers()");

		return customerService.getAllCustomersWithOrdersNew();
	}

	@GetMapping("/showAllCustomers")
	public List<CustomerResponse> viewAllCustomers()
	{
		return customerService.getAllCustomers();
	}

	@GetMapping("/searchByid")
	public CustomerResponse getCustomerById(@RequestParam long id)
	{
		return customerService.readCustomerById(id);
	}

	@GetMapping("/showAll/eager")
	public String showAllCustomers()
	{
		System.out.println("CustomerController.showAllCustomers()");
		customerService.findAllCustomers();
		return "EAGER";
	}

	@GetMapping("/viewAllOrdersByCustomer/{id}")
	public CustomerOrderResponse viewAllOrdersOfCustomer(@PathVariable long id)
	{
		return customerService.getCustomerOrdersById(id);
	}

	@PatchMapping("/update/{id}/email")
	public String updateEmail(@PathVariable("id") long customer_id, @RequestParam("newEmail") String email)
	{
		return customerService.updateCustomerEmail(customer_id, email);

	}

}
