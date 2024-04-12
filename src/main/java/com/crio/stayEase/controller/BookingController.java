package com.crio.stayEase.controller;

import com.crio.stayEase.entity.Booking;
import com.crio.stayEase.entity.User;
import com.crio.stayEase.exception.BookingNotFoundException;
import com.crio.stayEase.exception.HotelNotFoundException;
import com.crio.stayEase.exception.RoomUnavailableException;
import com.crio.stayEase.exception.UserNotFoundException;
import com.crio.stayEase.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {

    @Autowired
    BookingService bookingService;

    // POST /hotels/{hotelId}/book
    @PostMapping("/hotels/{hotelId}/book")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<String> bookRoom(@PathVariable Long hotelId) throws UserNotFoundException, HotelNotFoundException, RoomUnavailableException {
        String msg = "";
        try{
            msg = bookingService.bookHotelRoom(hotelId);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok(msg);
    }

    // DELETE /bookings/{bookingId}
    @DeleteMapping("/bookings/{bookingId}")
    @PreAuthorize("hasAuthority('HOTEL_MANAGER')")
    public ResponseEntity<String> deleteBooking(@PathVariable Long bookingId) throws UserNotFoundException, BookingNotFoundException {
        String msg = "";
        try{
            msg = bookingService.deleteBooking(bookingId);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok(msg);
    }
}
