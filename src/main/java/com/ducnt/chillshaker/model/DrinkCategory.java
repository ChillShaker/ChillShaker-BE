package com.ducnt.chillshaker.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class DrinkCategory extends BaseModel{
     String name;
     String description;

    @OneToMany(mappedBy = "drinkCategory", cascade = CascadeType.ALL)
     Collection<Drink> drinks;
}
