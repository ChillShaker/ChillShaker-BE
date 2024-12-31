package com.ducnt.chillshaker.dto.response.barTable;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BarTableManagementResponse extends BarTableResponse{
    boolean isDeleted;
}
