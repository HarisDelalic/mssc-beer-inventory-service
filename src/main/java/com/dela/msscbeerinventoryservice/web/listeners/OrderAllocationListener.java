package com.dela.msscbeerinventoryservice.web.listeners;

import com.dela.brewery.events.OrderAllocationRequest;
import com.dela.brewery.events.OrderAllocationResponse;
import com.dela.brewery.models.beer_order.BeerOrderDto;
import com.dela.msscbeerinventoryservice.config.JmsConfig;
import com.dela.msscbeerinventoryservice.web.services.AllocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderAllocationListener {

    private final AllocationService allocationService;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_REQUEST_QUEUE)
    public void listenAndSendResponse(OrderAllocationRequest orderAllocationRequest) {
        BeerOrderDto beerOrderDto = orderAllocationRequest.getBeerOrderDto();

        boolean isError = false;
        boolean isPendingInventory = false;

        try {
            isPendingInventory = allocationService.allocateOrder(beerOrderDto);
        } catch (Exception e) {
            isError = true;
        } finally {
            OrderAllocationResponse orderAllocationResponse = OrderAllocationResponse.builder()
                    .beerOrderDto(beerOrderDto)
                    .error(isError)
                    .pendingInventory(isPendingInventory)
                    .build();

            jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE,
                    orderAllocationResponse);
        }
    }
}
