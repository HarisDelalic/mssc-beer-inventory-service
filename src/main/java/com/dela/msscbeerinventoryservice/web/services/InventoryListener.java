package com.dela.msscbeerinventoryservice.web.services;

import com.dela.msscbeerinventoryservice.web.model.BeerDto;
import com.dela.events.NewInventoryEvent;
import com.dela.msscbeerinventoryservice.config.JmsConfig;
import com.dela.msscbeerinventoryservice.domain.BeerInventory;
import com.dela.msscbeerinventoryservice.repositories.BeerInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;

@RequiredArgsConstructor
@Slf4j
@Component
public class InventoryListener {

    public final BeerInventoryRepository beerInventoryRepository;

    @JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)
    public void createOrUpdateInventory(@Payload Message<NewInventoryEvent> body) throws JMSException {


        BeerDto beerDto = body.getPayload().getBeerDto();
        log.info("Saving beerInventory for beer with ID: " + beerDto.getId());
//        TODO refactor to use builder pattern
        BeerInventory beerInventory = new BeerInventory();
        beerInventory.setBeerId(beerDto.getId());
        beerInventory.setQuantityOnHand(beerDto.getQuantityOnHand());
        beerInventory.setUpc(beerDto.getUpc());



        beerInventoryRepository.save(beerInventory);
    }
}
