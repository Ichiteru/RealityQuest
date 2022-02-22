package com.chern.util;

import org.springframework.stereotype.Component;

@Component
public class SearchQueryBuilder {

    public SearchQueryBuilder() {
    }

    public static String buildSearchQuery(String tagName, String namePart,
                                          String descriptionPart, String sortBy, String sortType){
        String sortByQueryPart = "";
        String selectByTagNamePart = "";
        String selectByNameAndDescriptionPart = "";
        String unionPart = "";

        if (!sortBy.equals("")){
            sortByQueryPart = " order by " + sortBy + " " + sortType;
        }

        if (!tagName.equals("")){
            selectByTagNamePart = "select q.id, q.name, genre, price, duration, max_people from quest_tag" +
                    " inner join quest q on q.id = quest_tag.quest_id" +
                    " inner join tag t on t.id = quest_tag.tag_id" +
                    " where upper(t.name) like upper('%" + tagName + "%')";
        }
        if (!namePart.equals("") || !descriptionPart.equals("")){
            selectByNameAndDescriptionPart = "select * from searchByNameAndDescription('" + namePart + "', '" + descriptionPart + "')";
        }

        // if all empty do select all
        if (selectByTagNamePart.equals("") && selectByNameAndDescriptionPart.equals("")){
            unionPart = "select id, name, genre, price, duration, max_people from quest " + sortByQueryPart;
        }

        // if all not empty query union distinct
        if (!selectByTagNamePart.equals("") && !selectByNameAndDescriptionPart.equals("")){
            unionPart = selectByTagNamePart + " UNION DISTINCT " + selectByNameAndDescriptionPart + sortByQueryPart;
        }

        //if only tag name query by tag name
        if (!selectByTagNamePart.equals("") && selectByNameAndDescriptionPart.equals("")){
            unionPart = selectByTagNamePart + sortByQueryPart;
        }

        // if only quest params query by name and description
        if (selectByTagNamePart.equals("") && !selectByNameAndDescriptionPart.equals("")){
            unionPart = selectByNameAndDescriptionPart + sortByQueryPart;
        }
        return unionPart;
    }
}
