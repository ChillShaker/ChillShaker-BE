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
public class BarTable extends BaseModel{
    String name;
    int status;
    boolean isActive;
    boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bar_id", referencedColumnName = "id")
    Bar bar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tableType_id", referencedColumnName = "id")
    TableType tableType;

    @OneToMany(mappedBy = "barTable", cascade = CascadeType.ALL)
    Collection<BookingTable> bookingTables = new ArrayList<>();
}
