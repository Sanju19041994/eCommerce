package com.shruteekatech.eCommerce.service;

import com.shruteekatech.eCommerce.dto.ProductDto;
import com.shruteekatech.eCommerce.utills.ProductResponse;

import java.util.List;

public interface ProductService {

    public ProductDto addProduct(ProductDto productDto,Long categoryId);

    public ProductDto getSingleProduct(Long productId);

    public ProductResponse getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir);

    public ProductDto updateProduct(ProductDto productDto, Long productId);

    public void deleteProduct(Long productId);

    public ProductResponse getProductByCategoryId(Long categoryId, int pageNumber, int pageSize, String sortBy, String sortDir);

}
