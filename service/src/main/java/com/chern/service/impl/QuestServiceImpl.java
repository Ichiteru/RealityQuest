package com.chern.service.impl;

import com.chern.model.Quest;
import com.chern.repo.QuestRepository;
import com.chern.service.QuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestServiceImpl implements QuestService {

    private final QuestRepository questRepository;

    public QuestServiceImpl(QuestRepository questRepository) {
        this.questRepository = questRepository;
    }

    @Override
    public Quest save(Quest quest) {
        return null;
    }

    @Override
    public ResponseEntity getById(long id) {
        try {
            return ResponseEntity.ok().body(questRepository.getById(id));
        } catch (EmptyResultDataAccessException ex){
            return ResponseEntity.badRequest().body("Requested quest does not exist");
        }
    }

    @Override
    public List<Quest> getAll() {
        return null;
    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public Quest update(Quest quest) {
        return null;
    }
}
