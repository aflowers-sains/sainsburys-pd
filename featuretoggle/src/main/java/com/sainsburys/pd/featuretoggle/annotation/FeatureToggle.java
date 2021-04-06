package com.sainsburys.pd.featuretoggle.annotation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(prefix = "feature.toggle")
public @interface FeatureToggle {
    String name() default "";

    String value() default "";
}
