package com.chern.util;

public class SearchQueryBuilder {

    private static String SELECT_BY_TAG_NAME_PART="select q.id, q.name, genre, price, duration, max_people from quest_tag" +
            " inner join quest q on q.id = quest_tag.quest_id" +
            " inner join tag t on t.id = quest_tag.tag_id" +
            " where upper(t.name) like upper(%s)";
    private static String SELECT_BY_NAME_AND_DESCRIPTION_PART = "select * from searchByNameAndDescription('" + "%s" + "', '" + "%s" + "')";
    private static String SELECT_ALL_PART = "select id, name, genre, price, duration, max_people from quest ";
    private static String INTERSECT_PART = " INTERSECT ";
    private static String ORDER_BY_PART =  " order by " + "%s" + " " + "%s";

    public SearchQueryBuilder() {
    }

    public static String buildSearchQuery(String tagName, String namePart,
                                          String descriptionPart, String sortBy, String sortType) {
        String sortByQueryPart = "";
        String selectByTagNamePart = "";
        String selectByNameAndDescriptionPart = "";
        String unionPart = "";

        if (!sortBy.equals("")) {
            sortByQueryPart = String.format(ORDER_BY_PART, sortBy, sortType);
        }

        if (!tagName.equals("")) {
            selectByTagNamePart = String.format(SELECT_BY_TAG_NAME_PART, "'%" + tagName + "%'");
        }
        if (!namePart.equals("") || !descriptionPart.equals("")) {
            selectByNameAndDescriptionPart = String.format(SELECT_BY_NAME_AND_DESCRIPTION_PART, namePart, descriptionPart);
        }

        if (selectByTagNamePart.equals("") && selectByNameAndDescriptionPart.equals("")) {
            unionPart = SELECT_ALL_PART + sortByQueryPart;
        }

        if (!selectByTagNamePart.equals("") && !selectByNameAndDescriptionPart.equals("")) {
            unionPart = selectByTagNamePart + INTERSECT_PART + selectByNameAndDescriptionPart + sortByQueryPart;
        }

        if (!selectByTagNamePart.equals("") && selectByNameAndDescriptionPart.equals("")) {
            unionPart = selectByTagNamePart + sortByQueryPart;
        }

        if (selectByTagNamePart.equals("") && !selectByNameAndDescriptionPart.equals("")) {
            unionPart = selectByNameAndDescriptionPart + sortByQueryPart;
        }
        return unionPart;
    }
}
