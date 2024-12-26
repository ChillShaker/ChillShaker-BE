package com.ducnt.chillshaker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Food extends BaseModel{
    String name;
    String description;
    double price;
    String image;
    boolean status;

    @ManyToMany(mappedBy = "foods")
    Collection<Menu> menus = new ArrayList<>();
}
