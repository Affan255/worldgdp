package com.webapp.worldgdp.dao;

import com.mysql.cj.util.StringUtils;
import com.webapp.worldgdp.config.DataSourceConfig;
import com.webapp.worldgdp.mapper.CountryRowMapper;
import com.webapp.worldgdp.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CountryDao {

    public static class Helper {
        public static Map<String, Object> getCountryAsMap(String code, Country country){
            Map<String, Object> countryMap = new HashMap<String, Object>();
            countryMap.put("name", country.getName());
            countryMap.put("localName", country.getLocalName());
            countryMap.put("capital", country.getCapital().getId());
            countryMap.put("continent", country.getContinent());
            countryMap.put("region", country.getRegion());
            countryMap.put("headOfState", country.getHeadOfState());
            countryMap.put("governmentForm", country.getGovernmentForm());
            countryMap.put("indepYear", country.getIndepYear());
            countryMap.put("surfaceArea", country.getSurfaceArea());
            countryMap.put("population", country.getPopulation());
            countryMap.put("lifeExpectancy", country.getLifeExpectancy());
            countryMap.put("code", code);
            return countryMap;
        }
    }

    private static final int PAGE_SIZE = 20;
    private static final String SELECT_CLAUSE = "SELECT c.code, c.name, c.continent, c.region, c.surfaceArea, c.indepYear, c.population, c.lifeExpectancy, c.gnp, c.localName, c.governmentForm, c.headOfState, c.code2, c.capital, cy.name AS capital_name FROM country c LEFT OUTER JOIN city cy ON cy.id=c.capital";
    private static final String SEARCH_WHERE_CLAUSE = " AND (LOWER(c.name) LIKE CONCAT( '%', LOWER(:search), '%' )) ";
    private static final String CONTINENT_WHERE_CLAUSE = " AND c.continent=:continent ";
    private static final String REGION_WHERE_CLAUSE = " AND c.region=:region ";
    private static final String PAGINATION_CLAUSE = " ORDER BY c.code LIMIT :size OFFSET :offset ";

    @Autowired
    private DataSourceConfig dataSourceConfig;

    public List<Country> getCountries(Map<String, Object> params) {
        int pageNo = 1;
        if (params.containsKey("pageNo")) {
            pageNo = Integer.parseInt(params.get("pageNo").toString());
        }
        Integer offset = (pageNo - 1) * PAGE_SIZE;
        params.put("offset", offset);
        params.put("size", PAGE_SIZE);
        String search = !StringUtils.isNullOrEmpty((String) params.get("search")) ? SEARCH_WHERE_CLAUSE : "";
        String continent = !StringUtils.isNullOrEmpty((String) params.get("continent")) ? CONTINENT_WHERE_CLAUSE : "";
        String region = !StringUtils.isNullOrEmpty((String) params.get("region")) ? REGION_WHERE_CLAUSE : "";

        return dataSourceConfig.namedParamJdbcTemplate().query(SELECT_CLAUSE + " WHERE 1=1 " + search + continent + region + PAGINATION_CLAUSE, params, new CountryRowMapper());
    }

    public int getCountriesCount(Map<String, Object> params) {
        String search = !StringUtils.isNullOrEmpty((String) params.get("search")) ? SEARCH_WHERE_CLAUSE : "";
        String continent = !StringUtils.isNullOrEmpty((String) params.get("continent")) ? CONTINENT_WHERE_CLAUSE : "";
        String region = !StringUtils.isNullOrEmpty((String) params.get("region")) ? REGION_WHERE_CLAUSE : "";

        return dataSourceConfig.namedParamJdbcTemplate().queryForObject("SELECT COUNT(*) FROM country c " + " WHERE 1=1 " + search + continent + region, params, Integer.class);

    }

    public Country getCountryDetails(String code) {
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        return dataSourceConfig.namedParamJdbcTemplate().queryForObject(SELECT_CLAUSE + " WHERE code = :code ", params, new CountryRowMapper());
    }

    public void editCountryDetail(String code, Country country) {
        dataSourceConfig.namedParamJdbcTemplate().update(" UPDATE country SET " + " name = :name, " + " localname = :localName, " + " capital = :capital, " + " continent = :continent, " + " region = :region, " + " HeadOfState = :headOfState, " + " GovernmentForm = :governmentForm, " + " IndepYear = :indepYear, " + " SurfaceArea = :surfaceArea, " + " population = :population, " + " LifeExpectancy = :lifeExpectancy " + "WHERE Code = :code ", Helper.getCountryAsMap(code, country));
    }
}
