package com.crio.stayEase.service;

import com.crio.stayEase.entity.Booking;
import com.crio.stayEase.entity.Hotel;
import com.crio.stayEase.entity.User;
import com.crio.stayEase.enums.Role;
import com.crio.stayEase.exception.BookingNotFoundException;
import com.crio.stayEase.exception.HotelNotFoundException;
import com.crio.stayEase.exception.RoomUnavailableException;
import com.crio.stayEase.exception.UserNotFoundException;
import com.crio.stayEase.repository.BookingRepository;
import com.crio.stayEase.repository.HotelRepository;
import com.crio.stayEase.service.implementation.BookingServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private HotelRepository hotelRepository;

    @Test
    public void bookHotelRoomTest() throws UserNotFoundException, HotelNotFoundException, RoomUnavailableException {
        Hotel hotel = new Hotel(1L, "Hotel1", "Lucknow", "test", 2);
        User user = new User(1L,"test-email","123","firstname","lastname",Role.ADMIN);
        try(MockedStatic<BookingServiceImpl> utilities  = Mockito.mockStatic(BookingServiceImpl.class)){
            utilities.when(BookingServiceImpl::getLoggedInUserDetails).thenReturn(user);
            Assertions.assertNotNull(BookingServiceImpl.getLoggedInUserDetails());
            Mockito.when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
            Mockito.when(bookingRepository.save(any())).thenReturn(new Booking());
            String result = bookingService.bookHotelRoom(1L);
            Assertions.assertNotNull(result);
            Assertions.assertEquals(result,"Room in " + hotel.getName() +" booked By CUSTOMER " + user.getFirstName() + " " + user.getLastName());
        }

    }

    @Test
    public void bookHotelRoomTest1() throws UserNotFoundException, HotelNotFoundException, RoomUnavailableException {
        Hotel hotel = new Hotel(1L, "Hotel1", "Lucknow", "test", 2);
        User user = new User(1L,"test-email","123","firstname","lastname",Role.ADMIN);
        try(MockedStatic<BookingServiceImpl> utilities  = Mockito.mockStatic(BookingServiceImpl.class)){
            utilities.when(BookingServiceImpl::getLoggedInUserDetails).thenReturn(user);
        }
        try {
            String result = bookingService.bookHotelRoom(1L);
        }
        catch (Exception e){
            Assertions.assertEquals(e.getMessage(),"User Not Found");
        }

    }

    @Test
    public void deleteHotelRoomTest() throws UserNotFoundException, BookingNotFoundException {
        Hotel hotel = new Hotel(1L, "Hotel1", "Lucknow", "test", 2);
        User user = new User(1L,"test-email","123","firstname","lastname",Role.ADMIN);
        Booking booking = new Booking(1L,user,hotel);
        try(MockedStatic<BookingServiceImpl> utilities  = Mockito.mockStatic(BookingServiceImpl.class)){
            utilities.when(BookingServiceImpl::getLoggedInUserDetails).thenReturn(user);
            Assertions.assertNotNull(BookingServiceImpl.getLoggedInUserDetails());

            Mockito.when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
            Mockito.when(hotelRepository.save(any())).thenReturn(hotel);
            String result = bookingService.deleteBooking(1L);
            Assertions.assertNotNull(result);
            Assertions.assertEquals(result,booking.getHotel().getName() +" Canceled By HOTEL MANAGER " + user.getFirstName() + " " + user.getLastName());
        }

    }
}
