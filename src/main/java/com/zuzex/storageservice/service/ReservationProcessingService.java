package com.zuzex.storageservice.service;
import com.zuzex.storageservice.model.dto.ReservationDto;

public interface ReservationProcessingService {
    void processReserve(ReservationDto reservationDto);
}
