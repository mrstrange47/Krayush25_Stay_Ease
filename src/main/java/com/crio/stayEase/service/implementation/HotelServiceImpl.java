package com.crio.stayEase.service.implementation;

import com.crio.stayEase.dto.request.HotelRequest;
import com.crio.stayEase.entity.Hotel;
import com.crio.stayEase.exception.HotelNotFoundException;
import com.crio.stayEase.exception.InvalidNumberOfRoomException;
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
    public Hotel addHotel(HotelRequest hotelRequest) throws InvalidNumberOfRoomException {
        if(hotelRequest.getNumberOfAvailableRooms() == null){
            hotelRequest.setNumberOfAvailableRooms(0);
        }

        if(hotelRequest.getNumberOfAvailableRooms() < 0){
            throw new InvalidNumberOfRoomException("Number of Rooms can't be negative");
        }

        Hotel hotel = new Hotel();
        hotel.setName(hotelRequest.getName());
        hotel.setLocation(hotelRequest.getLocation());
        hotel.setDescription(hotelRequest.getDescription());
        hotel.setNumberOfAvailableRooms(hotelRequest.getNumberOfAvailableRooms());
        log.info("New Hotel added");
        return hotelRepository.save(hotel);
    }

    @Override
    public String deleteHotelById(Long hotelId) throws HotelNotFoundException{
        Optional<Hotel> optionalHotel = hotelRepository.findById(hotelId);
        if (optionalHotel.isPresent()) {
            hotelRepository.deleteById(hotelId);
            log.info("Hotel Deleted");
            return "Hotel with id " + hotelId + " has been deleted successfully.";
        }
        else{
            throw new HotelNotFoundException("Hotel not found with id: " + hotelId);
        }
    }

    @Override
    public Hotel updateHotelById(Long hotelId, HotelRequest hotelRequest) throws HotelNotFoundException, InvalidNumberOfRoomException {
        if(hotelRequest.getNumberOfAvailableRooms() < 0){
            throw new InvalidNumberOfRoomException("Number of Rooms can't be negative");
        }

        Optional<Hotel> optionalHotel = hotelRepository.findById(hotelId);
        if (optionalHotel.isPresent()) {
            Hotel existingHotel = optionalHotel.get();
            existingHotel.setName(hotelRequest.getName());
            existingHotel.setLocation(hotelRequest.getLocation());
            existingHotel.setDescription(hotelRequest.getDescription());
            existingHotel.setNumberOfAvailableRooms(hotelRequest.getNumberOfAvailableRooms());
            log.info("Hotel details updated");
            return hotelRepository.save(existingHotel);
        }
        else {
            throw new HotelNotFoundException("Hotel not found with id: " + hotelId);
        }
    }
}
