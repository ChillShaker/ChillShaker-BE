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
public class BookingType extends BaseModel{
    String name;
    String description;

    @OneToMany(mappedBy = "bookingType", cascade = CascadeType.ALL)
    Collection<Booking> bookings = new ArrayList<>();
}
