package com.ecommerce.constant;

import java.util.Random;


public enum OrderStatus
{
	DELIVERED,
	PLACED,
    SHIPPED,
    CANCELLED;
    
    public static OrderStatus randomStatus() {

        OrderStatus[] statuses = OrderStatus.values();

        return statuses[new Random().nextInt(statuses.length)];
    }
}
