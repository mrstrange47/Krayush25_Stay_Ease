package com.crio.stayEase.exception;

public class HotelNotFoundException extends RuntimeException{
    public HotelNotFoundException(String msg){
        super(msg);
    }
}
