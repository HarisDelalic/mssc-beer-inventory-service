package com.dela.msscbeerinventoryservice.web.services;

import com.dela.brewery.models.beer_order.BeerOrderDto;

public interface AllocationService {
    boolean allocateOrder(BeerOrderDto beerOrderDto);
}
