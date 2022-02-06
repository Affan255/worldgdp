package com.webapp.worldgdp.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Data
@Getter
@Setter
@Component
public class CountryGDP {
    private Short year;
    private Double value;
}
