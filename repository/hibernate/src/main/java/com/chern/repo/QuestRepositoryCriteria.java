package com.chern.repo;

import com.chern.filter.QuestFilter;
import com.chern.model.Quest;
import com.chern.model.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class QuestRepositoryCriteria implements QuestRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Quest save(Quest quest) {
        entityManager.persist(quest);
        return quest;
    }

    @Override
    public Quest getById(long id) throws EmptyResultDataAccessException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Quest> query = criteriaBuilder.createQuery(Quest.class);
        Root<Quest> quest = query.from(Quest.class);
        Predicate idPredicate = criteriaBuilder.equal(quest.get("id"), id);
        query.where(idPredicate);
        TypedQuery<Quest> questById = entityManager.createQuery(query);
        return questById.getSingleResult();
    }

    @Override
    public List<Quest> getAll(int page, int size) throws EmptyResultDataAccessException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Quest> query = criteriaBuilder.createQuery(Quest.class);
        Root<Quest> questRoot = query.from(Quest.class);
        CriteriaQuery<Quest> select = query.select(questRoot);
        TypedQuery<Quest> pagination = entityManager.createQuery(select);
        pagination.setFirstResult(page);
        pagination.setMaxResults(size);
        return pagination.getResultList();
    }

    @Override
    public Quest update(Quest quest) {
        return entityManager.merge(quest);
    }

    @Override
    public Boolean existsById(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Quest> query = criteriaBuilder.createQuery(Quest.class);
        Root<Quest> from = query.from(Quest.class);
        Subquery<Quest> subquery = query.subquery(Quest.class);
        Root<Quest> subqueryFrom = subquery.from(Quest.class);

        subquery.select(subqueryFrom)
                .where(criteriaBuilder
                        .equal(subqueryFrom.get("id"), id));

        query.select(from).where(criteriaBuilder.exists(subquery));
        TypedQuery<Quest> result = entityManager.createQuery(query);
        return result.getResultList().isEmpty() ? false : true;
    }

    @Override
    public long deleteById(long id){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Quest> criteriaDelete = criteriaBuilder.createCriteriaDelete(Quest.class);
        Root<Quest> from = criteriaDelete.from(Quest.class);
        criteriaDelete.where(criteriaBuilder.equal(from.get("id"), id));
        int i = entityManager.createQuery(criteriaDelete).executeUpdate();
        return i;
    }

    @Override
    public int delete(List<Long> ids) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Quest> criteriaDelete = criteriaBuilder.createCriteriaDelete(Quest.class);
        Root<Quest> from = criteriaDelete.from(Quest.class);
        criteriaDelete.where(from.get("id").in(ids));
        return entityManager.createQuery(criteriaDelete).executeUpdate();
    }

    @Override
    public List<Quest> searchBySeveralTags(List<Long> tagIds) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Quest> query = criteriaBuilder.createQuery(Quest.class);
        Root<Quest> from = query.from(Quest.class);
//        Join<Quest, Tag> tags = from.join("tags");
        Optional<Predicate> predicate = tagIds.stream()
                .map(id -> criteriaBuilder.isMember(id, from.get("tags")))
                .reduce(criteriaBuilder::and);
        CriteriaQuery<Quest> result = query.select(from).where(predicate.get());
        List<Quest> resultList = entityManager.createQuery(result).getResultList();
        return resultList;
    }

    @Override
    public List<Quest> findByFilter(QuestFilter filter, int page, int size) {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Quest> filterQuery = builder.createQuery(Quest.class);
            Root<Quest> root = filterQuery.from(Quest.class);

            Optional<Predicate> predicate = buildOptionsPredicate(builder, root, filter);
            predicate.ifPresent(filterQuery::where);

            TypedQuery<Quest> query = entityManager.createQuery(filterQuery);
            query.setFirstResult(page);
            query.setMaxResults(size);
            return query.getResultList();
    }

    private Optional<Predicate> buildOptionsPredicate(CriteriaBuilder builder,
                                                      Root<Quest> root,
                                                      QuestFilter filter
    ) {
        Optional<Predicate> tagsPredicate = filter.getTags().stream()
                .map(tag -> builder.isMember(tag.getName(), root.get("tags")))
                .reduce(builder::and);
        Optional<Predicate> namesPredicate = filter.getNames().stream()
                .map(name -> builder.like(root.get("name"), "%" + name + "%"))
                .reduce(builder::and);
        Optional<Predicate> descriptionsPredicate = filter.getDescriptions().stream()
                .map(description -> builder.like(root.get("description"), "%" + description + "%"))
                .reduce(builder::and);
        return Stream.of(tagsPredicate, namesPredicate, descriptionsPredicate)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce(builder::and);
    }

//    @Override
//    public List<Quest> searchByParams(String query){
//        List<Quest> quests = jdbcTemplate.queryForObject(query, new RowMapper<List<Quest>>() {
//            @Override
//            public List<Quest> mapRow(ResultSet rs, int rowNum) throws SQLException {
//                List<Quest> quests = new ArrayList<>();
//                do {
//                    quests.add(
//                            QuestBuilder.aQuest()
//                                    .withId(rs.getLong("id"))
//                                    .withName(rs.getString("name"))
//                                    .withGenre(rs.getString("genre"))
//                                    .withPrice(rs.getDouble("price"))
//                                    .withDuration(rs.getTime("duration").toLocalTime())
//                                    .withMaxPeople(rs.getInt("max_people")).build());
//                } while (rs.next());
//                return quests;
//            }
//        });
//        return quests;
//    }
}
