package com.adamidis.learning.warehousestockflow.Controller;

import com.adamidis.learning.warehousestockflow.DTO.MockOrderRequest;
import com.adamidis.learning.warehousestockflow.DTO.MockOrderResponse;
import com.adamidis.learning.warehousestockflow.Service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/mock")
    public MockOrderResponse mockOrder(@RequestBody @Valid MockOrderRequest req) {
        return orderService.placeMockOrder(req.productId(), req.quantity());
    }
}