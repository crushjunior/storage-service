package com.zuzex.storageservice.model.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ReservationDto(
        UUID clientId,
        Long productId,
        Integer amount
) {
}
