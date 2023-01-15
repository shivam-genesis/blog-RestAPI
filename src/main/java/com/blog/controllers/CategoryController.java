package com.blog.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.exceptions.ApiResponse;
import com.blog.payloads.CategoryDTO;
import com.blog.services.CategoryService;

@RestController
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	// POST -> Create Category
	@PostMapping("/category")
	public ResponseEntity<CategoryDTO> createUser(@Valid @RequestBody CategoryDTO categoryDTO) {
		CategoryDTO createCategoryDTO = this.categoryService.createCategory(categoryDTO);
		return new ResponseEntity<CategoryDTO>(createCategoryDTO, HttpStatus.CREATED);
	}

	// GET -> Get Specific Category
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Integer categoryId) {
		CategoryDTO categoryDTO = this.categoryService.getCategoryById(categoryId);
		return new ResponseEntity<CategoryDTO>(categoryDTO, HttpStatus.OK);
	}

	// GET -> Get Categories
	@GetMapping("/categories")
	public ResponseEntity<List<CategoryDTO>> getAllCategory() {
		return ResponseEntity.ok(this.categoryService.getAllCategory());
	}

	// PUT -> Update Category Title
	@PutMapping("/category/{categoryId}/title")
	public ResponseEntity<CategoryDTO> updateCategoryTitle(@PathVariable Integer categoryId,
			@Valid @RequestParam String title) {
		CategoryDTO updatedCategoryDTO = this.categoryService.updateCategoryTitle(categoryId, title);
		return new ResponseEntity<CategoryDTO>(updatedCategoryDTO, HttpStatus.OK);
	}

	// PUT -> Update Category Description
	@PutMapping("/category/{categoryId}/description")
	public ResponseEntity<CategoryDTO> updateCategoryDescription(@PathVariable Integer categoryId,
			@Valid @RequestParam String description) {
		CategoryDTO updatedCategoryDTO = this.categoryService.updateCategoryDescription(categoryId, description);
		return new ResponseEntity<CategoryDTO>(updatedCategoryDTO, HttpStatus.OK);
	}

	// DELETE -> Delete Category
	@DeleteMapping("/category/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted!!", true), HttpStatus.OK);
	}

	@GetMapping("/categories/download")
	public ResponseEntity<ApiResponse> downloadToExcel(HttpServletResponse response) throws Exception {
		this.categoryService.downloadToExcel(response);
		return new ResponseEntity<ApiResponse>(new ApiResponse("File Downloaded!!", true), HttpStatus.OK);
	}
}
