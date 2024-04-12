package com.crio.stayEase;

import com.crio.stayEase.controller.BookingController;
import com.crio.stayEase.controller.HotelController;
import com.crio.stayEase.entity.Booking;
import com.crio.stayEase.entity.Hotel;
import com.crio.stayEase.exception.HotelNotFoundException;
import com.crio.stayEase.exception.InvalidNumberOfRoomException;
import com.crio.stayEase.exception.RoomUnavailableException;
import com.crio.stayEase.exception.UserNotFoundException;
import com.crio.stayEase.service.BookingService;
import com.crio.stayEase.service.HotelService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class HotelControllerTest {

    @InjectMocks
    private HotelController hotelController;

    @Mock
    private HotelService hotelService;

    @Mock
    private BookingService bookingService;

    @Mock
    private BookingController bookingController;

    private MockMvc mockMvc;

    @Test
    public void createHotelTest() throws InvalidNumberOfRoomException {
        Mockito.when(hotelService.addHotel(any())).thenReturn(new Hotel());
        ResponseEntity<Object> hotel = hotelController.createHotel(any());
        Assertions.assertNotNull(hotel);
    }

    @Test
    public void getAllHotelTest(){
        Mockito.when(hotelService.findAllHotels()).thenReturn(new ArrayList<>());
        List<Hotel> Hotels = hotelController.getAllHotel();
        Assertions.assertNotNull(Hotels);
        Assertions.assertSame(0, Hotels.size());
    }
}
