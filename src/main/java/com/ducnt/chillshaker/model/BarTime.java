package com.ducnt.chillshaker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class BarTime extends BaseModel {
     int dayOfWeek;
     LocalTime startTime;
     LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "bar_id", referencedColumnName = "id")
     Bar bar;
}
