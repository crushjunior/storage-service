package com.zuzex.storageservice.service;
import com.zuzex.storageservice.model.dto.RequestReservation;

public interface ReservationProcessingService {
    void processReserve(RequestReservation reservationDto);
}
