package com.webapp.worldgdp.mapper;

import com.webapp.worldgdp.model.City;
import com.webapp.worldgdp.model.Country;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CountryRowMapper implements RowMapper<Country> {
    @Override
    public Country mapRow(ResultSet rs, int rowNum) throws SQLException {
        Country country = new Country();
        country.setCode(rs.getString("code"));
        country.setName(rs.getString("name"));
        country.setContinent(rs.getString("continent"));
        country.setRegion(rs.getString("region"));
        country.setSurfaceArea(rs.getDouble("surfaceArea"));
        country.setIndepYear(rs.getShort("indepYear"));
        country.setPopulation(rs.getLong("population"));
        country.setLifeExpectancy(rs.getDouble("lifeExpectancy"));
        country.setGnp(rs.getDouble("gnp"));
        country.setLocalName(rs.getString("localName"));
        country.setGovernmentForm(rs.getString("governmentForm"));
        country.setHeadOfState(rs.getString("headOfState"));
        country.setCode2(rs.getString("Code2"));
        if (Long.valueOf(rs.getLong("capital")) != null) {
            City city = new City();
            city.setId(rs.getLong("capital"));
            city.setName(rs.getString("capital_name"));
            country.setCapital(city);
        }
        return country;
    }
}
