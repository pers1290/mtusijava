plugins {
    id("java")
    id("application")
    id("com.gradleup.shadow") version "9.0.0-beta4"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation(libs.guava)

    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("ch.qos.logback:logback-classic:1.4.11")
    implementation("org.slf4j:slf4j-api:2.0.9")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    mainClass = "rrr.App"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.shadowJar {
    manifest {
        attributes(mapOf("Main-Class" to "rrr.App"))
    }
    archiveBaseName.set("lab-app")
    archiveVersion.set("1.0")
}

