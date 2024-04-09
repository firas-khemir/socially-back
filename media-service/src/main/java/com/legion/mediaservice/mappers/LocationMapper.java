package com.legion.mediaservice.mappers;


import com.legion.mediaservice.entities.event.Location;
import com.legion.mediaservice.dto.LocationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    LocationDTO locationTolocationDTO(Location location);

    Location locationDtoTolocation(LocationDTO locationDto);

    List<LocationDTO> locationsToLocationDTOs(List<Location> locations);

    List<Location> locationDTOsToLocations(List<LocationDTO> locationDTOs );
}
