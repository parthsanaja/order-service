package org.parths.orderservice.service;

import org.parths.orderservice.dto.OrderStatus;
import org.parths.orderservice.dto.PurchaseOrderDto;
import org.parths.orderservice.entity.PurchaseOrder;
import org.parths.orderservice.mapper.PurchaseOrderMapper;
import org.parths.orderservice.repository.OrderRepository;
import org.parths.productservicev2.client.ProductServiceClient;
import org.parths.userservice.client.UserServiceClient;
import org.parths.userservice.dto.UserTransactionResponseDto;
import org.parths.userservice.enums.UserTranStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class PurchaseOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductServiceClient productServiceClient;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;

    public Mono<PurchaseOrderDto> createPurchaseOrder(Mono<PurchaseOrderDto> purchaseOrderDtoMono) {
        return purchaseOrderDtoMono.map(
                PurchaseOrderContext::new
        ).flatMap(this::populateProductDto)
        .doOnNext(context -> {
            PurchaseOrderDto purchaseOrderDto = context.getPurchaseOrderDto();
            purchaseOrderDto.setAmount(purchaseOrderDto.getQty() * context.getProductDto().getPrice());
        })
        .flatMap(this::postTransaction)
        .flatMap(this::processOrder);
    }

    private Mono<PurchaseOrderContext> populateProductDto(PurchaseOrderContext context){

        return productServiceClient.getProduct(context.getPurchaseOrderDto().getProductId())
                .doOnNext(productDto -> {
                    context.setProductDto(productDto);
                    context.getPurchaseOrderDto().setProductPrice(productDto.getPrice());
                })
                .thenReturn(context);

    }

    private Mono<PurchaseOrderContext> postTransaction(PurchaseOrderContext context){
        return userServiceClient.createUserTransaction(
                        context.createUserTransactionRequest()
                )
                .doOnNext(context::setUserTransactionResponseDto)
                .thenReturn(context);
    }

    private Mono<PurchaseOrderDto> processOrder(PurchaseOrderContext context){
        PurchaseOrderDto purchaseOrderDto = context.getPurchaseOrderDto();
        UserTransactionResponseDto userTransactionResponseDto = context.getUserTransactionResponseDto();
        if(userTransactionResponseDto.getStatus() != UserTranStatus.Success){
            purchaseOrderDto.setStatus(OrderStatus.Payment_Error);
            return Mono.just(purchaseOrderDto);
        }else{
            PurchaseOrder purchaseOrder = purchaseOrderMapper.dtoToPurchaseOrder(purchaseOrderDto);
            purchaseOrder.setStatus(OrderStatus.Completed);
            return Mono.just(
                    purchaseOrderMapper.purchaseOrderToDto(
                    orderRepository.save(purchaseOrder)))
                    .subscribeOn(Schedulers.boundedElastic());

        }
    }

}
