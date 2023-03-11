package com.shruteekatech.eCommerce.controller;

import com.shruteekatech.eCommerce.constants.AppConstants;
import com.shruteekatech.eCommerce.dto.ProductDto;
import com.shruteekatech.eCommerce.entity.Product;
import com.shruteekatech.eCommerce.exception.ResourceNotFoundException;
import com.shruteekatech.eCommerce.service.FileUpload;
import com.shruteekatech.eCommerce.service.ProductService;
import com.shruteekatech.eCommerce.utills.ApiResponse;
import com.shruteekatech.eCommerce.utills.ProductResponse;
import org.apache.catalina.loader.ResourceEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private FileUpload fileUpload;

    @Value("${product.images.path}")
    private String imagePath;

    /**
     * @author  Sanju
     * @param productDto
     * @param categoryId
     * @apiNote  api created for addProduct
     * @return  productDto
     * @since 1.0
     */
    @PostMapping("/addProduct/category/{categoryId}")
    public ResponseEntity<ProductDto> addProduct
    (@Valid @RequestBody ProductDto productDto, @PathVariable Long categoryId)
    {
        logger.info("Start : addProduct() statred from ProductController");
        ProductDto addProduct = this.productService.addProduct(productDto,categoryId);
        logger.info("Complete : addProduct() completed from ProductController");
        return new ResponseEntity<ProductDto>(addProduct, HttpStatus.CREATED);
    }


    /**
     * @apiNote : api created to upload product images
     * @param productId
     * @param file
     * @return
     */
    @PostMapping("/images/{productId}")
    // ResponseEntity<?> : with help of ? mark we can return many types in one method
    public ResponseEntity<?> uploadImageOfProduct
            ( @PathVariable Long productId, @RequestParam("product_image") MultipartFile file ) {
        logger.info("Start : uploadImageOfProduct() statred from ProductController");
        // get product by productId
        ProductDto product = this.productService.getSingleProduct(productId);
        String imageName = null;
        try {
            // call uploadFile method & provide input as path & MultipartFile file
            imageName = this.fileUpload.uploadFile(imagePath, file);
            // set this image name into product
            product.setImageName(imageName);
            // update product with image setted new product & productId
            ProductDto updatedProduct = this.productService.updateProduct(product, productId);
            logger.info("Complete : uploadImageOfProduct() completed from ProductController");
            return new ResponseEntity<ProductDto>(updatedProduct,HttpStatus.CREATED);

        }catch (IOException e)
        {
            // ResponseEntity<?> : with help of ? mark we can return many types in one method, For Example..
            return new ResponseEntity<>(new ApiResponse(AppConstants.IMAGE_UPLOAD_FAIL,false),HttpStatus.INTERNAL_SERVER_ERROR);
         // return new ResponseEntity<>(Map.of("message",AppConstants.IMAGE_UPLOAD_FAIL),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/images/{productId}")
    public void downloadImageOfProduct(@PathVariable Long productId, HttpServletResponse response) throws IOException {
        logger.info("Start : downloadImageOfProduct() started from ProductController");
        // get product by productId & get imageName from product
        ProductDto product = this.productService.getSingleProduct(productId);
        String imageName = product.getImageName();
        // take fullPath of image
        String fullPath = imagePath + File.separator+imageName;

        InputStream resource = this.fileUpload.getResource(fullPath);

        // HttpServletResponse help to set media type of image
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        OutputStream outputStream = response.getOutputStream();
        // now copy resource & get response into OutPutStream
        StreamUtils.copy(resource,outputStream);

        logger.info("Complete : downloadImageOfProduct() completed from ProductController");

    }



    /**
     * @author  Sanju
     * @apiNote  api created for getSingleProduct details with productId
     * @param productId
     * @return productDto
     * @since 1.0
     * @exception : ResourceNotFoundException
     */
    @GetMapping("/getSingleProduct/{productId}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable Long productId){
        logger.info("Start : getSingleProduct() statred from ProductController");
        ProductDto singleProduct = this.productService.getSingleProduct(productId);
        logger.info("Complete : getSingleProduct() completed from ProductController");
        return new ResponseEntity<>(singleProduct,HttpStatus.OK);
    }

    /**
     * @author  Sanju
     * @apiNote  api created for get list of Product with categoryId
     * @param categoryId
     * @return : list of products
     * @since 1.0
     * @exception : ResourceNotFoundException
     */
    @GetMapping("/getProductByCategory/{categoryId}")
    // url for default => localhost:9393/products/getProductByCategory/{categoryId}
    // for customized url => localhost:9393/products/getProductByCategory/{categoryId}?pageNumber=1&pageSize=2
    // demo url....with sorting & pagination :
    // localhost:9393/products/getProductByCategory/3?pageNumber=0&pageSize=2&sortBy=productName&sortDir=desc
    public ResponseEntity<ProductResponse> getProductByCategory(
            @PathVariable Long categoryId,
            @RequestParam(value = "pageNumber" , defaultValue = AppConstants.PAGE_NUMBER_STRING, required = false) int pageNumber,
            @RequestParam(value = "pageSize" , defaultValue = AppConstants.PAGE_SIZE_STRING, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_STRING_PRODUCT,required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR_STRING, required = false) String sortDir
    )
    {
        logger.info("Start : getProductByCategory() statred from ProductController");
        ProductResponse response = this.productService.getProductByCategoryId(categoryId, pageNumber, pageSize,sortBy,sortDir);
        logger.info("Complete : getProductByCategory() completed from ProductController");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * @author  Sanju
     * @apiNote : api created for get list of all product
     * @return list of productDto
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     */
    @GetMapping("/allProduct")
    // url for default find => localhost:9393/products/allProduct
    // url for pageNumer=0 => localhost:9393/products/allProduct?pageNumber=0
    // url for pageNumer=1 & pageSize=3 => localhost:9393/products/allProduct?pageNumber=1&pageSize=3
    public ResponseEntity<ProductResponse> getAllProduct(
            @RequestParam(value = "pageNumber" , defaultValue = AppConstants.PAGE_NUMBER_STRING, required = false) int pageNumber,
            @RequestParam(value = "pageSize" , defaultValue = AppConstants.PAGE_SIZE_STRING, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_STRING_PRODUCT,required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR_STRING, required = false) String sortDir
    ){
        logger.info("Start : getAllProduct() started from ProductController");
        ProductResponse allProduct = this.productService.getAllProduct(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Complete : getallProduct() completed from ProductController");
        return new ResponseEntity<>(allProduct, HttpStatus.OK);
    }

    /**
     * @autor Sanju
     * @apiNote api created for update product with help of productId
     * @param productDto
     * @param productId
     * @return updated productDto
     * @since 1.0
     * @exception : ResourceNotFoundException
     */
    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto,
                                                    @PathVariable Long productId){
        logger.info("Start : updateProduct() started from ProductController");
        ProductDto updateProduct = this.productService.updateProduct(productDto, productId);
        logger.info("Complete : updateProduct() completed from ProductController");
        return new ResponseEntity<>(updateProduct,HttpStatus.CREATED);
    }

    /**
     * @autor Sanju
     * @apiNote api created for delete product details with help of productId
     * @param productId
     * @return
     * @since 1.0
     * @exception :ResourceNotFoundException
     */
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> deleteProduct ( @PathVariable Long productId){
        logger.info("Start : deleteProduct() started from ProductController");
        this.productService.deleteProduct(productId);
        logger.info("Complete : deleteProduct() completed from ProductController");
        return new ResponseEntity<>(AppConstants.DELETE_SUCCESS, HttpStatus.OK);
    }



}