package com.ducnt.chillshaker.dto.response.barTable;

import com.ducnt.chillshaker.dto.response.bar.BarResponse;
import com.ducnt.chillshaker.dto.response.tableType.TableTypeResponse;
import jakarta.persistence.MappedSuperclass;
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
@MappedSuperclass
public class BarTableResponse {
    UUID id;
    String name;
    int status;
    boolean isActive;

    TableTypeResponse tableType;
}
