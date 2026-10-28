package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ecommerce.entity.CustomerEntity;

public interface ICustomerRepository extends CrudRepository<CustomerEntity, Long>
{
	/**
	 * this method will give all customers with their all orders details
	 * @return {@link #List}<{@link #CustomerEntity}> with {@link #OrderEntity}
	 */
	@Query("select c from CustomerEntity c LEFT JOIN FETCH c.orders ")	//JPQL
	public List<CustomerEntity> readAllCustomers();
	
	
	/**
	 * it takes id of a customer and returns the complete details of that customer with orders
	 * 
	 * if the customer found with the given id then {@link #CustomerEntity} with its {@link #OrderEntity} 
	 *  otherwise it throw {@link #CustomerNotExistException}
	 *  
	 * @param id
	 * @return {@link #CustomerEntity} 
	 */
	@Query("select c from CustomerEntity c LEFT JOIN FETCH c.orders where c.id= :id ")	//JPQL
	public CustomerEntity getOrdersByCustomer(@Param("id") long id);
	
	
	/**
	 * Derived Query Method
	 * 
	 * @param name
	 * @param email
	 * @return true if {@link #name} and {@link #email} match in the database otherwise false.
	 */
	public boolean existsByNameAndEmail(String name, String email);
	
	/**
	 * it will update an email of a customer with the given id.
	 * 
	 * @param id
	 * @param newEmail
	 * @return number of rows Affected
	 */
	@Modifying
	@Query("update CustomerEntity c set c.email = :newEmel where c.id = :cusId")	//JPQL --> works on class entity
	public int updateEmail(@Param("cusId") long id, @Param("newEmel") String newEmail);
}
