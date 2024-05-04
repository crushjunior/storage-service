package com.zuzex.storageservice.kafka.publisher;

public interface MessagePublisher<T> {

    void publish(T message);
}
