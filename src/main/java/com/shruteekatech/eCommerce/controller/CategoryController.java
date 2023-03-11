package com.shruteekatech.eCommerce.controller;

import com.shruteekatech.eCommerce.constants.AppConstants;
import com.shruteekatech.eCommerce.utills.ApiResponse;
import com.shruteekatech.eCommerce.dto.CategoryDto;
import com.shruteekatech.eCommerce.serviceImpl.CategoryServiceImpl;
import com.shruteekatech.eCommerce.utills.CategoryResponse;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryServiceImpl categoryService;


    /**
     * @autor Sanju
     * @since 1.0
     * @apiNote : api created for create new category
     * @param categoryDto
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
        logger.info("Start : addCategory() started from CategoryController");
        CategoryDto savedCategory = this.categoryService.addCategory(categoryDto);
        logger.info("Complete : addCategory() completed from CategoryController");
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    /**
     * @autor Sanju
     * @since 1.0
     * @apiNote : api created for get single category by categoryId
     * @param categoryId
     * @return
     * @exception : ResourceNotFoundException
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable Long categoryId){
        logger.info("Start : getSingleCategory() started from CategoryController");
        CategoryDto singleCategory = this.categoryService.getSingleCategory(categoryId);
        logger.info("Complete : getSingleCategory() completed from CategoryController");
        return new ResponseEntity<>(singleCategory,HttpStatus.OK);
    }


    /**
     * Sanju
     * @since 1.0
     * @apiNote : api created for get all category list
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return : Category Response ( CategoryDto + Pagination )
     */
    @GetMapping("/")
    public ResponseEntity<CategoryResponse> getAllCategory(
            @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER_STRING,required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE_STRING,required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_STRING_CATEGORY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR_STRING, required = false) String sortDir
    ){
        logger.info("Start : getAllCategory() started from CategoryController");
        CategoryResponse allCategory = this.categoryService.getAllCategory(pageNumber,pageSize,sortBy,sortDir);
        logger.info("Complete : getAllCategory() completed from CategoryController");
        return new ResponseEntity<>(allCategory,HttpStatus.OK);
    }


    /**
     * @autor Sanju
     * @since 1.0
     * @apiNote : api created for update category with categoryId and categoryDto
     * @param categoryId
     * @param categoryDto
     * @return
     * @exception : ResourceNotFoundException
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory
            (@PathVariable Long categoryId,@RequestBody CategoryDto categoryDto)
    {
        logger.info("Start : updateCategory() started from CategoryController");
        CategoryDto updatedCategory = this.categoryService.updateCategory(categoryId, categoryDto);
        logger.info("Complete : updateCategory() completed from CategoryController");
        return new ResponseEntity<>(updatedCategory,HttpStatus.CREATED);
    }


    /**
     * @autor Sanju
     * @since 1.0
     * @apiNote : api created for delete category with help of categoryId
     * @param categoryId
     * @return
     * @exception : ResourceNotFoundException
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId){
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(new ApiResponse(AppConstants.DELETE_SUCCESS,true),HttpStatus.OK);
    }


}
