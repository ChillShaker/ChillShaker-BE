package com.ducnt.chillshaker.dto.request.tableType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TableTypeUpdationRequest {
    @Size(min = 6, message = "BarTable Type's name must be at least 6 character")
    String name;
    @Size(min = 6, message = "BarTable Type's description must be at least 6 character")
    String description;
    @Min(value = 1, message = "BarTable Type's limit of people must be at least 1")
    int limitOfPeople;

    List<String> oldFileUrls;
    List<MultipartFile> newFiles;
}
