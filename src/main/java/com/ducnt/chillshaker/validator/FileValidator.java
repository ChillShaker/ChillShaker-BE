package com.ducnt.chillshaker.validator;

import com.ducnt.chillshaker.exception.CustomException;
import com.ducnt.chillshaker.exception.ErrorResponse;
import com.ducnt.chillshaker.util.FileUploadUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class FileValidator implements ConstraintValidator<FileConstraint, MultipartFile> {
    int min;

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        if(Objects.isNull(file)) return true;

        final long size = file.getSize();
        if(size > FileUploadUtil.MAX_FILE_SIZE) {
            return false;
        }

        final String fileName = file.getOriginalFilename();
        final String extension = FilenameUtils.getExtension(fileName);
        if(!FileUploadUtil.isAllowedExtension(fileName, FileUploadUtil.IMAGE_PATTERN)) {
            throw new CustomException(ErrorResponse.IMAGE_FILE_INVALID);
        }
        return false;
    }

    @Override
    public void initialize(FileConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        min = constraintAnnotation.min();
    }
}
