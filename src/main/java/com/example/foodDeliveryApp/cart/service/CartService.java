package com.example.foodDeliveryApp.cart.service;

import com.example.foodDeliveryApp.cart.dto.ProductToCartRequest;
import com.example.foodDeliveryApp.cart.model.Cart;
import com.example.foodDeliveryApp.product.model.Product;

public interface CartService {
    Cart createCart(Long clientId);
    Cart getCartByClientId(Long clientId);
    Cart addProductToCart(Long clientId, ProductToCartRequest productToCartRequest);
    Cart removeProductFromCart(Long clientId, Long cartProductId);
}
