package com.ducnt.chillshaker.service.interfaces;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ICloudinaryService {
    @Transactional
    String uploadFile(MultipartFile file);

    @Transactional
    List<String> updateFiles(List<String> oldFileUrls, List<MultipartFile> newFiles) throws IOException;

    @Transactional
    List<String> uploadFiles(List<MultipartFile> files) throws IOException;

    @Transactional
    boolean deleteFiles(String imageUrls) throws IOException;
}
