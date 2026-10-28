package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ecommerce.entity.OrderEntity;

public interface IOrderRepository extends CrudRepository<OrderEntity, Long>
{
	@Query("select o from OrderEntity o where o.customer.id= :customerId")
	List<OrderEntity> getAllOrdersByCustomer(@Param("customerId") long id);
}
