package com.ducnt.chillshaker.service.thirdparty;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ducnt.chillshaker.exception.CustomException;
import com.ducnt.chillshaker.exception.ErrorResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CloudinaryService {
    Cloudinary cloudinary;

    @Transactional
    public String uploadFile(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("secure_url").toString();
        } catch (IOException  ex) {
            throw new CustomException(ErrorResponse.IMAGE_FILE_INVALID);
        }
    }

    @Transactional
    public List<String> updateFiles(List<String> oldFileUrls, List<MultipartFile> newFiles) {
        try {
            List<String> uploadedUrls = new ArrayList<>();
            for (String oldFileUrl : oldFileUrls) {
                String publicId = extractPublicId(oldFileUrl);
                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            }

            return uploadFiles(newFiles);
        } catch (IOException ex) {
            throw new CustomException(ErrorResponse.IMAGE_FILE_INVALID);
        }
    }

    private String extractPublicId(String fileUrl) {
        int lastSlashIndex = fileUrl.lastIndexOf('/');
        int dotIndex = fileUrl.lastIndexOf('.');
        return fileUrl.substring(lastSlashIndex, dotIndex);
    }

    @Transactional
    public List<String> uploadFiles(List<MultipartFile> files) {
        List<String> uploadedUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                String secureUrl = uploadResult.get("secure_url").toString();
                uploadedUrls.add(secureUrl);
            } catch (IOException ex) {
                throw new CustomException(ErrorResponse.IMAGE_FILE_INVALID);
            }
        }
        return uploadedUrls;
    }
}
