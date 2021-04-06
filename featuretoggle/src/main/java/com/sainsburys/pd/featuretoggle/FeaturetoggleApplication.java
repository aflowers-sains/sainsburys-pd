package com.sainsburys.pd.featuretoggle;

import com.sainsburys.pd.featuretoggle.features.MyFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
@SpringBootApplication
public class FeaturetoggleApplication implements CommandLineRunner {

    @Autowired
    private MyFeature myFeature;

    public static void main(String[] args) {
        SpringApplication.run(FeaturetoggleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        myFeature.doStuff();
    }
}
