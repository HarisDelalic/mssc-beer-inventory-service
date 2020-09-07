package com.dela.brewery.models;

import com.dela.brewery.models.beer_order.BeerOrderDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class OrderAllocationRequest {

    private final BeerOrderDto beerOrderDto;
}
