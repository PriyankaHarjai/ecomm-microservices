package com.ecomm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CartResponse {
    private Long cartId;
    private String message;
}
