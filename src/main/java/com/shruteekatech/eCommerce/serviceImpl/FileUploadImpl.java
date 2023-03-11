package com.shruteekatech.eCommerce.serviceImpl;

import com.shruteekatech.eCommerce.service.FileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileUploadImpl implements FileUpload {

    Logger logger = LoggerFactory.getLogger(FileUploadImpl.class);

    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        logger.info("Start : uploadFile() started from FileUploadImpl");
        // get original name of file
        String originalFilename = file.getOriginalFilename();
        // create full path with help of path & originalFilename
//        String fullPath = path + File.separator + originalFilename;

        // generate random name
        String randomNameId = UUID.randomUUID().toString();
        // concat with randomNameId, take extension after dot(.) by substring
        String randomName = randomNameId.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));
        String fullPath = path + File.separator + randomName;

        File fileFolder = new File(path);
        // checking folder is available or not, if not exits then create new one
        if(!fileFolder.exists()){
            fileFolder.mkdirs();
        }

        // copy file data into given path with help of copy() of Files class & get() of Paths class
        Files.copy(file.getInputStream(), Paths.get(fullPath));
        logger.info("Complete : uploadFile() completed from FileUploadImpl");
//        return originalFilename;
        return randomName;
    }

    @Override
    public InputStream getResource(String path) throws FileNotFoundException {
        logger.info("Start : getResource() started from FileUploadImpl");
        InputStream inputStream = new FileInputStream(path);
        logger.info("Complete : getResource() completed from FileUploadImpl");
        return inputStream;
    }




}
