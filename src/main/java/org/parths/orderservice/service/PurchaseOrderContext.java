package org.parths.orderservice.service;

import lombok.Data;
import lombok.ToString;
import org.parths.orderservice.dto.PurchaseOrderDto;
import org.parths.productservicev2.dto.ProductDto;
import org.parths.userservice.dto.UserTransactionRequestDto;
import org.parths.userservice.dto.UserTransactionResponseDto;

@Data
@ToString
public class PurchaseOrderContext {

    private PurchaseOrderDto purchaseOrderDto;

    private ProductDto productDto;

    private UserTransactionResponseDto userTransactionResponseDto;

    public PurchaseOrderContext(PurchaseOrderDto purchaseOrderDto) {
        this.purchaseOrderDto = purchaseOrderDto;
    }

    public UserTransactionRequestDto createUserTransactionRequest(){
        UserTransactionRequestDto userTransactionRequestDto = new UserTransactionRequestDto();
        userTransactionRequestDto.setUserId(purchaseOrderDto.getUserId());
        userTransactionRequestDto.setAmount(purchaseOrderDto.getAmount());
        return userTransactionRequestDto;
    }

}
