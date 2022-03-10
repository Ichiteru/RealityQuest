package com.chern.service;

import com.chern.exception.NoSuchDataException;
import com.chern.exception.QuestReservationException;
import com.chern.model.Order;
import com.chern.model.Quest;
import com.chern.model.User;
import com.chern.model.builder.OrderBuilder;
import com.chern.repo.OrderRepository;
import com.chern.repo.QuestRepository;
import com.chern.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final QuestRepository questRepository;

    @Transactional
    public Order save(String username, long questId, LocalDateTime reservationTime) {
        Optional<User> userByUsername = userRepository.findUserByUsername(username);
        if (userByUsername.isEmpty()){
            throw new NoSuchDataException("User with this username not found");
        }
        if (!questRepository.existsById(questId).booleanValue()){
            throw new NoSuchDataException("Quest for reservation not found");
        }
        if (orderRepository.isReservedAtThisTime(reservationTime)){
            throw new QuestReservationException("This time is reserved, choose another date");
        }
        Quest quest = questRepository.getById(questId);
        LocalDateTime endTime = reservationTime.plusHours(quest.getDuration().getHour())
                .plusMinutes(quest.getDuration().getMinute())
                .plusSeconds(quest.getDuration().getSecond());
        Order order = OrderBuilder.anOrder()
                .withQuest(quest)
                .withUser(userByUsername.get())
                .withPurchaseTime(LocalDateTime.now())
                .withCost(quest.getPrice())
                .withReserveTime(reservationTime)
                .withEndTime(endTime).build();

        Order save = orderRepository.save(order);

        System.out.println(save);
        return order;
    }

    public List<Order> getAll(int page, int size) {
        try{
            return orderRepository.getAll(page,size);
        } catch (EmptyResultDataAccessException ex){
            return new ArrayList<>();
        }
    }

    public Optional<Order> getById(long id) {
        Optional<Order> order = orderRepository.getById(id);
        return order;
    }
}
