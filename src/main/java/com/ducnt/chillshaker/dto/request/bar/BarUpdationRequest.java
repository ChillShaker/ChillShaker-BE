package com.ducnt.chillshaker.dto.request.bar;

import com.ducnt.chillshaker.dto.request.barTime.BarTimeUpdationRequest;
import com.ducnt.chillshaker.model.BarTime;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BarUpdationRequest {
    @Size(min = 4, message = "FULLNAME_INVALID")
    String name;
    @Email(message = "EMAIL_INVALID")
    String email;
    @Size(min = 12, message = "Description of bar must be at least 12 character")
    String description;
    @Pattern(regexp = "^(0|\\+84)([3|5|7|8|9])+([0-9]{8})\\b$", message = "PHONE_INVALID")
    String phone;
    @NotBlank(message = "Address must not be blank")
    String address;

    List<String> oldFileUrls;
    List<MultipartFile> newFiles = new ArrayList<>();

    List<BarTimeUpdationRequest> barTimes = new ArrayList<>();
}
