package com.webapp.worldgdp.dao;

import com.webapp.worldgdp.config.DataSourceConfig;
import com.webapp.worldgdp.mapper.CountryLanguageRowMapper;
import com.webapp.worldgdp.model.CountryLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.webapp.worldgdp.Constants.PAGE_SIZE;

@Repository
public class CountryLanguageDao {

    @Autowired
    private DataSourceConfig dataSourceConfig;

    public List<CountryLanguage> getLanguages(String countryCode, Integer pageNo) {
        Map<String, Object> params = new HashMap<>();
        params.put("code", countryCode);

        Integer offset = (pageNo - 1) * PAGE_SIZE.getPageSize();
        params.put("offset", offset);
        params.put("size", PAGE_SIZE.getPageSize());

        return dataSourceConfig.namedParamJdbcTemplate().query("SELECT * FROM countrylanguage" + " WHERE countrycode = :code" + " ORDER BY percentage DESC " + " LIMIT :size OFFSET :offset ", params, new CountryLanguageRowMapper());
    }

    public void addLanguage(String countryCode, CountryLanguage cl) {
        dataSourceConfig.namedParamJdbcTemplate().update("INSERT INTO countrylanguage ( " + " countrycode, language, isofficial, percentage ) " + " VALUES ( :country_code, :language, " + " :is_official, :percentage ) ", getAsMap(countryCode, cl));
    }

    public boolean languageExists(String countryCode, String language) {
        Map<String, Object> params = new HashMap<>();
        params.put("code", countryCode);
        params.put("lang", language);

        Integer langCount = dataSourceConfig.namedParamJdbcTemplate().queryForObject("SELECT COUNT(*) FROM countrylanguage" + " WHERE countrycode = :code " + " AND language = :lang", params, Integer.class);
        return langCount > 0;
    }

    public void deleteLanguage(String countryCode, String language) {
        Map<String, Object> params = new HashMap<>();
        params.put("code", countryCode);
        params.put("lang", language);
        dataSourceConfig.namedParamJdbcTemplate().update("DELETE FROM countrylanguage " + " WHERE countrycode = :code AND " + " language = :lang ", params);
    }

    private Map<String, Object> getAsMap(String countryCode, CountryLanguage cl) {
        Map<String, Object> map = new HashMap<>();
        map.put("country_code", countryCode);
        map.put("language", cl.getLanguage());
        map.put("is_official", cl.getIsOfficial());
        map.put("percentage", cl.getPercentage());
        return map;
    }
}
