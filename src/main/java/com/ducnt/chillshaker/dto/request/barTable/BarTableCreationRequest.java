package com.ducnt.chillshaker.dto.request.barTable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BarTableCreationRequest {
    String name;
    UUID barId;
    UUID tableTypeId;
}
