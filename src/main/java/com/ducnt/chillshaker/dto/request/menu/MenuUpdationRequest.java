package com.ducnt.chillshaker.dto.request.menu;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MenuUpdationRequest {
    String name;
    String description;
    String images;
    Double price;
    UUID barId;
    List<String> oldUrls;
    List<MultipartFile> newfiles;
}
