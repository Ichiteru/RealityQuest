package com.chern.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQuestKey implements Serializable {

    @Column(name = "user_id")
    long userId;
    @Column(name = "quest_id")
    long questId;
}
