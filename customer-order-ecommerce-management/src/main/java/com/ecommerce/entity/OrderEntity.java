package com.ecommerce.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "orderss")
public class OrderEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String orderNumber;
	private LocalDate orderDate;
	private double totalAmount;
	private String status;
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	private CustomerEntity customer;
	

	public OrderEntity() {
		this.orderDate = LocalDate.now();
	}
	
	public long getId()
	{
		return id;
	}

	public String getOrderNumber()
	{
		return orderNumber;
	}

	public LocalDate getOrderDate()
	{
		return orderDate;
	}

	public double getTotalAmount()
	{
		return totalAmount;
	}

	public String getStatus()
	{
		return status;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public void setOrderNumber(String orderNumber)
	{
		this.orderNumber = orderNumber;
	}

	public void setOrderDate(LocalDate orderDate)
	{
		this.orderDate = orderDate;
	}

	public void setTotalAmount(double totalAmount)
	{
		this.totalAmount = totalAmount;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public CustomerEntity getCustomer()
	{
		return customer;
	}

	public void setCustomer(CustomerEntity customer)
	{
		this.customer = customer;
	}


}
