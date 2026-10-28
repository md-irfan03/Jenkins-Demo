package com.ecommerce.request;

public class OrderRequest
{
	private long customerId;
	
	private long productId;
	private int quantity;
	private String shippingAddress;

	public long getCustomerId()
	{
		return customerId;
	}

	public void setCustomerId(long customerId)
	{
		this.customerId = customerId;
	}

	public long getProductId()
	{
		return productId;
	}

	public int getQuantity()
	{
		return quantity;
	}

	public String getShippingAddress()
	{
		return shippingAddress;
	}

	public void setProductId(long productId)
	{
		this.productId = productId;
	}

	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}

	public void setShippingAddress(String shippingAddress)
	{
		this.shippingAddress = shippingAddress;
	}

	
}
