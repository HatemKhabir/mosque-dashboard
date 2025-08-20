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
import com.hatemkhabir.mosque_dashboards.model.FileResource;
import com.hatemkhabir.mosque_dashboards.model.Khotba;
import com.hatemkhabir.mosque_dashboards.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {

    private final Cloudinary cloudinary;
    private final FileRepository fileRepository;

    public FileResource uploadFile(MultipartFile file, String fileType, Khotba khotba) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap("resource_type", "auto", "folder", "khotbas"));

        FileResource fileResource = FileResource.builder()
                .fileUrl(uploadResult.get("secure_url").toString())
                .cloudinaryId(uploadResult.get("public_id").toString())
                .fileType(fileType)
                .fileSize(file.getSize())
                .uploadedAt(LocalDateTime.now())
                .khotba(khotba)
                .build();

        return fileRepository.save(fileResource);
    }

    public void deleteFile(FileResource fileResource) throws IOException {
        cloudinary.uploader().destroy(fileResource.getCloudinaryId(), ObjectUtils.emptyMap());
        fileRepository.delete(fileResource);
    }

    public void deleteFiles(List<Long> fileIds) {
        List<FileResource> files = fileRepository.findAllById(fileIds);
        for (FileResource file : files) {
            try {
                cloudinary.uploader().destroy(file.getCloudinaryId(), ObjectUtils.emptyMap());
            } catch (Exception e) {
                log.error("Failed to delete file {}: {}", file.getFileId(), e.getMessage());
            }
        }
        fileRepository.deleteAll(files);
    }


    public String generateDownloadUrl(String publicId) {
        return cloudinary.url()
                .generate(publicId);
    }

    public List<FileResource> listFiles(Long khotbaId){
        return fileRepository.findAllByKhotbaId(khotbaId);
    }

    @SneakyThrows
    public void deleteFilesByKhotba(Khotba khotba) throws IOException{
            List<FileResource> files = fileRepository.findAllByKhotbaId(khotba.getId());
        for (FileResource file : files) {
            try {
                deleteFile(file);
            } catch (Exception e) {
                log.error("Failed to delete file {} for khotba {}: {}", file.getFileId(), khotba.getId(), e.getMessage());
            }
        }
        fileRepository.deleteAll(files);
        }

}
