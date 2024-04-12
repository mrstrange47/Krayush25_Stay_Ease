package com.crio.stayEase.service.implementation;

import com.crio.stayEase.dto.request.HotelRequest;
import com.crio.stayEase.entity.Hotel;
import com.crio.stayEase.exception.HotelNotFoundException;
import com.crio.stayEase.repository.HotelRepository;
import com.crio.stayEase.service.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class HotelServiceImpl implements HotelService {

    @Autowired
    HotelRepository hotelRepository;

    @Override
    public List<Hotel> findAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel addHotel(HotelRequest hotelRequest) {
        if(hotelRequest.getNumberOfAvailableRooms() == null){
            hotelRequest.setNumberOfAvailableRooms(0);
        }


        Hotel hotel = new Hotel();
        hotel.setName(hotelRequest.getName());
        hotel.setLocation(hotelRequest.getLocation());
        hotel.setDescription(hotelRequest.getDescription());
        hotel.setNumberOfAvailableRooms(hotelRequest.getNumberOfAvailableRooms());
        return hotelRepository.save(hotel);
    }

    @Override
    public String deleteHotelById(Long hotelId) throws HotelNotFoundException{
        Optional<Hotel> optionalHotel = hotelRepository.findById(hotelId);
        if (optionalHotel.isPresent()) {
            hotelRepository.deleteById(hotelId);
            return "Hotel with id " + hotelId + " has been deleted successfully.";
        }
        else{
            throw new HotelNotFoundException("Hotel not found with id: " + hotelId);
        }
    }

    @Override
    public Hotel updateHotelById(Long hotelId, HotelRequest hotelRequest) throws HotelNotFoundException{
        Optional<Hotel> optionalHotel = hotelRepository.findById(hotelId);
        if (optionalHotel.isPresent()) {
            Hotel existingHotel = optionalHotel.get();
            existingHotel.setName(hotelRequest.getName());
            existingHotel.setLocation(hotelRequest.getLocation());
            existingHotel.setDescription(hotelRequest.getDescription());
            existingHotel.setNumberOfAvailableRooms(hotelRequest.getNumberOfAvailableRooms());
            return hotelRepository.save(existingHotel);
        }
        else {
            throw new HotelNotFoundException("Hotel not found with id: " + hotelId);
        }
    }
}
