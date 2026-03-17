package com.appl.ecom.service;

import com.appl.ecom.dto.CartItemRequest;
import com.appl.ecom.model.CartItem;
import com.appl.ecom.model.Product;
import com.appl.ecom.model.User;
import com.appl.ecom.repository.CartItemRepository;
import com.appl.ecom.repository.ProductRepository;
import com.appl.ecom.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    // making objects of repo to find the user, product, item in database
    private final CartItemRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public boolean addToCart(String userId, CartItemRequest request) {
        Optional<Product> productOpt = productRepository.findById(request.getProductId());
         if(productOpt.isEmpty())  // checking whether product exist or not in database
              return false;

         Product product = productOpt.get();
         if(request.getQuantity() > product.getStockQuantity())
             return false; // is it have the quantity available

        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
           if(userOpt.isEmpty())
               return false;  // is User exist ? or is it valid userId?

          User user = userOpt.get();

        CartItem existingCartItem = cartRepository.findByUserAndProduct(user, product);
          if(existingCartItem != null) {
              // Update the quantity and price
             existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
             existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
             cartRepository.save(existingCartItem);
          } else {
              // Create new cartItem
              CartItem cartItem = new CartItem();
              cartItem.setUser(user);
              cartItem.setProduct(product);
              cartItem.setQuantity(request.getQuantity());
              cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
              cartRepository.save(cartItem);
          }

          return true;
    }

    public boolean removeFromCart(String userId, Long productId) {
        Optional<User> userOpt=  userRepository.findById(Long.valueOf(userId));
        Optional<Product> productOpt= productRepository.findById(productId);

          if(userOpt.isPresent() && productOpt.isPresent()) {
              cartRepository.deleteByUserAndProduct(userOpt.get(), productOpt.get());
              return true;
          }

          return false;
    }

     public List<CartItem> fetchCart(String userId) {
          return userRepository.findById(Long.valueOf(userId))
                  .map(cartRepository :: findByUser)
                  .orElseGet(List::of);
     }

    public void clearCart(String userId) {
        userRepository.findById(Long.valueOf(userId)).ifPresent(
                cartRepository :: deleteByUser);

    }
}
