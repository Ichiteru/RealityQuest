package com.chern.controller;

import com.chern.exception.IncorrectPathVariableException;
import com.chern.exception.NoSuchDataException;
import com.chern.model.Quest;
import com.chern.service.QuestService;
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
    public ResponseEntity getById(@PathVariable long id) {
        if (id < 0) {
            throw new IncorrectPathVariableException("Incorrect id(" + id + "): must be more than -1x");
        }
        return ResponseEntity.ok(questService.getById(id));
    }

    @GetMapping(value = "/quests")
    public ResponseEntity getAll() {
        List<Quest> quests = questService.getAll();
        return ResponseEntity.ok().body(quests);
    }

    @PostMapping(path = "/quests"
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity save(@RequestBody Quest quest) {
        Quest saveQuest = questService.save(quest);
        return ResponseEntity.ok(saveQuest);
    }

    @PutMapping(path = "/quests"
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody Quest quest) {
        Quest updatedQuest = questService.update(quest);
        return ResponseEntity.ok(updatedQuest);
    }

    @DeleteMapping(value = "/quests/{id}")
    public ResponseEntity deleteById(@PathVariable long id) {
        if (id < 0) {
            throw new IncorrectPathVariableException("Incorrect id(" + id + "): must be more than -1x");
        }
        return ResponseEntity.ok(questService.deleteById(id));
    }

    @DeleteMapping(value = "/quests")
    public ResponseEntity delete(@RequestBody List<Long> ids){
        if (ids == null){
            throw new NoSuchDataException("There are no records select for removing");
        }
        return ResponseEntity.ok(questService.delete(ids) + " record(-s) were removed");
    }
}
