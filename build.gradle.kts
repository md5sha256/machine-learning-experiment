plugins {
    java
    idea
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "io.github.md5sha256"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("nz.ac.waikato.cms.weka:weka-stable:3.8.6")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks {

    assemble {
        dependsOn(shadowJar)
    }

}