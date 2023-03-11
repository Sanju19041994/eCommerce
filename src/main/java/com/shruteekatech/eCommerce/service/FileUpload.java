package com.shruteekatech.eCommerce.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileUpload {

    // for upload file : create a file on server
    public String uploadFile(String path, MultipartFile file) throws IOException;

    // for browse file : get the resource or file
    public InputStream getResource(String path) throws FileNotFoundException;



}
