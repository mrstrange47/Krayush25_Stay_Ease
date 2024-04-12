package com.crio.stayEase.service;

import com.crio.stayEase.entity.Booking;
import com.crio.stayEase.exception.BookingNotFoundException;
import com.crio.stayEase.exception.HotelNotFoundException;
import com.crio.stayEase.exception.RoomUnavailableException;
import com.crio.stayEase.exception.UserNotFoundException;

public interface BookingService {
    String bookHotelRoom(Long hotelId) throws UserNotFoundException, HotelNotFoundException, RoomUnavailableException;
    String deleteBooking(Long bookingId) throws UserNotFoundException, BookingNotFoundException;
}
