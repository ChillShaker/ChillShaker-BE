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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bar_id", referencedColumnName = "id")
     Bar bar;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "menu_drink",
            joinColumns = @JoinColumn(name = "menu_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "drink_id", referencedColumnName = "id")
    )
     Collection<Drink> drinks = new ArrayList<>();
}
