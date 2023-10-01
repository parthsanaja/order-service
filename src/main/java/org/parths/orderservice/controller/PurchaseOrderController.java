package org.parths.orderservice.controller;

import org.parths.orderservice.dto.PurchaseOrderDto;
import org.parths.orderservice.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("order")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @PostMapping
    public Mono<PurchaseOrderDto> order(@RequestBody Mono<PurchaseOrderDto> purchaseOrderDtoMono){
        return purchaseOrderService.createPurchaseOrder(purchaseOrderDtoMono);
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<PurchaseOrderDto>> getOrder(@PathVariable Integer id){
        return purchaseOrderService.getPurchaseOrder(id)
                .map(purchaseOrderDto -> ResponseEntity.ok().body(purchaseOrderDto))
                .onErrorResume( throwable ->
                        Mono.just(ResponseEntity.internalServerError().body(new PurchaseOrderDto()))
                )
                .switchIfEmpty(Mono.just(ResponseEntity.badRequest().body(new PurchaseOrderDto())));
    }
}
