package com.ducnt.chillshaker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Drink extends BaseModel {
     String name;
     String description;
     double price;
     String image;
     boolean status;

    @ManyToMany(mappedBy = "drinks")
     Collection<Menu> menus = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "drinkCategory_id", referencedColumnName = "id")
     DrinkCategory drinkCategory;
}
