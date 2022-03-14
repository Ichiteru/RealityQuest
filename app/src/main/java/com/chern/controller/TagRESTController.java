package com.chern.controller;

import com.chern.model.Tag;
import com.chern.exception.NoSuchDataException;
import com.chern.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TagRESTController {

    @Autowired
    private TagService tagService;

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_OWNER')")
    @GetMapping(value = "/tags/{id}")
    public ResponseEntity getById(@PathVariable long id) {
        return ResponseEntity.ok(tagService.getById(id));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_OWNER')")
    @GetMapping(value = "/tags")
    public ResponseEntity getAll() {
        List<Tag> tags = tagService.getAll();
        return ResponseEntity.ok(tags);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_OWNER')")
    @PostMapping(path = "/tags")
    public ResponseEntity save(@RequestBody List<Tag> tags) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.save(tags));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_OWNER')")
    @DeleteMapping(path = "/tags/{id}")
    public ResponseEntity deleteById(@PathVariable long id) {
        tagService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_OWNER')")
    @DeleteMapping(path = "/tags")
    public ResponseEntity deleteById(@RequestBody List<Long> ids) {
        if (ids.size() == 0) {
            throw new NoSuchDataException("There are no tags selected for removing");
        }
        tagService.delete(ids);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'ROLE_USER')")
    @GetMapping("/tags/search/mostUsed")
    public ResponseEntity getMostUsedTag(){
        return ResponseEntity.ok(tagService.getMostUsedTag());
    }
}
