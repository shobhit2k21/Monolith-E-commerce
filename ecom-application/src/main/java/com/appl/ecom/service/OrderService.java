package com.appl.ecom.service;

import com.appl.ecom.dto.OrderItemDTO;
import com.appl.ecom.dto.OrderResponse;
import com.appl.ecom.model.*;
import com.appl.ecom.repository.OrderRepository;
import com.appl.ecom.repository.ProductRepository;
import com.appl.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartService cartService;

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public Optional<OrderResponse> createOrder(String userId) {
        //Validate for CartItems
        List<CartItem> cartItems = cartService.fetchCart(userId);
         if(cartItems.isEmpty()) {
            return Optional.empty();
         }

        // Validate for User
        Optional<User> userOpt=  userRepository.findById(Long.valueOf(userId));
          if(userOpt.isEmpty()) {
              return Optional.empty();
          }
          User user = userOpt.get();

        // Calculate totalPrice
        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem :: getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

          // create order
          Order order = new Order();
           order.setUser(user);
           order.setStatus(OrderStatus.CONFIRMED);
           order.setTotalAmount(totalPrice);

           List<OrderItem> orderItems = cartItems.stream()
                   .map(item -> new OrderItem(
                    null,
                    item.getProduct(),
                    item.getQuantity(),
                    item.getPrice(),
                    order))
                   .toList();

           order.setItems(orderItems);
           Order savedOrder = orderRepository.save(order);

        // clear the cart
         cartService.clearCart(userId);

         return Optional.of(mapToOrderResponse(savedOrder));
    }

    private OrderResponse mapToOrderResponse(Order savedOrder) {
        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getTotalAmount(),
                savedOrder.getStatus(),
                savedOrder.getItems().stream()  // converting orderItem into orderItemDTO
                        .map(orderItem -> new OrderItemDTO(
                         orderItem.getId(),
                         orderItem.getProduct().getId(),
                         orderItem.getQuantity(),
                         orderItem.getPrice(),
                         orderItem.getPrice()
                       //  orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()))
                        )).toList(),
                     savedOrder.getCreatedAt());
    }
}
