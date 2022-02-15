package com.chern.controller;

import com.chern.model.Quest;
import com.chern.service.QuestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class QuestController {

    private final QuestService questService;

    public QuestController(QuestService questService) {
        this.questService = questService;
    }

    @GetMapping(value = "/quest/{id}")
    public ResponseEntity getById(@PathVariable long id){
        if (id < 0){
            return ResponseEntity.badRequest().body("Incorrect request(id should be more than -1)");
        }
        return questService.getById(id);
    }

    @GetMapping(value = "/quests")
    public List<Quest> getAll(){
        return questService.getAll();
    }

    @DeleteMapping(value = "/quest/{id}")
    public ResponseEntity deleteById(@PathVariable long id){
        return null;
    }

    @PostMapping(value = "/quest")
    public ResponseEntity save(@RequestBody Quest quest){
        questService.save(quest);
        return null;
    }
}
