package com.shruteekatech.eCommerce.constants;

public class AppConstants {

    public static final String DELETE_SUCCESS = "Deleted Successfully";

    public static final String IMAGE_UPLOAD_FAIL = "File Not Uploaded On Server!!";
    public static final String LOGIN_SUCCESS = "Login successfully";

    public static final String LOGIN_FAIL = "Invalid credential, Login Fail";

    // for sorting & pagination
    public static final String PAGE_NUMBER_STRING = "0";
    public static final String PAGE_SIZE_STRING = "2";
    public static final String SORT_BY_STRING_PRODUCT = "productId";
    public static final String SORT_BY_STRING_CATEGORY = "categoryId";
    public static final String SORT_DIR_STRING = "asc";

    // for security
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

}
