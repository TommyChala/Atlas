package com.Hub.kafka;

import com.Hub.identity.model.IdentityModel;
import flux.events.IdentityEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);
    private final KafkaTemplate <String, byte[]> kafkaTemplate;


    public KafkaProducer(KafkaTemplate <String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEventCreate(IdentityModel identityModel) {
        IdentityEvent identityEvent = IdentityEvent.newBuilder()
                .setIdentityId(identityModel.getIdentityId())
                .setEventType("IDENTITY_CREATED")
                .build();
        try {
            kafkaTemplate.send("fluxIdentity", identityEvent.toByteArray());
        } catch (Exception e) {
            log.error("Error sending IdentityCreated event: {}", identityEvent);
        }
    }

    public void sendEventUpdate(IdentityModel identityModel) {
        IdentityEvent identityEvent = IdentityEvent.newBuilder()
                .setIdentityId(identityModel.getIdentityId())
                .setEventType("IDENTITY_UPDATED")
                .build();
        try {
            kafkaTemplate.send("fluxIdentity", identityEvent.toByteArray());
        }
        catch (Exception e) {
            log.error("Error sending IdentityUpdated event: {}", identityEvent);
        }
    }

}