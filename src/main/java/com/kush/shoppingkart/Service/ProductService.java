package com.kush.shoppingkart.Service;

import java.util.List;

import com.kush.shoppingkart.model.Product;
import com.kush.shoppingkart.request.AddProductRequest;
import com.kush.shoppingkart.request.UpdateProductRequest;

public interface ProductService {
	
	Product addProduct(AddProductRequest product);
	Product getProductById(Long id);
	void deleteProductById(Long id);
	Product updateProduct(UpdateProductRequest requst, Long productId);
	List<Product> getAllProducts();
	List<Product> getProductByCategory(String category);
	List<Product> getProductByBrand(String brand);
	List<Product> getProductByCategoryAndBrand(String category, String brand);
	List<Product> getProductByName(String name);
	List<Product> getProductByBrandAndName(String category, String name);
	Long countProductsByBradAndName(String brand, String name);
	
}
