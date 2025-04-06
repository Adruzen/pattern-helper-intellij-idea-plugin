plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.17.1"
}

group = "com.example.helloplugin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // JUnit 5 for regular unit tests
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.1")

    // JUnit 4 vintage engine to support JUnit 3
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.10.1")

    testImplementation("org.mockito:mockito-core:5.8.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.8.0")
}

tasks {
    test {
        useJUnitPlatform() // Use JUnit Platform for JUnit 5
    }
}

intellij {
    version.set("2024.1")
    type.set("IC")
}

tasks {
    patchPluginXml {
        sinceBuild.set("241")
        untilBuild.set("241.*")
    }
}