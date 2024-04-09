package com.legion.mediaservice.entities.event;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "varchar(36)",length = 36, updatable = false, nullable = false)
    private UUID id;

    private String name;

    private String address;
    private String city;
    private String county;
    private String postalCode;

    private String state;

    private String country;

    private Double latitude;
    private Double longitude;

}
