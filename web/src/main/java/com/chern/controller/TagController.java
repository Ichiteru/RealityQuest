package com.chern.controller;

import com.chern.exception.IncorrectPathVariableException;
import com.chern.exception.NoSuchDataException;
import com.chern.model.Tag;
import com.chern.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(value = "/tags/{id}")
    public ResponseEntity getById(@PathVariable long id) {
        if (id < 0) {
            throw new IncorrectPathVariableException("Incorrect id(" + id + "): must be more than -1x");
        }
        return ResponseEntity.ok(tagService.getById(id));
    }

    @GetMapping(value = "/tags")
    public ResponseEntity getAll() {
        List<Tag> tags = tagService.getAll();
        return ResponseEntity.ok(tags);
    }

    @PostMapping(path = "/tags")
    public ResponseEntity save(@RequestBody List<Tag> tags) {
        if (tags.size() == 0) {
            throw new NoSuchDataException("There are no tags selected for saving");
        }
        return ResponseEntity.ok().body(tagService.save(tags));
    }

    @DeleteMapping(path = "/tags/{id}")
    public ResponseEntity deleteById(@PathVariable long id) {
        if (id < 0) {
            throw new IncorrectPathVariableException("Incorrect id(" + id + "): must be more than -1x");
        }
        return ResponseEntity.ok().body(tagService.deleteById(id));
    }

    @DeleteMapping(path = "/tags")
    public ResponseEntity deleteById(@RequestBody List<Long> ids) {
        if (ids.size() == 0) {
            throw new NoSuchDataException("There are no tags selected for removing");
        }
        return ResponseEntity.ok(tagService.delete(ids) + " tag(-s) were removed");
    }
}
