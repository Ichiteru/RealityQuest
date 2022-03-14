package com.chern.service;

import com.chern.dto.TabularOrderDTO;
import com.chern.dto.converter.Converter;
import com.chern.exception.NoSuchDataException;
import com.chern.exception.QuestReservationException;
import com.chern.model.Order;
import com.chern.model.Quest;
import com.chern.model.User;
import com.chern.repo.OrderRepository;
import com.chern.repo.QuestRepository;
import com.chern.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private QuestRepository questRepository;
    @Autowired
    private Converter<TabularOrderDTO, Order> tabularOrderConverter;

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
        Order order = Order.builder()
                .quest(quest)
                .user(userByUsername.get())
                .purchaseTime(LocalDateTime.now())
                .cost(quest.getPrice())
                .reserveTime(reservationTime)
                .endTime(endTime).build();

        Order save = orderRepository.save(order);

        System.out.println(save);
        return order;
    }

    public List<TabularOrderDTO> getAll(int page, int size) {
        try{
            List<Order> orders = orderRepository.getAll(page, size);
            List<TabularOrderDTO> dtoList = orders.stream()
                    .map(order -> tabularOrderConverter.entityToDtoConverter(order))
                    .collect(Collectors.toList());
            return dtoList;
        } catch (EmptyResultDataAccessException ex){
            return new ArrayList<>();
        }
    }

    public Optional<Order> getById(long id) {
        Optional<Order> order = orderRepository.getById(id);
        return order;
    }
}
