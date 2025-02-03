package com.ducnt.chillshaker.dto.response.barTable;

import com.ducnt.chillshaker.enums.BarTableStatusEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class BarTableStatusResponse {
    UUID id;
    BarTableStatusEnum statusEnum;
    String userEmail;
}
