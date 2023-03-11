package com.shruteekatech.eCommerce.service;

import com.shruteekatech.eCommerce.dto.CategoryDto;
import com.shruteekatech.eCommerce.utills.CategoryResponse;

import java.util.List;

public interface CategoryService {

    public CategoryDto addCategory(CategoryDto categoryDto);

    public CategoryDto getSingleCategory(Long categoryId);

    public CategoryResponse getAllCategory(int pageNumber, int pageSize,String sortBy, String sortDir);

    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto);

    public void deleteCategory(Long categoryId);
}
