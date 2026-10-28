package com.ecommerce.response;

import java.util.List;

public class CustomerOrderResponse
{
	private long id;
	private String name;
	private String email;	
	private List<OrderResponse> orders;
	private int totalOrders;

	public long getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public String getEmail()
	{
		return email;
	}

	

	public int getTotalOrders()
	{
		return totalOrders;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}


	public void setTotalOrders(int totalOrders)
	{
		this.totalOrders = totalOrders;
	}

	public List<OrderResponse> getOrders()
	{
		return orders;
	}

	public void setOrders(List<OrderResponse> orders)
	{
		this.orders = orders;
	}
	
	
}
