package com.webapp.worldgdp.controller.api;

import com.webapp.worldgdp.dao.CountryDao;
import com.webapp.worldgdp.model.Country;
import com.webapp.worldgdp.model.CountryGDP;
import com.webapp.worldgdp.service.WorldBankApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/countries")
@Slf4j
public class CountryAPIController {

    @Autowired
    WorldBankApiClient apiClient;

    @Autowired
    CountryDao countryDao;

    @GetMapping
    public ResponseEntity<?> getCountries(@RequestParam(required = false) String searchTerm, @RequestParam(required = false) String continent, @RequestParam(required = false) String region, @RequestParam(required = false) Integer pageNo) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("search", searchTerm);
            params.put("continent", continent);
            params.put("region", region);
            if (pageNo != null) params.put("pageNo", pageNo);
            List<Country> countries = countryDao.getCountries(params);
            int countriesCount = countryDao.getCountriesCount(params);
            Map<String, Object> response = new HashMap<>();
            response.put("list", countries);
            response.put("count", countriesCount);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while fetching countries");
        }
    }

    @PostMapping("/{countryCode}")
    public ResponseEntity<?> editCountry(@PathVariable String countryCode, @Valid @RequestBody Country country) {
        try {
            countryDao.editCountryDetail(countryCode, country);
            Country countryFromDB = countryDao.getCountryDetails(countryCode);
            return ResponseEntity.ok(countryFromDB);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while editing the country");
        }
    }

    @GetMapping("/{countryCode}/gdp")
    public ResponseEntity<?> getGDP(@PathVariable String countryCode) {
        try {
            List<CountryGDP> gdpList = apiClient.getGDP(countryCode);
            return ResponseEntity.ok(gdpList);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while fetching the country GDP");

        }
    }

}
