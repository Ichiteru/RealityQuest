package com.chern.service;

import com.chern.model.Tag;
import com.chern.repo.TagRepository;
import com.chern.exception.DuplicateFieldException;
import com.chern.exception.NoSuchDataException;
import com.chern.validation.Validator;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final Validator<Tag> tagValidator;

    public TagService(TagRepository tagRepository, Validator<Tag> tagValidator) {
        this.tagRepository = tagRepository;
        this.tagValidator = tagValidator;
    }

    public Tag getById(long id) {
        try {
            return tagRepository.getById(id);
        } catch (EmptyResultDataAccessException exception){
            throw new NoSuchDataException("There is no tag with such id(" + id + ")");
        }
    }

    public List<Tag> getAll() {
        try {
            return tagRepository.getAll();
        } catch (EmptyResultDataAccessException exception){
            return new ArrayList<>();
        }
    }

    public List<Tag> save(List<Tag> tags) {
        tags.forEach(tag -> tagValidator.validate(tag));
        try {
            return tagRepository.save(tags);
        } catch (DuplicateKeyException ex){
            throw new DuplicateFieldException("Tag with some of this names already exists");
        }
    }

    public long deleteById(long id){
        if (!tagRepository.existsById(id)){
            throw new NoSuchDataException("Tag with id = " + id + " doesn't exists");
        }
        return tagRepository.deleteById(id);
    }

    public int delete(List<Long> ids) {
        if (ids.size() == 0){
            return 0;
        }
        return tagRepository.delete(ids);
    }
}
