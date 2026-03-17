package com.appl.ecom.controller;

import com.appl.ecom.dto.CartItemRequest;
import com.appl.ecom.model.CartItem;
import com.appl.ecom.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor

public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(
              @RequestHeader("X-User-Id") String userId,
              @RequestBody CartItemRequest request) {

        if(!cartService.addToCart(userId, request)) { // if user or ProductRequest are not valid
          return ResponseEntity.badRequest().body("Product Out of Stock or User Not Found");
        }

        return ResponseEntity.ok().body("Item added Successfully");
     //   return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/item/{productId}")
    public ResponseEntity<Void> removeFromCart(
             @RequestHeader("X-User-Id") String userId,
             @PathVariable Long productId) {

        boolean deleted = cartService.removeFromCart(userId, productId);

        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

     @GetMapping
     public ResponseEntity<List<CartItem>> fetchCart(
             @RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(cartService.fetchCart(userId));
    }
}
