package com.ducnt.chillshaker.dto.response.drink;

import com.ducnt.chillshaker.dto.response.drinkCategory.DrinkCategoryResponse;
import com.ducnt.chillshaker.model.DrinkCategory;
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
public class DrinkResponse {
    UUID id;
    String name;
    String description;
    double price;
    String image;

    //Collection<Menu> menus = new ArrayList<>();

    DrinkCategoryResponse drinkCategory;
}
