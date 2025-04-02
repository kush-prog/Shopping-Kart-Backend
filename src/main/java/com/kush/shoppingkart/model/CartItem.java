package com.kush.shoppingkart.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "product_Id")
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_Id")
    private Cart cart;
    
    public void setTotalPrice() {
    	this.totalPrice = this.unitPrice.multiply(new BigDecimal(quantity));
    }
}

