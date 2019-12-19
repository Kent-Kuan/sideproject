package com.example.sideproject.service;

import com.example.sideproject.entity.Order;
import com.example.sideproject.entity.WinningNumbers;
import com.example.sideproject.exception.CustomNoSuchElementException;

import java.util.List;

public interface OrderService {
    boolean placeOrder(String email, int[] numbers) throws CustomNoSuchElementException;
    boolean revokeOrder(String email, int id, int[] numbers) throws CustomNoSuchElementException;
    List<Order> enquireOrdersByEmail(String email);
    List<WinningNumbers> enquireWinningNumbers(int page);
    boolean insertWinningNumbers(int[] numbers);
    void checkOrderWithWinningNumbers();
}
