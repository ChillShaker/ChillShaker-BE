package com.ducnt.chillshaker.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Booking extends BaseModel {
    String bookingCode;
    LocalDate bookingDate;
    LocalTime bookingTime;
    String note;
    double totalPrice;
    UUID checkInStaffId;
    UUID checkOutStaffId;
    int numberOfPeople;
    LocalDate expireAt;
    int status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bar_id", referencedColumnName = "id")
    Bar bar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_type_id", referencedColumnName = "id")
    BookingType bookingType;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    Collection<BookingTable> bookingTables;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    Collection<BookingDrink> bookingDrinks;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    Collection<BookingMenu> bookingMenus;
}
