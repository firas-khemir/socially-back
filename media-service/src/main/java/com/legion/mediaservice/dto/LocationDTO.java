package com.legion.mediaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LocationDTO {

    private UUID id;

    private String address;
    private String city;
    private String county;
    private String postalCode;

    private String state;

    private String country;

    private Double latitude;
    private Double longitude;

}
