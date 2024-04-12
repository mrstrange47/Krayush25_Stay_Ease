package com.crio.stayEase.controller;

import com.crio.stayEase.dto.request.HotelRequest;
import com.crio.stayEase.entity.Hotel;
import com.crio.stayEase.exception.HotelNotFoundException;
import com.crio.stayEase.exception.InvalidNumberOfRoomException;
import com.crio.stayEase.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    HotelService hotelService;

    // GET /hotels
    // Public API
    @GetMapping
    public List<Hotel> getAllHotel(){
        return hotelService.findAllHotels();
    }

    // POST /hotels
    // Only Admin can Add Hotel
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> createHotel(@RequestBody HotelRequest hotelRequest) throws InvalidNumberOfRoomException {
        Hotel hotel = null;
        try {
            hotel = hotelService.addHotel(hotelRequest);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok(hotel);
    }

    // DELETE /hotels/{hotelId}
    // Only Admin can Delete Hotel
    @DeleteMapping("/{hotelId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteHotel(@PathVariable Long hotelId) throws HotelNotFoundException{
        String msg = "";
        try{
            msg = hotelService.deleteHotelById(hotelId);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok(msg);
    }

    // PUT /hotels/{hotelId}
    @PutMapping("/{hotelId}")
    @PreAuthorize("hasAuthority('HOTEL_MANAGER')")
    public ResponseEntity<Object> updateHotel(@PathVariable("hotelId") Long hotelId, @RequestBody HotelRequest hotelRequest) throws HotelNotFoundException, InvalidNumberOfRoomException {
        Hotel hotel = null;
        try{
            hotel = hotelService.updateHotelById(hotelId, hotelRequest);
        }
        catch (InvalidNumberOfRoomException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch (HotelNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok(hotel);
    }
}
