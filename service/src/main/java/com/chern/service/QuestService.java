package com.chern.service;

import com.chern.model.Quest;
import com.chern.repo.QuestRepository;
import com.chern.repo.QuestRepositoryPostgres;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestService {

    private final QuestRepositoryPostgres questRepository;

    public QuestService(QuestRepositoryPostgres questRepository) {
        this.questRepository = questRepository;
    }


    public ResponseEntity getById(long id) {
        try {
            return ResponseEntity.ok().body(questRepository.getById(id));
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.badRequest().body("Requested quest does not exist");
        }
    }

}
