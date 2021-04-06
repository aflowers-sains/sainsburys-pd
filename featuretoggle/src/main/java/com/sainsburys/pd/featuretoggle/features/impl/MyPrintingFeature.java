package com.sainsburys.pd.featuretoggle.features.impl;

import com.sainsburys.pd.featuretoggle.annotation.FeatureToggle;
import com.sainsburys.pd.featuretoggle.features.MyFeature;
import org.springframework.stereotype.Component;

@FeatureToggle(name = "myfeature", value = "printing")
//@ConditionalOnProperty(prefix = "feature.toggle", name = "myfeature", havingValue = "printing")
@Component
public class MyPrintingFeature implements MyFeature {
    @Override
    public void doStuff() {
        System.out.println("Print some stuff");
    }
}
