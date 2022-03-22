package com.chern.controller;

import com.chern.model.Quest;
import com.chern.model.Tag;
import com.chern.service.QuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/test")
public class DataGeneratorController {

    @Autowired
    private RestTemplate restTemplate;
    private static final String URL = "https://random-word-api.herokuapp.com/word?number=4000";
    @Autowired
    private QuestService questService;

    @Transactional
    @PreAuthorize("hasAnyAuthority('ROLE_OWNER')")
    @PostMapping("/generate")
    public void generate(){
        ResponseEntity<String[]> forEntity = restTemplate.getForEntity(URL, String[].class);
        String[] body = forEntity.getBody();
        List<Quest> quests = new ArrayList<>(1000);
        for (int i = 0; i < body.length; i++) {
            Quest quest = Quest.builder().name(body[i])
                    .description(body[i])
                    .genre(body[i])
                    .modificationDate(LocalDate.now())
                    .duration(generateRandomLocalTime())
                    .maxPeople(10)
                    .price(10)
                    .creationDate(LocalDate.now())
                    .tags(List.of(
                            new Tag(body[++i]),
                            new Tag(body[++i]),
                            new Tag(body[++i])
                    ))
                    .build();
            quests.add(quest);
        }
        List<Quest> collect = quests.stream().peek(quest -> questService.save(quest)).collect(Collectors.toList());
        System.out.println(collect);
    }

    private LocalTime generateRandomLocalTime() {
        LocalTime time1 = LocalTime.of(1, 0, 0);
        LocalTime time2 = LocalTime.of(4, 0, 0);
        int secondOfDayTime1 = time1.toSecondOfDay();
        int secondOfDayTime2 = time2.toSecondOfDay();
        Random random = new Random();
        int randomSecondOfDay = secondOfDayTime1 + random.nextInt(secondOfDayTime2-secondOfDayTime1);
        LocalTime randomLocalTime = LocalTime.ofSecondOfDay(randomSecondOfDay);
        return randomLocalTime;
    }
}
