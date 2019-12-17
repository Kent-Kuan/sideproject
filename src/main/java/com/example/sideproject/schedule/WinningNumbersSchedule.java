package com.example.sideproject.schedule;

import com.example.sideproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

@Component
public class WinningNumbersSchedule {
    private final int NUMBER_MAX = 29;
    private final int NUMBER_MIN = 1;
    private final int NUMBER_SIZE = 5;
    @Autowired
    OrderService orderService;

    @Scheduled(cron = "0 0,5,10,15,20,25,30,35,40,45,50,55 * * * *")
    public void generateWinningNumbersCron() throws InterruptedException {
        Random random = new Random();
        Set<Integer> randomsNumbers = new HashSet<>();
        while(randomsNumbers.size() < NUMBER_SIZE + 1) {
            randomsNumbers.add(random.nextInt(NUMBER_MAX) + 1);
        }
        int[] numbers = randomsNumbers.stream().mapToInt(Integer::intValue).toArray();
        Arrays.sort(numbers);
        orderService.insertWinningNumbers(numbers);
        orderService.checkOrderWithWinningNumbers();
    }
}
