package com.ducnt.chillshaker.dto.response.tableType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TableTypeResponse {
    UUID id;
    String name;
    String description;
    int capacity;
    double depositAmount;
    String image;
}
