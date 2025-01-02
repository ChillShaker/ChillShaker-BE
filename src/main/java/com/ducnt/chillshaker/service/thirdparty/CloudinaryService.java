package com.ducnt.chillshaker.service.thirdparty;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ducnt.chillshaker.exception.CustomException;
import com.ducnt.chillshaker.exception.ErrorResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class CloudinaryService {
    Cloudinary cloudinary;

    @Transactional
    public String uploadFile(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("secure_url").toString();
        } catch (IOException  ex) {
            log.error(ex.getMessage());
            throw new CustomException(ErrorResponse.IMAGE_FILE_INVALID);
        }
    }

    @Transactional
    public List<String> updateFiles(List<String> oldFileUrls, List<MultipartFile> newFiles) throws IOException {
        try {
            for (String oldFileUrl : oldFileUrls) {
                String publicId = extractPublicId(oldFileUrl);
                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            }

            return uploadFiles(newFiles);
        } catch (IOException ex) {
            log.error(ex.getMessage());
            throw new IOException(ex.getMessage());
        }
    }

    private String extractPublicId(String fileUrl) {
        int lastSlashIndex = fileUrl.lastIndexOf('/');
        int dotIndex = fileUrl.lastIndexOf('.');
        return fileUrl.substring(lastSlashIndex + 1, dotIndex);
    }

    @Transactional
    public List<String> uploadFiles(List<MultipartFile> files) throws IOException {
        List<String> uploadedUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                String secureUrl = uploadResult.get("secure_url").toString();
                uploadedUrls.add(secureUrl);
            } catch (IOException ex) {
                log.error(ex.getMessage());
                throw new IOException(ex.getMessage());
            }
        }
        return uploadedUrls;
    }

    @Transactional
    public boolean deleteFiles(String imageUrls) throws IOException {
        List<String> imageUrlList = extractUrlFromString(imageUrls);
        try {
            for (String oldFileUrl : imageUrlList) {
                String publicId = extractPublicId(oldFileUrl);
                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            }

            return true;
        } catch (IOException ex) {
            log.error(ex.getMessage());
            throw new IOException(ex.getMessage());
        }
    }

    private List<String> extractUrlFromString(String imageUrls) {
        return Arrays.asList(imageUrls.split("\\s*,\\s*"));
    }
}
