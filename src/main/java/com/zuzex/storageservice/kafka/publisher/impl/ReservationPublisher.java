package com.zuzex.storageservice.kafka.publisher.impl;

import com.zuzex.storageservice.kafka.publisher.MessagePublisher;
import com.zuzex.storageservice.model.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * Отправляет в топик сообщения для Магазина о том успешно ли прошёл резерв товара
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationPublisher implements MessagePublisher<MessageResponse> {

    @Value("${kafka.topics.shop-service.response_reservation}")
    private String topicName;
    private final KafkaTemplate<String, MessageResponse> kafkaTemplate;

    @Override
    public void publish(MessageResponse message) {
        CompletableFuture<SendResult<String, MessageResponse>> future = kafkaTemplate.send(topicName, message);
        future.whenComplete((result, ex) -> {
            if(ex==null) {
                log.info("Sent message to topic [" + topicName +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }else {
                log.error("Unable to send message to topic [" + topicName + "] due to : " + ex.getMessage());
            }
        });
    }
}
