package com.webapp.worldgdp.controller.view;

import com.webapp.worldgdp.dao.CityDao;
import com.webapp.worldgdp.dao.CountryDao;
import com.webapp.worldgdp.dao.LookUpDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/")
public class ViewController {
    @Autowired
    private LookUpDao lookUpDao;

    @Autowired
    private CountryDao countryDao;

    @Autowired
    private CityDao cityDao;

    @GetMapping({"/countries", "/"})
    public String getCountries(Model model, @RequestParam Map<String, Object> params) {
        model.addAttribute("continents", lookUpDao.getContinents());
        model.addAttribute("countries", countryDao.getCountries(params));
        model.addAttribute("regions", lookUpDao.getRegions());
        model.addAttribute("count", countryDao.getCountriesCount(params));
        return "countries";
    }

    @GetMapping("/countries/{code}")
    public String countryDetail(@PathVariable String code, Model model) {
        model.addAttribute("c", countryDao.getCountryDetails(code));
        return "country";
    }

    @GetMapping("/countries/{code}/form")
    public String editCountry(@PathVariable String code, Model model) {
        model.addAttribute("c", countryDao.getCountryDetails(code));
        model.addAttribute("cities", cityDao.getCities(code));
        model.addAttribute("continents", lookUpDao.getContinents());
        model.addAttribute("regions", lookUpDao.getRegions());
        model.addAttribute("heads", lookUpDao.getHeadOfStates());
        model.addAttribute("govs", lookUpDao.getGovernmentTypes());
        return "country-form";
    }
}
