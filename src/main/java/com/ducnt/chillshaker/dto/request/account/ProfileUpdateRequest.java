package com.ducnt.chillshaker.dto.request.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ProfileUpdateRequest {
    @Size(min = 4, message = "FULLNAME_INVALID")
    String fullName;
    @Email(message = "EMAIL_INVALID")
    String email;

    @Pattern(regexp = "^(0|\\+84)([3|5|7|8|9])+([0-9]{8})\\b$", message = "PHONE_INVALID")
    String phone;
    @NotBlank(message = "Address must not be blank")
    String address;

    List<String> oldFileUrls;
    List<MultipartFile> newFiles;
}
