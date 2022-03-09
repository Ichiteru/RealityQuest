package com.chern.service;

import com.chern.exception.NoSuchDataException;
import com.chern.model.Order;
import com.chern.model.Quest;
import com.chern.model.User;
import com.chern.model.builder.OrderBuilder;
import com.chern.repo.OrderRepository;
import com.chern.repo.QuestRepository;
import com.chern.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final QuestRepository questRepository;

    @Transactional
    public Order save(String username, long questId) {
        Optional<User> userByUsername = userRepository.findUserByUsername(username);
        if (userByUsername.isEmpty()){
            throw new NoSuchDataException("User with this username not found");
        }
        Quest quest = questRepository.getById(questId);
        Order order = OrderBuilder.anOrder()
                .withQuest(quest)
                .withUser(userByUsername.get())
                .withPurchaseTime(LocalDateTime.now())
                .withCost(quest.getPrice())
                .withReserveTime(LocalTime.now()).build();
        Order save = orderRepository.save(order);
        System.out.println(save);
        return order;
    }
}
