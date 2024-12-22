package com.ducnt.chillshaker.dto.request.account;

import com.ducnt.chillshaker.validator.DobConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AccountUpdationRequest {
    @Size(min = 3, max = 100, message = "FullName must be at range 3 to 100 character")
    String fullName;
    @Size(min = 6, message = "Password must be at least 6 character ")
    String password;
    @Pattern(regexp = "^(0|\\+84)([3|5|7|8|9])+([0-9]{8})\\b$")
    String phone;
    @NotBlank(message = "Address must not be blank")
    String address;
    @DobConstraint(min = 18)
    LocalDate dob;

    Set<String> roles = new HashSet<>();
}
