package com.kush.shoppingkart.request;

import java.math.BigDecimal;
import com.kush.shoppingkart.model.Category;
import lombok.Data;

@Data
public class AddProductRequest {
	
	private Long id;
    
	private String name;
    
	private String brand;
    
	private BigDecimal price;
    
	private int inventory;
    
    private String description;

    private Category category;

}
