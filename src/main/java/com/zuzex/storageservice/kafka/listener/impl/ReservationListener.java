package com.zuzex.storageservice.kafka.listener.impl;

import com.zuzex.storageservice.kafka.listener.MessageListener;
import com.zuzex.storageservice.model.dto.RequestReservation;
import com.zuzex.storageservice.service.ReservationProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Слушает топик, в который отправляются сообщения от Магазина о резерве товара
 */

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(topics = "${kafka.topics.shop-service.request_reservation}",
        groupId = "${spring.kafka.consumer.group-id}")
public class ReservationListener implements MessageListener<RequestReservation> {

    private final ReservationProcessingService reservationProcessingService;

    @Override
    @KafkaHandler
    @KafkaListener(topics = "${kafka.topics.shop-service.request_reservation}")
    public void listenMessage(RequestReservation reservationDto) {
        log.info("Received available count goods of product: {}" + reservationDto.productId());
        reservationProcessingService.processReserve(reservationDto);
    }
}
