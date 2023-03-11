package com.shruteekatech.eCommerce.utills;

import com.shruteekatech.eCommerce.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private List<ProductDto> content;

    private int pageNumber;

    private int pageSize;

    private long totalPages;

    private long totalElements;

    private boolean lastPage;


}
