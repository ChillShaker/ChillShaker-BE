package com.ducnt.chillshaker.dto.request.drink;

import com.ducnt.chillshaker.validator.FileConstraint;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class DrinkCreationRequest {
    @Size(min = 5, message = "")
    String name;
    @Size(min = 12, message = "")
    String description;
    @Min(value = 1000, message = "Price must be at least 1000")
    double price;
    UUID drinkCategoryId;
    List<MultipartFile> files;
}
