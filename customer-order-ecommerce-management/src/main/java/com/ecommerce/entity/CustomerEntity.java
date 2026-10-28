package com.ecommerce.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "customer")
public class CustomerEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;
	private String email;
	private LocalDate createdAt;
	
	@OneToMany(mappedBy ="customer", fetch = FetchType.LAZY)	//EAGER	
	//EAGER loads orderEntity data whenever CustomerEntity is loaded even if you don't need it --> hitting database unnecessarily
	List<OrderEntity> orders;

	public CustomerEntity() {
		this.createdAt = LocalDate.now();
	}
	
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

	public List<OrderEntity> getOrders()
	{
		return orders;
	}

	public void setOrders(List<OrderEntity> orders)
	{
		this.orders = orders;
	}

	public LocalDate getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt)
	{
		this.createdAt = createdAt;
	}

}
