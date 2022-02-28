package com.chern.validation;

import com.chern.model.Tag;
import com.chern.exception.IncorrectDataException;
import org.springframework.stereotype.Component;

@Component
public class TagValidator implements Validator<Tag> {
    @Override
    public void validate(Tag tag) {
        if (tag.getName().equals("") || tag.getName().equals(null)){
            throw new IncorrectDataException("Tag name must be not empty");
        }
    }
}
