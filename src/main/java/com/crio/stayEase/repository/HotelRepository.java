package com.crio.stayEase.repository;

import com.crio.stayEase.entity.Hotel;
import com.crio.stayEase.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Hotel findByName(String name);
}
