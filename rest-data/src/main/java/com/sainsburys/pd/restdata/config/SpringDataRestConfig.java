package com.sainsburys.pd.restdata.config;

import com.sainsburys.pd.restdata.data.Route;
import com.sainsburys.pd.restdata.data.Stop;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class SpringDataRestConfig implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.disableDefaultExposure();

        config.exposeIdsFor(Route.class);
        config.exposeIdsFor(Stop.class);
    }
}
