import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.spring") version "1.7.10"
    kotlin("plugin.jpa") version "1.7.10"
    id("org.springframework.boot") version "2.7.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
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

extra["springdocVersion"] = "1.6.4"
extra["postgresVersion"] = "42.2.5"
extra["jodaVersion"] = "2.10.1"
extra["mockkVersion"] = "1.10.5"
extra["springMockkVersion"] = "3.0.1"
extra["restAssuredVersion"] = "4.4.0"
extra["poiVersion"] = "5.2.2"

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
    implementation("org.springdoc:springdoc-openapi-ui:${property("springdocVersion")}")
    implementation("org.springdoc:springdoc-openapi-security:${property("springdocVersion")}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("org.apache.poi:poi:${property("poiVersion")}")
    implementation("org.apache.poi:poi-ooxml:${property("poiVersion")}")
    runtimeOnly("org.jetbrains.kotlin:kotlin-reflect:1.4.21")
    runtimeOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("com.ninja-squad:springmockk:${property("springMockkVersion")}")
    testImplementation("io.rest-assured:kotlin-extensions:${property("restAssuredVersion")}")
    testRuntimeOnly("com.h2database:h2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootBuildImage> {
    imageName = "alexandre3gomes/finances-easy-api"
}
