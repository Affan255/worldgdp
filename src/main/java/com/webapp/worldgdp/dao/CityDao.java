package com.webapp.worldgdp.dao;

import com.webapp.worldgdp.Constants;
import com.webapp.worldgdp.config.DataSourceConfig;
import com.webapp.worldgdp.mapper.CityRowMapper;
import com.webapp.worldgdp.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.webapp.worldgdp.Constants.PAGE_SIZE;

@Repository
public class CityDao {

    @Autowired
    private DataSourceConfig dataSourceConfig;

    public List<City> getCities(String countryCode, Integer pageNo) {
        Map<String, Object> params = new HashMap<>();
        params.put("code", countryCode);
        if (pageNo != null) {
            Integer offset = (pageNo - 1) * PAGE_SIZE.getPageSize();
            params.put("offset", offset);
            params.put("size", PAGE_SIZE.getPageSize());
        }

        return dataSourceConfig.namedParamJdbcTemplate().query("SELECT " + " id, name, countrycode country_code, district, population " + " FROM city WHERE countrycode = :code" + " ORDER BY Population DESC" + ((pageNo != null) ? " LIMIT :offset , :size " : ""), params, new CityRowMapper());
    }

    public List<City> getCities(String countryCode) {
        return getCities(countryCode, null);
    }

    public City getCityDetail(Long cityId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", cityId);
        return dataSourceConfig.namedParamJdbcTemplate().queryForObject("SELECT id, " + " name, countrycode country_code, " + " district, population " + " FROM city WHERE id = :id", params, new CityRowMapper());
    }

    public Long addCity(String countryCode, City city ) {
        SqlParameterSource parameterSource = new MapSqlParameterSource(getMapForCity(countryCode, city));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        dataSourceConfig.namedParamJdbcTemplate().update("INSERT INTO city (name, countrycode, district, population) VALUES(:name, :country_code,:district,:population)", parameterSource, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    private Map<String,Object> getMapForCity(String countryCode, City city) {
        Map<String, Object> cityMap = new HashMap<>();
        cityMap.put("name", city.getName());
        cityMap.put("country_code", city.getCountryCode());
        cityMap.put("district", city.getDistrict());
        cityMap.put("population", city.getPopulation());
        return cityMap;
    }

    public void deleteCity(Long cityId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", cityId);
        dataSourceConfig.namedParamJdbcTemplate().update("DELETE FROM city WHERE id = :id", params);
    }


}
