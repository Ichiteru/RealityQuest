package com.chern.service;

import com.chern.dto.TabularOrderDto;
import com.chern.dto.converter.Mapper;
import com.chern.exception.NoSuchDataException;
import com.chern.exception.QuestReservationException;
import com.chern.model.Order;
import com.chern.model.Quest;
import com.chern.model.User;
import com.chern.repo.OrderRepository;
import com.chern.repo.QuestRepository;
import com.chern.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final QuestRepository questRepository;
    private final Mapper<TabularOrderDto, Order> tabularOrderMapper;

    @Transactional
    public TabularOrderDto save(String username, long questId, LocalDateTime reservationTime) {
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
        return tabularOrderMapper.entityToDto(save);
    }

    public List<TabularOrderDto> getAll(int page, int size) {
        try{
            List<Order> orders = orderRepository.getAll(page*10, size);
            List<TabularOrderDto> dtoList = orders.stream()
                    .map(order -> tabularOrderMapper.entityToDto(order))
                    .collect(Collectors.toList());
            return dtoList;
        } catch (EmptyResultDataAccessException ex){
            return new ArrayList<>();
        }
    }

    public TabularOrderDto getById(long id) {
        Optional<Order> orderOptional = orderRepository.getById(id);
        if (orderOptional.isPresent()){
            TabularOrderDto tabularOrderDTO = tabularOrderMapper.entityToDto(orderOptional.get());
            return tabularOrderDTO;
        } else{
            throw new NoSuchDataException("There is no order with id = " + id);
        }

    }

    @Transactional
    public void deleteById(long id) {
        orderRepository.deleteById(id);
    }

    public List<TabularOrderDto> getUserReservations(long userId) {
        List<Order> reservations = orderRepository.getUserReservations(userId);
        return reservations.stream()
                .map(r -> tabularOrderMapper.entityToDto(r))
                .collect(Collectors.toList());
    }
}
