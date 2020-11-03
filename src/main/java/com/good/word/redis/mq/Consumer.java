package com.good.word.redis.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author wangxianlei
 * @since 1.0
 */
@Component
@Slf4j
public class Consumer {

    @KafkaListener(topics = "user-topic")
    private void consumer(ConsumerRecord consumerRecord) {
        Optional<Object> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        if (kafkaMessage.isPresent()) {
            Object o = kafkaMessage.get();
            System.out.println(o);
        }
    }
}
