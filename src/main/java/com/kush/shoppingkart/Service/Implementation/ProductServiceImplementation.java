package com.kush.shoppingkart.Service.Implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kush.shoppingkart.Service.ProductService;
import com.kush.shoppingkart.exceptions.ProductNotFoundException;
import com.kush.shoppingkart.model.Category;
import com.kush.shoppingkart.model.Product;
import com.kush.shoppingkart.repository.CategoryRepository;
import com.kush.shoppingkart.repository.ProductRepository;
import com.kush.shoppingkart.request.AddProductRequest;
import com.kush.shoppingkart.request.UpdateProductRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImplementation implements ProductService{
	
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;

	@Override
	public Product addProduct(AddProductRequest request) {
		// Check if the category is found in the DB
		// If Yes, set it as the new product category
		// If No, then save it as a new category
		// Then set as the new product category.
		
		Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
	}
	
	private Product createProduct(AddProductRequest request, Category category) {
		return new Product(
				request.getName(),
				request.getBrand(),
				request.getPrice(),
				request.getInventory(),
				request.getDescription(),
				category
				
			);
	}

	@Override
	public Product getProductById(Long id) {
		return productRepository.findById(id)
				.orElseThrow(()-> new ProductNotFoundException("Product not Found!"));
	}

	@Override
	public void deleteProductById(Long id) {
		productRepository.findById(id)
				.ifPresentOrElse(productRepository::delete,
						() -> {throw new ProductNotFoundException("Product not Found!");});
		
	}

	@Override
	public Product updateProduct(UpdateProductRequest request, Long productId) {
		
		return productRepository.findById(productId)
			    .map(existingProduct -> updateExistingProduct(existingProduct, request))
				.map(productRepository :: save)
				.orElseThrow(() -> new ProductNotFoundException("Product not found!"));
		
	}
	
	private Product updateExistingProduct(Product existingProduct, UpdateProductRequest request) {
		
		existingProduct.setName(request.getName());
		existingProduct.setBrand(request.getBrand());
		existingProduct.setPrice(request.getPrice());
		existingProduct.setInventory(request.getInventory());
		existingProduct.setDescription(request.getDescription());
		
		Category category = categoryRepository.findByName(request.getCategory().getName());
		existingProduct.setCategory(category);
		
		return existingProduct;
		
	}

	@Override
	public List<Product> getAllProducts() {
		return null;
	}

	@Override
	public List<Product> getProductByCategory(String category) {
		return productRepository.findByCategoryName(category);
	}

	@Override
	public List<Product> getProductByBrand(String brand) {
		return productRepository.findByBrand(brand);
	}

	@Override
	public List<Product> getProductByCategoryAndBrand(String category, String brand) {
		return productRepository.findByCategoryNameAndBrand(category, brand);
	}

	@Override
	public List<Product> getProductByName(String name) {
		return productRepository.findByName(name);
	}

	@Override
	public List<Product> getProductByBrandAndName(String category, String name) {
		return productRepository.findByBrandAndName(category, name);
	}

	@Override
	public Long countProductsByBradAndName(String brand, String name) {
		return productRepository.countByBrandAndName(brand, name);
	}

}
