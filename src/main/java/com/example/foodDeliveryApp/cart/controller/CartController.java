package com.example.foodDeliveryApp.cart.controller;

import com.example.foodDeliveryApp.cart.dto.ProductToCartRequest;
import com.example.foodDeliveryApp.cart.model.Cart;
import com.example.foodDeliveryApp.cart.service.CartService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/{clientId}/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<Cart> getCartByClientId(@PathVariable("clientId") Long clientId) {
        return ResponseEntity.ok(cartService.getCartByClientId(clientId));
    }

    @PostMapping
    public ResponseEntity<Cart> createCart(@PathVariable("clientId") Long clientId) {
        return new ResponseEntity<>(cartService.createCart(clientId), HttpStatus.CREATED);
    }

    @PutMapping("/add")
    public ResponseEntity<Cart> addProductToCart(@PathVariable("clientId") Long clientId, @RequestBody ProductToCartRequest productToCartRequest) {
        return ResponseEntity.ok(cartService.addProductToCart(clientId, productToCartRequest));
    }

    @PutMapping("/remove/{cartProductId}")
    public ResponseEntity<Cart> removeProductFromCart(@PathVariable("clientId") Long clientId, @PathVariable Long cartProductId) {
        return ResponseEntity.ok(cartService.removeProductFromCart(clientId, cartProductId));
    }

}
