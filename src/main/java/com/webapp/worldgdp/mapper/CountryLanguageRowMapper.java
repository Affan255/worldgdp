package com.webapp.worldgdp.mapper;

import com.webapp.worldgdp.model.CountryLanguage;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CountryLanguageRowMapper implements RowMapper<CountryLanguage> {
    public CountryLanguage mapRow(ResultSet rs, int rowNum) throws SQLException {
        CountryLanguage countryLng = new CountryLanguage();
        countryLng.setCountryCode(rs.getString("countrycode"));
        countryLng.setIsOfficial(rs.getString("isofficial"));
        countryLng.setLanguage(rs.getString("language"));
        countryLng.setPercentage(rs.getDouble("percentage"));
        return countryLng;
    }
}
