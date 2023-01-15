package com.blog.services;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.blog.payloads.CategoryDTO;

public interface CategoryService {

	// Create Category
	CategoryDTO createCategory(CategoryDTO categoryDTO);

	// Get Category By Id
	CategoryDTO getCategoryById(Integer categoryId);

	// Get All Categories
	List<CategoryDTO> getAllCategory();

	// Update Category Title
	CategoryDTO updateCategoryTitle(Integer categoryId, String title);

	// Update Category Description
	CategoryDTO updateCategoryDescription(Integer categoryId, String description);

	// Delete Category
	void deleteCategory(Integer categoryId);
	
	void downloadToExcel(HttpServletResponse response) throws Exception;

}
