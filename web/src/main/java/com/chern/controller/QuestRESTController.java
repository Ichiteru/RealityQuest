package com.chern.controller;

import chern.model.Quest;
import com.chern.service.QuestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class QuestRESTController {

    private final QuestService questService;

    public QuestRESTController(QuestService questService) {
        this.questService = questService;
    }

    @GetMapping(value = "/quests/{id}")
    public ResponseEntity getById(@PathVariable long id) {
        return ResponseEntity.ok(questService.getById(id));
    }

    @GetMapping(value = "/quests")
    public ResponseEntity getAll() {
        List<Quest> quests = questService.getAll();
        return ResponseEntity.ok().body(quests);
    }

    @PostMapping(path = "/quests", consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity save(@RequestBody Quest quest) {
        Quest saveQuest = questService.save(quest);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveQuest);
    }

    @PutMapping(path = "/quests", consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody Quest quest) {
        Quest updatedQuest = questService.update(quest);
        return ResponseEntity.ok(updatedQuest);
    }

    @DeleteMapping(value = "/quests/{id}")
    public ResponseEntity deleteById(@PathVariable long id) {
        questService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/quests")
    public ResponseEntity delete(@RequestBody List<Long> ids) {
        questService.delete(ids);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/quests/search")
    public ResponseEntity search(@RequestParam(required = false, defaultValue = "") String tagName
            , @RequestParam(required = false, defaultValue = "") String namePart
            , @RequestParam(required = false, defaultValue = "") String descriptionPart
            , @RequestParam(required = false, defaultValue = "") String sortBy
            , @RequestParam(required = false, defaultValue = "DESC") String sortType) {
        List<Quest> quests = questService.searchBy(tagName, namePart, descriptionPart, sortBy, sortType);
        return ResponseEntity.ok(quests);
    }
}
