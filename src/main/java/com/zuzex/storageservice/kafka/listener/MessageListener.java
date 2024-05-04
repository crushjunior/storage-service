package com.zuzex.storageservice.kafka.listener;

public interface MessageListener<T> {

    void listenMessage(T message);
}
