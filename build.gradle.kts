import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.21"
    kotlin("plugin.spring") version "1.4.21"
    kotlin("plugin.jpa") version "1.4.21"
    id("org.springframework.boot") version "2.4.1"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"
}

group = "com.finances"
version = "0.0.1-SNAPSHOT"
description = "Finances easy API"

repositories {
    mavenLocal()
    mavenCentral()
    maven { url = uri("https://plugins.gradle.org/m2/") }
    maven { url = uri("https://repo.spring.io/snapshot") }
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }
    maven { url = uri("https://repo.maven.apache.org/maven2") }
}

extra["swaggerVersion"] = "3.0.0"
extra["postgresVersion"] = "42.2.5"
extra["jodaVersion"] = "2.10.1"
extra["mockkVersion"] = "1.10.5"
extra["springMockkVersion"] = "3.0.1"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.liquibase:liquibase-core")
    implementation("org.mockito:mockito-core")
    implementation("org.postgresql:postgresql:${property("postgresVersion")}")
    implementation("org.apache.commons:commons-lang3")
    implementation("joda-time:joda-time:${property("jodaVersion")}")
    implementation("io.springfox:springfox-boot-starter:${property("swaggerVersion")}")
    implementation("io.springfox:springfox-swagger2:${property("swaggerVersion")}")
    implementation("io.springfox:springfox-swagger-ui:${property("swaggerVersion")}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.micrometer:micrometer-registry-prometheus")
    runtimeOnly("org.jetbrains.kotlin:kotlin-reflect:1.4.21")
    runtimeOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("com.ninja-squad:springmockk:${property("springMockkVersion")}")
    testRuntimeOnly("com.h2database:h2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "15"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootBuildImage> {
    imageName = "alexandre3gomes/finances-easy-api"
}