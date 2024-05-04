package com.zuzex.storageservice.service.impl;

import com.zuzex.storageservice.exception.ResourceNotFoundException;
import com.zuzex.storageservice.kafka.publisher.MessagePublisher;
import com.zuzex.storageservice.model.dto.MessageResponse;
import com.zuzex.storageservice.model.dto.ReservationDto;
import com.zuzex.storageservice.model.entity.Product;
import com.zuzex.storageservice.model.entity.Reservation;
import com.zuzex.storageservice.service.ProductService;
import com.zuzex.storageservice.service.ReservationProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationProcessingServiceImpl implements ReservationProcessingService {

    @Value("${app.reserveExpirationMs}")
    private int reserveExpirationMs;

    private final ProductService productService;

    private final MessagePublisher reservationPublisher;


    @Override
    @Transactional
    public void processReserve(ReservationDto reservationDto) {
        try {
            int availableCount = productService.getQuantityGoods(reservationDto.productId());

            if (availableCount == 0) {
                MessageResponse messageResponse = new MessageResponse
                        ("No available items for reserve product " + reservationDto.productId());
                reservationPublisher.publish(messageResponse);

            } else if (availableCount < reservationDto.productId()) {
                MessageResponse messageResponse = new MessageResponse
                        ("You must enter a smaller number of products");
                reservationPublisher.publish(messageResponse);

            } else {
                Product product = productService.getProductById(reservationDto.productId());

                Reservation reservation = Reservation.builder()
                        .amount(reservationDto.amount())
                        .clientId(reservationDto.clientId())
                        .product(product)
                        .expirationDate(new Date((new Date()).getTime() + reserveExpirationMs).toInstant())
                        .build();

                product.addReservation(reservation);
                productService.saveProduct(product);

                MessageResponse messageResponse = new MessageResponse
                        ("Successfully reserved " + reservationDto.amount() + " products");
                reservationPublisher.publish(messageResponse);
            }
        } catch (ResourceNotFoundException e) {
            reservationPublisher.publish(new MessageResponse(e.getMessage()));
        }
    }
}
