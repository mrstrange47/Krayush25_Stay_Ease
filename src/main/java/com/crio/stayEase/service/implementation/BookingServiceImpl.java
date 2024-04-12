package com.crio.stayEase.service.implementation;

import com.crio.stayEase.entity.Booking;
import com.crio.stayEase.entity.Hotel;
import com.crio.stayEase.entity.User;
import com.crio.stayEase.exception.BookingNotFoundException;
import com.crio.stayEase.exception.HotelNotFoundException;
import com.crio.stayEase.exception.RoomUnavailableException;
import com.crio.stayEase.exception.UserNotFoundException;
import com.crio.stayEase.repository.BookingRepository;
import com.crio.stayEase.repository.HotelRepository;
import com.crio.stayEase.repository.UserRepository;
import com.crio.stayEase.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    HotelRepository hotelRepository;


    @Override
    public String bookHotelRoom(Long hotelId) throws UserNotFoundException, HotelNotFoundException, RoomUnavailableException {
        Hotel hotel = null;
        User user = getLoggedInUserDetails();

        if(user.getId() == null){
            throw new UserNotFoundException("User Not Found");
        }

        Optional<Hotel> existingHotel = hotelRepository.findById(hotelId);
        if (existingHotel.isPresent()) {
            hotel = existingHotel.get();
        }
        else {
            throw new HotelNotFoundException("Hotel not found with id: " + hotelId);
        }

        if(hotel.getNumberOfAvailableRooms() < 1){
            throw new RoomUnavailableException("Room Unavailable for Hotel: " + hotel.getName());
        }

        Booking booking = new Booking();
        booking.setHotel(hotel);
        booking.setUser(user);
        bookingRepository.save(booking);
        log.info("Room Booked");
        return "Room in " + hotel.getName() +" booked By CUSTOMER " + user.getFirstName() + " " + user.getLastName();
    }

    @Override
    public String deleteBooking(Long bookingId) throws UserNotFoundException, BookingNotFoundException {
        Booking booking = null;
        User user = getLoggedInUserDetails();

        if(user.getId() == null){
            throw new UserNotFoundException("User Not Found");
        }

        Optional<Booking> existingBooking = bookingRepository.findById(bookingId);
        if (existingBooking.isPresent()) {
            booking = existingBooking.get();
        }
        else {
            throw new BookingNotFoundException("Booking not found with id: " + bookingId);
        }

        Hotel hotel = booking.getHotel();

        hotel.setNumberOfAvailableRooms(hotel.getNumberOfAvailableRooms() + 1);
        hotelRepository.save(hotel);

        log.info("Delete Booking");
        return booking.getHotel().getName() +" Canceled By HOTEL MANAGER " + user.getFirstName() + " " + user.getLastName();
    }

    @Bean
    public static User getLoggedInUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return (User) authentication.getPrincipal();
    }
}
