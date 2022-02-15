package com.chern.service.impl;

import com.chern.model.Quest;
import com.chern.repo.QuestRepository;
import com.chern.service.QuestService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Quest getById(long id) {
        return questRepository.getById(id);
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
