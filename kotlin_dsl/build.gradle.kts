plugins {
    kotlin("jvm") version "1.9.25"
    application
}

group = "uk.co.sainsburys"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

dependencies {
    runtimeOnly("org.jetbrains.kotlin:kotlin-scripting-jsr223:2.0.20")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

application {
    mainClass.set("MainKt")
}