package com.webapp.worldgdp.dao;

import com.webapp.worldgdp.config.DataSourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LookUpDao {

    @Autowired
    DataSourceConfig dataSourceConfig;

    public List<String> getContinents() {
        MapSqlParameterSource params = new MapSqlParameterSource();
        return dataSourceConfig.namedParamJdbcTemplate().queryForList("SELECT DISTINCT continent FROM country " + " ORDER BY continent", params, String.class);
    }

    public List<String> getRegions() {
        MapSqlParameterSource params = new MapSqlParameterSource();
        return dataSourceConfig.namedParamJdbcTemplate().queryForList("SELECT DISTINCT region FROM country " + " ORDER BY region", params, String.class);
    }

    public List<String> getHeadOfStates() {
        MapSqlParameterSource params = new MapSqlParameterSource();
        return dataSourceConfig.namedParamJdbcTemplate().queryForList("SELECT DISTINCT headofstate FROM country " + " ORDER BY headofstate", params, String.class);
    }

    public List<String> getGovernmentTypes() {
        MapSqlParameterSource params = new MapSqlParameterSource();
        return dataSourceConfig.namedParamJdbcTemplate().queryForList("SELECT DISTINCT governmentform FROM country " + " ORDER BY governmentform", params, String.class);
    }


}
