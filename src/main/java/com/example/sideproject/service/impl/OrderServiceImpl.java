package com.example.sideproject.service.impl;

import com.example.sideproject.dao.OrderDao;
import com.example.sideproject.dao.UserDao;
import com.example.sideproject.entity.Order;
import com.example.sideproject.entity.User;
import com.example.sideproject.entity.WinningNumbers;
import com.example.sideproject.exception.CustomNoSuchElementException;
import com.example.sideproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private static final int ORDER_PRICE = 100;
    private static final int WINNING_MONEY = 200;

    @Autowired
    OrderDao orderDao;
    @Autowired
    UserDao userDao;

    @Transactional
    @Override
    public boolean placeOrder(String email, int[] numbers) throws CustomNoSuchElementException {
        Arrays.sort(numbers);
        User user = userDao.findUserByEmail(email);
        if(user == null) {
            throw new CustomNoSuchElementException(997, "Cannot find this user.");
        }
        int currentBalance = user.getBalance() - ORDER_PRICE;
        if(currentBalance < 0) {
            throw new CustomNoSuchElementException(998, "Balance not enough.");
        }
        orderDao.insertOrder(email, numbers);
        userDao.updateBalanceWithEmail(currentBalance, email);
        return false;
    }

    @Override
    public List<Order> enquireOrdersByEmail(String email) {
        return orderDao.findOrdersByEmail(email);
    }

    @Override
    public List<WinningNumbers> enquireWinningNumbers(int page) {
        return orderDao.findAllWinningNumbers(page);
    }

    @Override
    public boolean insertWinningNumbers(int[] numbers) {
        return orderDao.insertWinningNumbers(numbers) == 1;
    }

    @Override
    public boolean revokeOrder(String email, int orderId, int[] numbers) throws CustomNoSuchElementException {
        int result = orderDao.deleteOrder(orderId, email, numbers);
        if(result != 1)
            throw new CustomNoSuchElementException(999, "Revoke order fail.");
        return true;
    }

    @Override
    public void checkOrderWithWinningNumbers() {
        WinningNumbers winningNumbers = orderDao.findNotCheckedWinningNumbers();
        Set<Integer> numbers = Arrays.stream(winningNumbers.getNumbers()).boxed().collect(Collectors.toSet());
        List<Order> notCheckedOrder = orderDao.findNotCheckedOrdersByStage(winningNumbers.getStage());
        Map<String, Integer> updateBalanceByUserId = new HashMap<>();
        notCheckedOrder.stream().forEach(order -> {
            int matchedCount = 0;
            for(int num: order.getNumbers()) {
                if(numbers.contains(num)) {
                    matchedCount++;
                }
            }
            if(matchedCount == 2) {
                order.setWinning(1);
                updateBalanceByUserId.put(order.getUserId(), updateBalanceByUserId.getOrDefault(order.getUserId(), 0) + WINNING_MONEY);
            } else {
                order.setWinning(-1);
            }
        });
        updateWinningCheckAndBalance(winningNumbers, notCheckedOrder, updateBalanceByUserId);
    }

    @Transactional
    public void updateWinningCheckAndBalance(WinningNumbers winningNumbers, List<Order> notCheckedOrder, Map<String, Integer> updateBalanceByUserId) {
        orderDao.updateWinningChecked(winningNumbers);
        orderDao.updateOrder(notCheckedOrder);
        if(!updateBalanceByUserId.isEmpty()) {
            orderDao.updateAddBalanceWithUserId(updateBalanceByUserId);
        }
    }
}
