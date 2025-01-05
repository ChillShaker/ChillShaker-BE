package com.ducnt.chillshaker.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TableType extends BaseModel{
    String name;
    String description;
    int limitOfPeople;
    double depositAmount;
    String image;

    @OneToMany(mappedBy = "tableType", cascade = CascadeType.ALL)
    Collection<BarTable> barTables = new ArrayList<>();
}
