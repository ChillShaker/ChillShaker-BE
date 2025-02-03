package com.ducnt.chillshaker.dto.response.bar;

import com.ducnt.chillshaker.dto.response.barTable.BarTableResponse;
import com.ducnt.chillshaker.dto.response.barTime.BarTimeResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BarResponse {
    String name;
    String email;
    String description;
    String address;
    String phone;
    String image;
    boolean status;

    List<BarTimeResponse> barTimes;
}