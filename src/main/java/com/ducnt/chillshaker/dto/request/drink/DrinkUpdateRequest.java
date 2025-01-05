package com.ducnt.chillshaker.dto.request.drink;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
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
public class DrinkUpdateRequest {
    @Size(min = 5, message = "Name must be at least 5 character")
    String name;
    @Size(min = 12, message = "Description must be at least 12 character")
    String description;
    @Min(value = 1000, message = "Price must be at least 1000")
    double price;
    UUID drinkCategoryId;
    boolean status;
    List<String> oldFileUrls;
    List<MultipartFile> newFiles;
}
