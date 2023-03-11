package com.shruteekatech.eCommerce.serviceImpl;

import com.shruteekatech.eCommerce.dto.CategoryDto;
import com.shruteekatech.eCommerce.entity.Category;
import com.shruteekatech.eCommerce.exception.ResourceNotFoundException;
import com.shruteekatech.eCommerce.repository.CategoryRepo;
import com.shruteekatech.eCommerce.service.CategoryService;
import com.shruteekatech.eCommerce.utills.CategoryResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        logger.info("Start : addCategory() started from CategoryServiceImpl");
        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category save = this.categoryRepo.save(category);
        CategoryDto savedCategory = this.modelMapper.map(save, CategoryDto.class);
        logger.info("Complete : addCategory() completed from CategoryServiceImpl");
        return savedCategory;
    }

    @Override
    public CategoryDto getSingleCategory(Long categoryId) {
        logger.info("Start : getSingleCategory() started from CategoryServiceImpl");
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id"));
        CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
        logger.info("Complete : getSingleCategory() completed from CategoryServiceImpl");
        return categoryDto;
    }

    @Override
    public CategoryResponse getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDir) {
        logger.info("Start : getAllCategory() started from CategoryServiceImpl");
        Sort sort = null;
        if(sortDir.trim().toLowerCase().equals("asc")){
            sort = Sort.by(sortBy).ascending();
        }else{
            sort = Sort.by(sortBy).descending();
        }

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Category> page = this.categoryRepo.findAll(pageable);
        List<Category> content = page.getContent();
        List<CategoryDto> dtoList = content.stream().map((category ->
                this.modelMapper.map(category, CategoryDto.class))).collect(Collectors.toList());

        CategoryResponse response = new CategoryResponse();
        response.setContent(dtoList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());
        response.setLastPage(page.isLast());

        logger.info("Complete : getAllCategory() completed from CategoryServiceImpl");
        return response;
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        logger.info("Start : updatedCategory() started from CategoryServiceImpl");
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id"));
        category.setCategoryName(categoryDto.getCategoryName());

        Category update = this.categoryRepo.save(category);
        CategoryDto updatedCategory = this.modelMapper.map(update, CategoryDto.class);
        logger.info("Complete : updatedCategory() completed from CategoryServiceImpl");
        return updatedCategory;
    }

    @Override
    public void deleteCategory(Long categoryId) {
        logger.info("Start : deleteCategory() started from CategoryServiceImpl");
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id"));
        logger.info("Complete : deleteCategory() completed from CategoryServiceImpl");
        this.categoryRepo.delete(category);

    }
}
