package com.example.sideproject.controller;

import com.example.sideproject.entity.Order;
import com.example.sideproject.entity.ResponseBean;
import com.example.sideproject.entity.WinningNumbers;
import com.example.sideproject.exception.BaseException;
import com.example.sideproject.exception.CustomNoSuchElementException;
import com.example.sideproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/api/lottery")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Value("${lottery.max}")
    private int NUMBER_MAX;
    @Value("${lottery.min}")
    private int NUMBER_MIN;
    @Value("${lottery.place.size}")
    private int NUMBER_SIZE;

    @PostMapping("/order")
    public ResponseEntity<ResponseBean> placeOrder(@RequestBody Order order, Principal principal) throws CustomNoSuchElementException {
        if (validNumbers(order.getNumbers()))
            return new ResponseEntity<>(ResponseBean.error(400, "Numbers error."), HttpStatus.BAD_REQUEST);
        String userEmail = principal.getName();
        orderService.placeOrder(userEmail, order.getNumbers());
        return new ResponseEntity<>(ResponseBean.ok(), HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<ResponseBean> enquiryOrders(Principal principal) {
        String userEmail = principal.getName();
        List<Order> orders = orderService.enquireOrdersByEmail(userEmail);
        return new ResponseEntity<>(ResponseBean.ok(orders), HttpStatus.OK);
    }

    @DeleteMapping("/order")
    public ResponseEntity<ResponseBean> revokeOrder(@RequestBody Order order, Principal principal) throws CustomNoSuchElementException {
        if (order.getNumbers() == null || validNumbers(order.getNumbers()) || order.getId() == 0)
            return new ResponseEntity<>(ResponseBean.error("Numbers or order id error"), HttpStatus.BAD_REQUEST);
        String userEmail = principal.getName();
        orderService.revokeOrder(userEmail, order.getId(), order.getNumbers());
        return new ResponseEntity<>(ResponseBean.ok("Revoke order success."), HttpStatus.OK);
    }

    @GetMapping("/winning")
    public ResponseEntity<ResponseBean> enquireWinningNumbers(@RequestParam(value = "page", required = false) String page) throws CustomNoSuchElementException {
        int pageNumber = page == null ? 0 : Integer.valueOf(page);
        List<WinningNumbers> winningNumbers = orderService.enquireWinningNumbers(Integer.valueOf(pageNumber));
        return new ResponseEntity<>(ResponseBean.ok(winningNumbers), HttpStatus.OK);
    }

    private boolean validNumbers(int[] numbers) {
        Set<Integer> duplicate = new HashSet<>();
        if (numbers.length != NUMBER_SIZE)
            return true;
        return Arrays.stream(numbers).anyMatch(num -> num < NUMBER_MIN || num > NUMBER_MAX || !duplicate.add(num));
    }

    @ExceptionHandler(value = BaseException.class)
    private ResponseEntity<ResponseBean> exceptionHandler(BaseException e) {
        return new ResponseEntity<>(ResponseBean.error(e.getStatus(), e.getMessage()), HttpStatus.OK);
    }
}
