package com.shruteekatech.eCommerce.utills;

import com.shruteekatech.eCommerce.dto.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {

    private List<CategoryDto> content;

    private int pageNumber;

    private int pageSize;

    private long totalPages;

    private long totalElements;

    private boolean lastPage;


}
