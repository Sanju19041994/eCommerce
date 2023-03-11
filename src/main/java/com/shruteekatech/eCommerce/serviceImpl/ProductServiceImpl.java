package com.shruteekatech.eCommerce.serviceImpl;

import com.shruteekatech.eCommerce.dto.ProductDto;
import com.shruteekatech.eCommerce.entity.Category;
import com.shruteekatech.eCommerce.entity.Product;
import com.shruteekatech.eCommerce.exception.ResourceNotFoundException;
import com.shruteekatech.eCommerce.repository.CategoryRepo;
import com.shruteekatech.eCommerce.repository.ProductRepo;
import com.shruteekatech.eCommerce.service.ProductService;
import com.shruteekatech.eCommerce.utills.ProductResponse;
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
public class ProductServiceImpl implements ProductService {

    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public ProductDto addProduct(ProductDto productDto,Long categoryId) {
        logger.info("Start : addProduct() started from ProductServiceImpl");
        Product product =  this.modelMapper.map(productDto, Product.class);

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId"));
        product.setCategory(category);

        Product productSaved = this.productRepo.save(product);
        ProductDto savedDto = this.modelMapper.map(productSaved,ProductDto.class);
        logger.info("Complete : addProduct() completed from ProductServiceImpl ");
        return savedDto;
    }

    @Override
    public ProductDto getSingleProduct(Long productId) {
        logger.info("Start : getSingleProduct() started from ProductServiceImpl");
        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id"));
        ProductDto productDto = this.modelMapper.map(product, ProductDto.class);
        logger.info("Complete : getSingleProduct() completed from ProductServiceImpl ");
        return productDto;
    }


    @Override
    public ProductResponse getAllProduct(int pageNumber,int pageSize, String sortBy, String sortDir) {
        logger.info("Start : getAllProduct() started from ProductServiceImpl");
        Sort sort = null;
        if(sortDir.trim().toLowerCase().equals("asc")){
            sort = Sort.by(sortBy).ascending();
        }else{
            sort = Sort.by(sortBy).descending();
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<Product> page = this.productRepo.findAll(pageable);

        List<Product> products = page.getContent();
        List<ProductDto> productDtoList = products.stream().map((product) -> this.modelMapper
                                 .map(product, ProductDto.class)).collect(Collectors.toList());

        ProductResponse response = new ProductResponse();
        response.setContent(productDtoList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());
        response.setLastPage(page.isLast());

        logger.info("Complete : getAllProduct() completed from ProductServiceImpl ");
        return response;
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Long productId) {
        logger.info("Start : updateProduct() started from ProductServiceImpl");
        Product product1 = this.productRepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product","Product Id"));

        product1.setProductName(productDto.getProductName());
        product1.setProductDesc(productDto.getProductDesc());
        product1.setProductPrice(productDto.getProductPrice());
        product1.setProductQuantity(productDto.getProductQuantity());
        product1.setImageName(productDto.getImageName());
        product1.setStock(productDto.isStock());
        product1.setLive(productDto.isLive());

        Product updatedProduct = this.productRepo.save(product1);

        ProductDto updatedProductDto = this.modelMapper.map(updatedProduct, ProductDto.class);
        logger.info("Complete : updateProduct() completed from ProductServiceImpl");
        return updatedProductDto;
    }

    @Override
    public void deleteProduct(Long productId) {
        logger.info("Start : deleteProduct() started from ProductServiceImpl");
        Product product1 = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id"));
        this.productRepo.delete(product1);
        logger.info("Complete : deleteProduct() completed from ProductServiceImpl");
        this.productRepo.deleteById(product1.getProductId());
    }


    @Override
    public ProductResponse getProductByCategoryId(Long categoryId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        logger.info("Start : getProductByCategoryId() started from ProductServiceImpl");
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id"));
        // creating Sort object for sorting
        Sort sort = null;
        if(sortDir.trim().toLowerCase().equals("asc")){
            sort = Sort.by(sortBy).ascending();
        }else{
            sort = Sort.by(sortBy).descending();
        }
        // creating pageable object
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        // getting list of product by passing category & pageable object
        Page<Product> page = this.productRepo.findByCategory(category, pageable);
        List<Product> content = page.getContent();
        List<ProductDto> productDtos = content.stream().map((list) -> this.modelMapper.map
                                        (list, ProductDto.class)).collect(Collectors.toList());
        // converting productDtos into ProductResponse
        ProductResponse response = new ProductResponse();
        response.setContent(productDtos);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());
        response.setLastPage(page.isLast());

        logger.info("Complete : getProductByCategoryId() completed from ProductServiceImpl");
        return response;
    }


}
