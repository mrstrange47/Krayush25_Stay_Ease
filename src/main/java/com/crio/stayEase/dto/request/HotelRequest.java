package com.crio.stayEase.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelRequest {
    private String name;
    private String location;
    private String description;
    private Integer numberOfAvailableRooms;
}
