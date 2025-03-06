package com.kush.shoppingkart.Service.Implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kush.shoppingkart.Service.CategoryService;
import com.kush.shoppingkart.exceptions.AlreadyExistsException;
import com.kush.shoppingkart.exceptions.ResourceNotFoundException;
import com.kush.shoppingkart.model.Category;
import com.kush.shoppingkart.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

	private final CategoryRepository categoryRepository;
	@Override
	public Category getCategoryById(Long id) {
		
		return categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
		
	}

	@Override
	public Category getCategoryByName(String name) {
		
		return categoryRepository.findByName(name);
		
	}

	@Override
	public List<Category> getAllCategories() {
	
		return categoryRepository.findAll();
		
	}

	@Override
	public Category addCategory(Category category) {
		
		return Optional.of(category).filter(c-> !categoryRepository.existsByName(c.getName()))
				.map(categoryRepository :: save)
				.orElseThrow(() -> new AlreadyExistsException(category.getName()+ " already exists"));
	
	}
	
	@Override
    public Category updateCategory(Category category, Long id) {
    
		return Optional.ofNullable(getCategoryById(id)).map(oldCategory -> {
            oldCategory.setName(category.getName());
            return categoryRepository.save(oldCategory);
        }) .orElseThrow(()-> new ResourceNotFoundException("Category not found!"));
	
	}

	@Override
	public void deleteCategoryById(Long id) {
	
		categoryRepository.findById(id)
	        .ifPresentOrElse(
	            category -> categoryRepository.delete(category), 
	            () -> { throw new ResourceNotFoundException("Category not found!"); }
	        );
	
	}
	
}
