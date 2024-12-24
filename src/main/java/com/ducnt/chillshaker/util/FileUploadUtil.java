package com.ducnt.chillshaker.util;

import lombok.experimental.UtilityClass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class FileUploadUtil {

    public static final long MAX_FILE_SIZE =  2 * 1024 * 1024;

    public static final String DATE_FORMAT = "yyyyMMddHHmmss";

    public static final String IMAGE_PATTERN = "([^\\\\s]+(\\\\.(?i)(jpg|jpeg|png|gif|bmp|webp))$)";

    public static final String FILE_NAME_FORMAT = "%s_%s_%s";

    public static boolean isAllowedExtension(final String fileName, final String pattern) {
        final Matcher matcher = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(fileName);
        return matcher.matches();
    }

//    public static void assertAllowed (MultipartFile file, String pattern) {
//        final long size = file.getSize();
//        if(size > MAX_FILE_SIZE) {
//            throw new CustomException(ErrorResponse.BAD_REQUEST);
//        }
//        final String fileName = file.getOriginalFilename();
//        final String extension = FilenameUtils.getExtension(fileName);
//        if(!isAllowedExtension(fileName, pattern)) {
//            throw new CustomException(ErrorResponse.BAD_REQUEST);
//        }
//    }

    public static String getFileName(final String name) {
        final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        final String date = dateFormat.format(System.currentTimeMillis());

        return String.format(FILE_NAME_FORMAT, name, date);
    }
}
