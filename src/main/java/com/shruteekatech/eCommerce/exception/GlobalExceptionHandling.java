package com.shruteekatech.eCommerce.exception;

import com.shruteekatech.eCommerce.utills.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandling {

    /**
     * to handle ResourceNotFoundException
     * @param ex
     * @return
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundHandller(ResourceNotFoundException ex){
        String message = ex.getMessage();
        ApiResponse api = new ApiResponse(message,false);
        return new ResponseEntity<>(api, HttpStatus.NOT_FOUND);
    }

    /**
     * to handle MethodArgumentNotValidException
       specially this api is useful to handle exception when validation failed
       (input not came as per validation)
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex)
    {
        // field & there message stored in map as key-value pair
        Map<String, String> map = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            // key
            String fieldName = ((FieldError)error).getField();
            // value
            String message = error.getDefaultMessage();
            map.put(fieldName, message);
        });
        return new ResponseEntity<Map<String,String>>(map,HttpStatus.BAD_REQUEST);
    }


}
