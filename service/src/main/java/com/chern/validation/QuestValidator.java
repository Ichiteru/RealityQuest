package com.chern.validation;

import com.chern.exception.IncorrectDataException;
import com.chern.model.Quest;
import org.springframework.stereotype.Component;

@Component
public class QuestValidator implements Validator<Quest>{

    @Override
    public void validate(Quest quest) {
        if (quest.getName().equals("") || quest.getName().equals(null)){
            throw new IncorrectDataException("Empty quest name");
        }
        if (quest.getPrice() < 0){
            throw new IncorrectDataException("Incorrect price(must be more than 0)");
        }
        if (quest.getName().equals("") || quest.getName().equals(null)){
            throw new IncorrectDataException("Empty quest genre");
        }
    }
}
