package com.zuzex.storageservice.kafka.listener.impl;

import com.zuzex.storageservice.kafka.listener.MessageListener;
import com.zuzex.storageservice.model.dto.ReservationDto;
import com.zuzex.storageservice.service.ReservationProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Слушает топик, в который отправляются сообщения от Магазина о резерве товара
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationListener implements MessageListener<ReservationDto> {

    private final ReservationProcessingService reservationProcessingService;

    @Override
    @KafkaListener(topics = "${kafka.topics.shop-service.request_reservation}",
            groupId = "${spring.kafka.consumer.group-id.my-consumer-group-id}")
    public void listenMessage(ReservationDto reservationDto) {
        log.info("Received available count goods of product: {}" + reservationDto.productId());
        reservationProcessingService.processReserve(reservationDto);
    }
}
