package com.crio.stayEase.service;

import com.crio.stayEase.dto.request.HotelRequest;
import com.crio.stayEase.entity.Hotel;
import com.crio.stayEase.exception.HotelNotFoundException;
import com.crio.stayEase.exception.InvalidNumberOfRoomException;
import com.crio.stayEase.repository.HotelRepository;
import com.crio.stayEase.service.implementation.HotelServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class HotelServiceTest {

    @InjectMocks
    HotelServiceImpl hotelService;

    @Mock
    HotelRepository hotelRepository;

    @Test
    public void findAllHotelsTest(){
        Mockito.when(hotelRepository.findAll()).thenReturn(new ArrayList<>());
        List<Hotel> hotels = hotelService.findAllHotels();
        Assertions.assertNotNull(hotels);
        Assertions.assertSame(0, hotels.size());
    }

    @Test
    public void addHotelTest1() throws InvalidNumberOfRoomException {
        Hotel hotel = new Hotel(1L, "Hotel1", "Lucknow", "test", 2);
        Mockito.when(hotelRepository.save(any())).thenReturn(hotel);
        HotelRequest req = new HotelRequest("Hotel1", "Lucknow", "test", 2);
        Hotel result = hotelService.addHotel(req);
        Assertions.assertNotNull(result);
        Assertions.assertSame(2, result.getNumberOfAvailableRooms());
    }

    @Test
    public void addHotelTest2() throws InvalidNumberOfRoomException {
        Hotel hotel = new Hotel(1L, "Hotel1", "Lucknow", "test", 2);
        Mockito.lenient().when(hotelRepository.save(any())).thenReturn(hotel);
        HotelRequest req = new HotelRequest("Hotel1", "Lucknow", "test", -2);

        try {
            Hotel result = hotelService.addHotel(req);
        }
        catch (InvalidNumberOfRoomException e){
            Assertions.assertSame("Number of Rooms can't be negative", e.getMessage());
        }
    }

    @Test
    public void deleteHotelByIdTest() throws HotelNotFoundException {
        Hotel hotel = new Hotel(1L, "Hotel1", "Lucknow", "test", 2);
        Mockito.when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        Mockito.doNothing().when(hotelRepository).deleteById(1L);
        String result = hotelService.deleteHotelById(1L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result,"Hotel with id " + hotel.getId() + " has been deleted successfully.");
    }

    @Test
    public void deleteHotelByIdTestException() throws HotelNotFoundException {
        Hotel hotel = new Hotel(1L, "Hotel1", "Lucknow", "test", 2);
        Mockito.when(hotelRepository.findById(1L)).thenReturn(Optional.empty());
        try{
            String result = hotelService.deleteHotelById(1L);
        }
        catch (Exception e){
            Assertions.assertEquals(e.getMessage(),"Hotel not found with id: " + hotel.getId());
        }
    }
}
