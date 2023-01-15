package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.configCategory.CategoryDownload;
import com.blog.entities.Category;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CategoryDTO;
import com.blog.repositories.CategoryRepo;
import com.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		Category cat = this.modelMapper.map(categoryDTO, Category.class);
		Category addedCat = this.categoryRepo.save(cat);
		return this.modelMapper.map(addedCat, CategoryDTO.class);
	}

	@Override
	public CategoryDTO getCategoryById(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category ", " Category Id: ", categoryId));
		return this.modelMapper.map(cat, CategoryDTO.class);
	}

	@Override
	public List<CategoryDTO> getAllCategory() {
		List<Category> category_list = this.categoryRepo.findAll();
		List<CategoryDTO> categoryDTOS = category_list.stream()
				.map(category -> this.modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
		return categoryDTOS;
	}

	@Override
	public CategoryDTO updateCategoryTitle(Integer categoryId, String title) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", " Category Id: ", categoryId));
		cat.setCategoryTitle(title);
		Category updatedCategory = this.categoryRepo.save(cat);
		return this.modelMapper.map(updatedCategory, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategoryDescription(Integer categoryId, String description) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", " Category Id: ", categoryId));
		cat.setCategoryDescription(description);
		Category updatedCategory = this.categoryRepo.save(cat);
		return this.modelMapper.map(updatedCategory, CategoryDTO.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category ", " Category Id: ", categoryId));
		this.categoryRepo.delete(cat);
	}

	@Override
	public void downloadToExcel(HttpServletResponse response) throws Exception {
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=categories.xlxs";
		response.setHeader(headerKey, headerValue);

		List<Category> categories = categoryRepo.findAll();
		if (categories.isEmpty()) {
			throw new ResourceNotFoundException("Categories");
		}
		CategoryDownload categoryDownload = new CategoryDownload(categories);
		categoryDownload.export(response);
	}

}
