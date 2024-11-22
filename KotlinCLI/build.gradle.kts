plugins {
    kotlin("jvm") version "1.9.25"
    application
}

group = "me.user"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

var spek_version = "2.0.15"
var kotest_version = "4.6.0"

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spek_version")
    testImplementation("io.kotest:kotest-runner-junit5:$kotest_version")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spek_version")

    // spek requires kotlin-reflect, can be omitted if already in the classpath
    testRuntimeOnly("org.jetbrains.kotlin:kotlin-reflect:1.4.32")
}

tasks.test {
    useJUnitPlatform {
        //includeEngines("spek2")
    }
}

application {
    mainClass = "MainKt"
}
