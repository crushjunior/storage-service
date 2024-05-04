package com.zuzex.storageservice.model.dto;

import lombok.Builder;

public record MessageResponse(String message) {
    @Builder
    public MessageResponse{
    }
}
