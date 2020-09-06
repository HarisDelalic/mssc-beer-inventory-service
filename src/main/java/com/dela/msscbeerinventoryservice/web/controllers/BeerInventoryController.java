package com.dela.msscbeerinventoryservice.web.controllers;

import com.dela.msscbeerinventoryservice.repositories.BeerInventoryRepository;
import com.dela.msscbeerinventoryservice.web.mappers.BeerInventoryMapper;
import com.dela.brewery.models.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by jt on 2019-05-31.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerInventoryController {

    private final BeerInventoryRepository beerInventoryRepository;
    private final BeerInventoryMapper beerInventoryMapper;

    @GetMapping("api/v1/msscbeerorderservice/{beerId}/inventory")
    List<BeerInventoryDto> listBeersById(@PathVariable UUID beerId){
        log.debug("Finding Inventory for beerId:" + beerId);

        return beerInventoryRepository.findAllByBeerId(beerId)
                .stream()
                .map(beerInventoryMapper::beerInventoryToBeerInventoryDto)
                .collect(Collectors.toList());
    }

    @GetMapping("api/v1/msscbeerorderservice/{beerId}/total-quantity")
    Integer getTotalQuantity(@PathVariable UUID beerId){

        return beerInventoryRepository.findAllByBeerId(beerId)
                .stream()
                .mapToInt(beerInventory -> beerInventory.getQuantityOnHand())
                .sum();
    }
}
