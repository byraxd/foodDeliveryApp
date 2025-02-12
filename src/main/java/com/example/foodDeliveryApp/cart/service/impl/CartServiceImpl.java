package com.example.foodDeliveryApp.cart.service.impl;

import com.example.foodDeliveryApp.cart.dto.ProductToCartRequest;
import com.example.foodDeliveryApp.cart.model.Cart;
import com.example.foodDeliveryApp.cart.model.CartProduct;
import com.example.foodDeliveryApp.cart.repository.CartProductRepository;
import com.example.foodDeliveryApp.cart.repository.CartRepository;
import com.example.foodDeliveryApp.cart.service.CartService;
import com.example.foodDeliveryApp.client.model.Client;
import com.example.foodDeliveryApp.client.repository.ClientRepository;
import com.example.foodDeliveryApp.exception.model.ClientNotFoundException;
import com.example.foodDeliveryApp.exception.model.ProductNotFoundException;
import com.example.foodDeliveryApp.product.model.Product;
import com.example.foodDeliveryApp.product.repository.ProductRepository;
import com.example.foodDeliveryApp.utils.ValidateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartProductRepository cartProductRepository;

    @Override
    public Cart createCart(Long clientId) {
        ValidateUtils.validateId(clientId);

        Client client = findClientByClientId(clientId);

        Cart cart = Cart
                .builder()
                .client(client)
                .build();
        cartRepository.save(cart);

        client.setCart(cart);
        clientRepository.save(client);

        return cart;
    }

    @Override
    public Cart getCartByClientId(Long clientId) {
        ValidateUtils.validateId(clientId);

        return cartRepository.findCartByClient(findClientByClientId(clientId));
    }

    @Override
    public Cart addProductToCart(Long clientId, ProductToCartRequest productToCartRequest) {
        ValidateUtils.validateId(clientId);
        ValidateUtils.validateId(productToCartRequest.productId());

        Cart cart = cartRepository.findCartByClient(findClientByClientId(clientId));

        Product product = productRepository.findById(productToCartRequest.productId()).orElseThrow(() -> {
            log.error("Product with id {} not found", productToCartRequest.productId());
            return new ProductNotFoundException("Product with id " + productToCartRequest.productId() + " not found");
        });

        CartProduct cartProduct = CartProduct
                .builder()
                .cart(cart)
                .product(product)
                .quantity(productToCartRequest.quantity())
                .build();
        cartProductRepository.save(cartProduct);

        cart.getCartProducts().add(cartProduct);
        return cartRepository.save(cart);
    }

    @Override
    public Cart removeProductFromCart(Long clientId, Long cartProductId) {
        ValidateUtils.validateId(clientId);
        ValidateUtils.validateId(cartProductId);

        Cart cart = cartRepository.findCartByClient(findClientByClientId(clientId));

        CartProduct cartProduct = cartProductRepository.findById(cartProductId).orElseThrow(() -> {
            log.error("Product with id {} not found", cartProductId);
            return new ProductNotFoundException("Product with id " + cartProductId + " not found");
        });

        cart.getCartProducts().remove(cartProduct);

        cartProductRepository.delete(cartProduct);

        cartRepository.save(cart);

        return cart;
    }

    private Client findClientByClientId(Long clientId) {
        return clientRepository.findById(clientId).orElseThrow(() ->{
            log.error("Client not found by id: {}", clientId);
            return new ClientNotFoundException("Client not found by id " + clientId);
        });
    }
}
