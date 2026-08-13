package com.application.food_shop.domain.order.service;

import com.application.food_shop.domain.customer.entity.Customer;
import com.application.food_shop.domain.customer.repository.CustomerRepository;
import com.application.food_shop.domain.menu.entity.Menu;
import com.application.food_shop.domain.menu.repository.MenuRepository;
import com.application.food_shop.domain.order.entity.Order;
import com.application.food_shop.domain.order.entity.OrderItem;
import com.application.food_shop.domain.order.model.CreateOrderRequest;
import com.application.food_shop.domain.order.model.OrderItemRequest;
import com.application.food_shop.domain.order.model.OrderItemsResponse;
import com.application.food_shop.domain.order.model.OrderResponse;
import com.application.food_shop.domain.order.repository.OrderRepository;
import com.application.food_shop.domain.staff.entity.Staff;
import com.application.food_shop.domain.staff.repository.StaffRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final StaffRepository staffRepository;
    private final MenuRepository menuRepository;

    public OrderService(OrderRepository orderRepository,
                        CustomerRepository customerRepository,
                        StaffRepository staffRepository,
                        MenuRepository menuRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.staffRepository = staffRepository;
        this.menuRepository = menuRepository;
    }

    @Transactional
    public OrderResponse save(CreateOrderRequest request) {
        Customer customer = customerRepository.findById(request.customerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + request.customerId()));

        Staff staff = null;
        if (request.createdById() != null) {
            staff = staffRepository.findById(request.createdById())
                    .orElseThrow(() -> new EntityNotFoundException("Staff not found with ID: " + request.createdById()));
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setCreatedBy(staff);

        for (OrderItemRequest itemReq : request.orderItems()) {
            Menu menu = menuRepository.findById(itemReq.menuId())
                    .orElseThrow(() -> new EntityNotFoundException("Menu item not found with ID: " + itemReq.menuId()));

            OrderItem item = new OrderItem();
            item.setMenu(menu);
            item.setQuantity(itemReq.quantity());

             item.setUnitPrice(BigDecimal.valueOf(menu.getPrice()));
             item.setSubtotal(item.getUnitPrice(), item.getQuantity());

            order.addOrderItem(item);
        }

        return createOrderResponse(orderRepository.save(order));
    }

    private OrderResponse createOrderResponse(Order order) {
        List<OrderItemsResponse> itemsResponses = order.getOrderItems().stream()
                .map(item -> new OrderItemsResponse(
                        item.getMenu().getName(),
                        item.getMenu().getPrice(),
                        item.getQuantity()
                ))
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getCreatedAt(),
                order.getCreatedBy().getId(),
                order.getCreatedBy().getFullName(),
                order.getCustomer().getId(),
                order.getCustomer().getFullName(),
                order.getCustomer().getPhoneNumber(),
                itemsResponses,
                order.getTotalPrice()
        );
    }

    public List<OrderResponse> findAll() {
        List<OrderResponse> orderResponses = orderRepository.findAll().stream().map(this::createOrderResponse)
                .toList();


        return orderResponses;
    }

    public Order findById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }

}
