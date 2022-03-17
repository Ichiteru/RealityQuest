package com.chern.controller;

import com.chern.dto.NewQuestDTO;
import com.chern.dto.TabularQuestDTO;
import com.chern.dto.TagDTO;
import com.chern.dto.converter.Converter;
import com.chern.model.Quest;
import com.chern.service.QuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class QuestRESTController {

    @Autowired
    private QuestService questService;
    @Autowired
    private Converter<NewQuestDTO, Quest> newQuestConverter;

    @PreAuthorize("hasAnyAuthority('ROLE_GUEST', 'ROLE_USER', 'ROLE_OWNER')")
    @GetMapping(value = "/quests/{id}")
    public ResponseEntity getById(@PathVariable long id) {
        return ResponseEntity.ok(questService.getById(id));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_GUEST', 'ROLE_USER', 'ROLE_OWNER')")
    @GetMapping(value = "/quests")
    public ResponseEntity getAll(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {
        List<TabularQuestDTO> quests = questService.getAll(page, size);
        return ResponseEntity.ok().body(quests);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_OWNER')")
    @PostMapping(path = "/quests", consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity save(@RequestBody NewQuestDTO quest) {
        Quest saveQuest = questService.save(newQuestConverter.dtoToEntityConverter(quest));
        return ResponseEntity.status(HttpStatus.CREATED).body(saveQuest);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_OWNER')")
    @PutMapping(path = "/quests", consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody Quest quest) {
        Quest updatedQuest = questService.update(quest);
        return ResponseEntity.ok(updatedQuest);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_OWNER')")
    @DeleteMapping(value = "/quests/{id}")
    public ResponseEntity deleteById(@PathVariable long id) {
        questService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_OWNER')")
    @DeleteMapping(value = "/quests")
    public ResponseEntity delete(@RequestBody List<Long> ids) {
        questService.delete(ids);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_OWNER')")
    @GetMapping(value = "/quests/search/by/several-tags")
    public ResponseEntity search(@RequestParam List<Long> tagIds) {
        List<TabularQuestDTO> quests = questService.searchBySeveralTags(tagIds);
        return ResponseEntity.ok(quests);
    }

//    @PreAuthorize("hasAnyAuthority('ROLE_GUEST', 'ROLE_USER', 'ROLE_OWNER')")
//    @GetMapping(value = "/quests/search")
//    public ResponseEntity search(@RequestParam(required = false, defaultValue = "") String tagName
//            , @RequestParam(required = false, defaultValue = "") String namePart
//            , @RequestParam(required = false, defaultValue = "") String descriptionPart
//            , @RequestParam(required = false, defaultValue = "") String sortBy
//            , @RequestParam(required = false, defaultValue = "DESC") String sortType) {
//        List<Quest> quests = questService.searchBy(tagName, namePart, descriptionPart, sortBy, sortType);
//        return ResponseEntity.ok(quests);
//    }
}
