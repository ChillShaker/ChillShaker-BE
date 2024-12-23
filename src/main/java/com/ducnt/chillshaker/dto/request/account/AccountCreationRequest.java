package com.ducnt.chillshaker.dto.request.account;

import com.ducnt.chillshaker.validator.DobConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AccountCreationRequest {
    @Size(min = 4, message = "FULLNAME_INVALID")
    String fullName;
    @Email(message = "EMAIL_INVALID")
    String email;
    @Size(min = 6, message = "PASSWORD_INVALID")
    String password;
    @Pattern(regexp = "^(0|\\+84)([3|5|7|8|9])+([0-9]{8})\\b$", message = "PHONE_INVALID")
    String phone;
    @NotBlank(message = "Address must not be blank")
    String address;
    @DobConstraint(min = 18, message = "DOB_INVALID")
    LocalDate dob;
}
