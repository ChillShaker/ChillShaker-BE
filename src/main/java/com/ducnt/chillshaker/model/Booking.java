package com.ducnt.chillshaker.model;

import com.ducnt.chillshaker.enums.BookingStatusEnum;
import com.ducnt.chillshaker.enums.BookingTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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
    LocalDateTime expireAt;
    BookingStatusEnum status;
    BookingTypeEnum bookingType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bar_id", referencedColumnName = "id")
    Bar bar;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    Collection<BookingTable> bookingTables = new ArrayList<>();

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    Collection<BookingDrink> bookingDrinks = new ArrayList<>();

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    Collection<BookingMenu> bookingMenus = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    Account account;

    @OneToOne(fetch = FetchType.LAZY)
    Payment payment;
}
