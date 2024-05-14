package com.zuzex.storageservice.model.dto;

import lombok.*;

import java.util.UUID;

@Builder
public record RequestReservation(
        UUID clientId,
        Long productId,
        Integer amount
){}
