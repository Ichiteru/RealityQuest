package com.chern.controller;

import com.chern.exception.IncorrectPathVariableException;
import com.chern.model.Quest;
import com.chern.model.TestRequest;
import com.chern.service.QuestService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
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

    @GetMapping(value = "/quests/{id}")
    public ResponseEntity getById(@PathVariable long id){
        if (id < 0){
            throw new IncorrectPathVariableException("Incorrect id(" + id + "): must be more than -1x");
        }
        return ResponseEntity.ok(questService.getById(id));
    }

    @GetMapping(value = "/quests")
    public ResponseEntity getAll(){
        List<Quest> quests = questService.getAll();
        return ResponseEntity.ok().body(quests);
    }

    @PostMapping(path = "/quests"
    , consumes = MediaType.APPLICATION_JSON_VALUE
    , produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity save(@RequestBody Quest quest){
        questService.save(quest);
        return ResponseEntity.ok("test save quest");
    }

//    @DeleteMapping(value = "/quest/{id}")
//    public ResponseEntity deleteById(@PathVariable long id){
//        return null;
//    }
//
//    @PostMapping(value = "/quest")
//    public ResponseEntity save(@RequestBody Quest quest){
//        questService.save(quest);
//        return null;
//    }
}
