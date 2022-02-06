package com.webapp.worldgdp.service;

import com.webapp.worldgdp.model.CountryGDP;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
public class WorldBankApiClient {
    String GDP_URL = "http://api.worldbank.org/countries/%s/indicators/NY.GDP.MKTP.CD?" + "format=json&date=2008:2018";

    public List<CountryGDP> getGDP(String countryCode) throws ParseException {
        RestTemplate worldBankRestTemplate = new RestTemplate();
        ResponseEntity<String> response = worldBankRestTemplate.getForEntity(String.format(GDP_URL, countryCode), String.class);
        JSONParser parser = new JSONParser();
        JSONArray responseData = (JSONArray) parser.parse(response.getBody());
        JSONArray countryData = (JSONArray) responseData.get(1);
        List<CountryGDP> gdps = new ArrayList<>();
        for (Object countryDatum : countryData) {
            JSONObject countryGDPYearWise = (JSONObject) countryDatum;
            CountryGDP gdp = new CountryGDP();
            String value = "0";
            if (countryGDPYearWise.get("value") != null) value = countryGDPYearWise.get("value").toString();
            String year = countryGDPYearWise.get("date").toString();
            gdp.setValue(Double.parseDouble(value));
            gdp.setYear(Short.parseShort(year));
            gdps.add(gdp);
        }
        return gdps;
    }
}
