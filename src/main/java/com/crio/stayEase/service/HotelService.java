package com.crio.stayEase.service;

import com.crio.stayEase.dto.request.HotelRequest;
import com.crio.stayEase.entity.Hotel;
import com.crio.stayEase.exception.HotelNotFoundException;

import java.util.List;

public interface HotelService {
    List<Hotel> findAllHotels();
    Hotel addHotel(HotelRequest hotelRequest);
    String deleteHotelById(Long hotelId) throws HotelNotFoundException;
    Hotel updateHotelById(Long hotelId, HotelRequest hotelRequest) throws HotelNotFoundException;
}
