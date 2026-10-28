package com.ecommerce.response;

import java.time.LocalDate;

public class OrderResponse
{
	private long customerId;
	private String orderNumber;
	private LocalDate orderDate;
	private double totalAmount;
	private String status;

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

	public long getCustomerId()
	{
		return customerId;
	}

	public void setCustomerId(long customerId)
	{
		this.customerId = customerId;
	}

}
