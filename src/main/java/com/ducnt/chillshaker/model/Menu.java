package com.ducnt.chillshaker.model;

import jakarta.persistence.*;
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
public class Menu extends BaseModel{
     String name;
     String description;
     String images;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bar_id", referencedColumnName = "id")
     Bar bar;
}
