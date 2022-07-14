package com.sid.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sid.api.utility.Constants;
import com.sid.api.utility.SQLQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sid.api.dao.CommonDAO;
import com.sid.api.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CommonDAO commonDAO;

    @Transactional
    @Override
    public Map<String, String> addCategory(Map<String, Object> request) {
        Map<String, String> response = new HashMap<>();
        String sqlQuery = SQLQueries.INSERT_INTO_CATEGORY_QUERY;
        String sqlQueryForCategoryInfluence = SQLQueries.INSTER_INTO_CATEGORY_INFLUENCE_QUERY;
        try {
            String categoryId = UUID.randomUUID().toString();
            String categoryName = (String) request.get(Constants.CATEGORY_NAME);
            String description = (String) request.get(Constants.DESCRIPTION);
            @SuppressWarnings("unchecked")
            List<String> influenceIds = (List<String>) request.get(Constants.INFLUENCE_IDS);
            Object[] params = {categoryId, categoryName, description};
            commonDAO.update(sqlQuery, params);

            for (String influenceId : influenceIds) {
                String categoryInfluenceId = UUID.randomUUID().toString();
                Object[] paramsForCategoryInfluence = {categoryInfluenceId, categoryId, influenceId};
                commonDAO.update(sqlQueryForCategoryInfluence, paramsForCategoryInfluence);
            }

            response.put(Constants.STATUS, Constants.OK);
            response.put(Constants.MESSAGE, Constants.CATEGORY_ADDED_SUCCESSFULLY);

        } catch (Exception e) {
            response.put(Constants.STATUS, Constants.ERROR);
            response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
        }
        return response;
    }

    @Transactional
    @Override
    public Map<String, String> addCategories(List<Map<String, Object>> request) {
        Map<String, String> response = new HashMap<>();
        String sqlQuery = SQLQueries.INSERT_INTO_CATEGORY_QUERY;
        String sqlQueryForCategoryInfluence = SQLQueries.INSTER_INTO_CATEGORY_INFLUENCE_QUERY;
        try {

            for (Map<String, Object> categoryMap : request) {
                String categoryId = UUID.randomUUID().toString();
                String categoryName = (String) categoryMap.get(Constants.CATEGORY_NAME);
                String description = (String) categoryMap.get(Constants.DESCRIPTION);
                @SuppressWarnings("unchecked")
                List<String> influenceIds = (List<String>) categoryMap.get(Constants.INFLUENCE_IDS);
                Object[] params = {categoryId, categoryName, description};
                commonDAO.update(sqlQuery, params);

                for (String influenceId : influenceIds) {
                    String categoryInfluenceId = UUID.randomUUID().toString();
                    Object[] paramsForCategoryInfluence = {categoryInfluenceId, categoryId, influenceId};
                    commonDAO.update(sqlQueryForCategoryInfluence, paramsForCategoryInfluence);
                }
            }
            response.put(Constants.STATUS, Constants.OK);
            response.put(Constants.MESSAGE, Constants.CATEGORY_ADDED_SUCCESSFULLY);

        } catch (Exception e) {
            response.put(Constants.STATUS, Constants.ERROR);
            response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
        }
        return response;
    }

    @Override
    public List<Map<String, Object>> fetchAllCategories() {
        List<Map<String, Object>> data = new ArrayList<>();
        List<Map<String, Object>> response = new ArrayList<>();
        String sqlQuery = SQLQueries.FETCH_ALL_CATEGORIES_QUERY;
        String sqlQueryForCategoryInfluences = SQLQueries.FETCH_CATEGORY_INFLUENCES_QUERY;
        String sqlQueryForCategoryInfluencesIds = SQLQueries.FETCH_CATEGORY_INFLUENCES_IDS_QUERY;

        try {

            data = commonDAO.fetch(sqlQuery);

            for (Map<String, Object> category : data) {

                String id = (String) category.get(Constants.ID);
                Object[] params = {id};
                List<String> categoryInfluences = commonDAO.fetchDataList(sqlQueryForCategoryInfluences, params);
                List<String> categoryInfluencesIds = commonDAO.fetchDataList(sqlQueryForCategoryInfluencesIds, params);
                category.put(Constants.CATEGORY_INFLUENCES, categoryInfluences);
                category.put(Constants.INFLUENCE_IDS, categoryInfluencesIds);
                response.add(category);
            }

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put(Constants.STATUS, Constants.ERROR);
            errorResponse.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);

            response.add(errorResponse);
        }
        return response;
    }

    @Transactional
    @Override
    public Map<String, Object> removeCategories(List<Map<String, String>> request) {
        Map<String, Object> response = new HashMap<>();
        String sqlQuery = SQLQueries.DELETE_FROM_CATEGORY_QUERY;
        String sqlQueryForCategoryInfluences = SQLQueries.DELETE_CATEGORY_INFLUENCES_QUERY;
        try {

            for (Map<String, String> data : request) {
                String categoryId = data.get(Constants.CATEGORY_ID);

                Object[] params = {categoryId};
                commonDAO.update(sqlQuery, params);
                commonDAO.update(sqlQueryForCategoryInfluences, params);
            }

            response.put(Constants.STATUS, Constants.OK);
            response.put(Constants.MESSAGE, Constants.INFLUENCES_REMOVED_SUCCESSFULLY);

        } catch (Exception e) {
            response.put(Constants.STATUS, Constants.ERROR);
            response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
        }
        return response;
    }

    @Transactional
    @Override
    public Map<String, String> updateCategory(Map<String, Object> request) {
        Map<String, String> response = new HashMap<>();
        String sqlQuery = SQLQueries.UPDATE_CATEGORY_QUERY;
        String sqlQueryDeleteCategoryInfluencesQuery = SQLQueries.DELETE_CATEGORY_INFLUENCES_QUERY;
        String sqlQueryForCategoryInfluence = SQLQueries.INSTER_INTO_CATEGORY_INFLUENCE_QUERY;
        try {
            String categoryId = request.get(Constants.CATEGORY_ID).toString();
            String categoryName = (String) request.get(Constants.CATEGORY_NAME);
            String description = (String) request.get(Constants.DESCRIPTION);
            @SuppressWarnings("unchecked")
            List<String> influenceIds = (List<String>) request.get(Constants.INFLUENCE_IDS);
            Object[] params = {categoryName, description, categoryId};
            commonDAO.update(sqlQuery, params);

            Object[] paramsForDeleteCategoryInfluence = {categoryId};
            commonDAO.update(sqlQueryDeleteCategoryInfluencesQuery, paramsForDeleteCategoryInfluence);

            for (String influenceId : influenceIds) {
                String categoryInfluenceId = UUID.randomUUID().toString();
                Object[] paramsForCategoryInfluence = {categoryInfluenceId, categoryId, influenceId};
                commonDAO.update(sqlQueryForCategoryInfluence, paramsForCategoryInfluence);
            }

            response.put(Constants.STATUS, Constants.OK);
            response.put(Constants.MESSAGE, Constants.CATEGORY_UPDATED_SUCCESSFULLY);

        } catch (Exception e) {
            response.put(Constants.STATUS, Constants.ERROR);
            response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
        }
        return response;
    }

}
