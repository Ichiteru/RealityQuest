package com.chern.controller;

import com.chern.model.Quest;
import com.chern.service.QuestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class QuestController {

    private final QuestService questService;

    public QuestController(QuestService questService) {
        this.questService = questService;
    }

    @GetMapping(value = "/quest/{id}")
    public Quest getById(@PathVariable long id){
        Quest quest = questService.getById(id);
        System.out.println(quest);
        return quest;
    }

    @GetMapping(value = "/quests")
    public List<Quest> getAll(){
        return questService.getAll();
    }
}
