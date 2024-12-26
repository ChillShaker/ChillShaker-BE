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
public class Table extends BaseModel{
    String name;
    int status;
    boolean isActive;
    boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bar_id", referencedColumnName = "id")
    Bar bar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_type_id", referencedColumnName = "id")
    TableType tableType;

    @OneToMany(mappedBy = "table", cascade = CascadeType.ALL)
    Collection<BookingTable> bookingTables = new ArrayList<>();
}
