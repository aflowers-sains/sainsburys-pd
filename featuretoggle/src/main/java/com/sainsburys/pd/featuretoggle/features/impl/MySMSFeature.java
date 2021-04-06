package com.sainsburys.pd.featuretoggle.features.impl;

import com.sainsburys.pd.featuretoggle.annotation.FeatureToggle;
import com.sainsburys.pd.featuretoggle.features.MyFeature;
import org.springframework.stereotype.Component;

@FeatureToggle(name = "myfeature", value = "sms")
//@ConditionalOnProperty(prefix = "feature.toggle", name = "myfeature", havingValue = "sms")
@Component
public class MySMSFeature implements MyFeature {
    @Override
    public void doStuff() {
        System.out.println("Do some sort of SMS stuff");
    }
}
