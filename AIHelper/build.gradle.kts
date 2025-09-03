plugins {
    alias(libs.plugins.springBoot)
    alias(libs.plugins.springDependencyManagement)
    java
    groovy
}

group = "uk.co.sainsburys"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.webflux)
    // Lombok for logging and annotations
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    testImplementation(libs.spring.boot.starter.test)
    testRuntimeOnly(libs.junit.platform.launcher)

    testImplementation(platform(libs.spock.bom))
    testImplementation(libs.spock.core)

    testImplementation(platform(libs.groovy.bom))
    testImplementation(libs.groovy)

    implementation(libs.graphql.java)
    implementation(libs.dgs.graphql.client)
}

tasks.withType<Test> {
    useJUnitPlatform()
}