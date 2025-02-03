package com.ducnt.chillshaker.repository;

import com.ducnt.chillshaker.enums.BookingStatusEnum;
import com.ducnt.chillshaker.model.BookingTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingTableRepository extends JpaRepository<BookingTable, UUID> {
    List<BookingTable> findAllByBookingBookingDateAndBookingBookingTimeAndBookingStatus(LocalDate bookingDate,
                                                                                        LocalTime bookingTime, BookingStatusEnum bookingStatus);
}
