package com.ducnt.chillshaker.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.HashSet;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Role extends BaseModel{
     String name;

    @ManyToMany(mappedBy = "roles")
     Collection<Account> accounts = new HashSet<>();

    public enum RoleName {
        ADMIN,
        USER
    }
}
