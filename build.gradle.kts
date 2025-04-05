plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.17.1"
}

group = "com.example.helloplugin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
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