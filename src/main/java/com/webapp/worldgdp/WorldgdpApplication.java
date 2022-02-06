package com.webapp.worldgdp;

import com.webapp.worldgdp.config.DataSourceConfig;
import com.webapp.worldgdp.config.ProjectConfig;
import com.webapp.worldgdp.dao.CityDao;
import com.webapp.worldgdp.dao.CountryDao;
import com.webapp.worldgdp.model.City;
import com.webapp.worldgdp.model.Country;
import com.webapp.worldgdp.service.WorldBankApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashMap;
import java.util.List;

@SpringBootApplication
public class WorldgdpApplication {


    public static void main(String[] args) throws Exception{
        SpringApplication.run(WorldgdpApplication.class, args);

//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProjectConfig.class);
//        CountryDao countryDao = context.getBean(CountryDao.class);
//        Country country =  countryDao.getCountryDetails("UKR");
//        country.setIndepYear((short) 2023);
//        countryDao.editCountryDetail("UKR", country);
//        country =  countryDao.getCountryDetails("UKR");
//        System.out.println(country);
//
//        CityDao cityDao = context.getBean(CityDao.class);
//        List<City> cities = cityDao.getCities("UKR");
//
//        WorldBankApiClient client = context.getBean(WorldBankApiClient.class);
//        System.out.println(client.getGDP("UKR"));
    }

}
