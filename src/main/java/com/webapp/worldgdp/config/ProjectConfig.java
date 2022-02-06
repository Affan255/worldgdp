package com.webapp.worldgdp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.webapp.worldgdp.dao", "com.webapp.worldgdp.mapper", "com.webapp.worldgdp.model", "com.webapp.worldgdp.config", "com.webapp.worldgdp.service"})
public class ProjectConfig {
}
