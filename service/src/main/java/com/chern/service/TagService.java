package com.chern.service;

import com.chern.dto.TagDTO;
import com.chern.dto.converter.Converter;
import com.chern.model.Tag;
import com.chern.repo.TagRepository;
import com.chern.exception.DuplicateFieldException;
import com.chern.exception.NoSuchDataException;
import com.chern.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final Validator<Tag> tagValidator;
    private final Converter<TagDTO, Tag> tagConverter;

    public TagDTO getById(long id) {
        try {
            Tag byId = tagRepository.getById(id);
            return tagConverter.entityToDtoConverter(byId);
        } catch (EmptyResultDataAccessException exception){
            throw new NoSuchDataException("There is no tag with such id(" + id + ")");
        }
    }

    public List<TagDTO> getAll(int page, int size) {
        try {
            List<Tag> tags = tagRepository.getAll(page, size);
            List<TagDTO> dtos = tags.stream()
                    .map(tag -> tagConverter.entityToDtoConverter(tag))
                    .collect(Collectors.toList());
            return dtos;
        } catch (EmptyResultDataAccessException exception){
            return new ArrayList<>();
        }
    }

    @Transactional
    public List<TagDTO> save(List<Tag> tags) throws DuplicateFieldException {
        tags.forEach(tag -> tagValidator.validate(tag));
        if (!tagRepository.getByNames(tags.stream().map(Tag::getName).collect(Collectors.toList())).isEmpty()){
            throw new DuplicateFieldException("Tag with some of this names already exists");
        }
        List<Tag> save = tagRepository.save(tags);
        return  save.stream()
                .map(t -> tagConverter.entityToDtoConverter(t))
                .collect(Collectors.toList());
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

    public TagDTO getMostUsedTag(){
        return tagConverter.entityToDtoConverter(tagRepository.findMostUsedOfTopUser());
    }
}
