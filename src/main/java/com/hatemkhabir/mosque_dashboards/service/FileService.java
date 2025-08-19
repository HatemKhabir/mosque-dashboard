package com.hatemkhabir.mosque_dashboards.service;


/*
* storeFile(file, fileType) - Save uploaded file to storage
getFile(fileId) - Retrieve file for download
deleteFile(fileId) - Remove file from storage
generateDownloadUrl(fileId) - Create temporary access URL
validateFileType(file, allowedTypes) - Check file format
extractMetadata(file) - Get duration, page count, etc.
* */

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {

    private final Cloudinary cloudinary;

    public String storeFile(MultipartFile file, String folder) {
        try {
            Map options = ObjectUtils.asMap(
                    "folder", folder,
                    "resource_type", "auto"
            );
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            log.error("File failed to upload with error {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String deleteFile(String fileId){
        try {
           Map uploadResult = cloudinary.uploader().destroy(fileId, ObjectUtils.emptyMap());
            return uploadResult.get("result").toString();
        }catch (Exception e){
            log.error("File deleting error : {}",e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean deleteFiles(String[] fileIds){
        try {
            for (String fileId : fileIds) {
            cloudinary.uploader().destroy(fileId,ObjectUtils.emptyMap());
            };
            return true;
        }catch (Exception e){
            log.error("Files deleting error : {}",e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String generateDownloadUrl(String publicId) {
        return cloudinary.url()
                .generate(publicId);
    }




}
