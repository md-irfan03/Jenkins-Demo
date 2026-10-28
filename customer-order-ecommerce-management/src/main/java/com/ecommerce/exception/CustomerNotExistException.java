package com.ecommerce.exception;

public class CustomerNotExistException extends RuntimeException
{

	public CustomerNotExistException(String message)
	{
		super(message);
	}
}
