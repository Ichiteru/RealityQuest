package com.chern.service;


import com.chern.model.Quest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuestService {
    Quest save(Quest quest);
    ResponseEntity getById(long id);
    List<Quest> getAll();
    void deleteById(long id);
    Quest update(Quest quest);
}
