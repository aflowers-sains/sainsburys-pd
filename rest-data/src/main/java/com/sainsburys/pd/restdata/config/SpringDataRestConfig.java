package com.sainsburys.pd.restdata.config;

import com.sainsburys.pd.restdata.data.Route;
import com.sainsburys.pd.restdata.data.Stop;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class SpringDataRestConfig implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.disableDefaultExposure();

        config.exposeIdsFor(Route.class);
        config.exposeIdsFor(Stop.class);
    }
}
