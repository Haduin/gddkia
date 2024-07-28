import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.ir.backend.js.compile

plugins {
	java
	id("org.springframework.boot") version "3.2.0"
	id("io.spring.dependency-management") version "1.1.3"
	kotlin("plugin.spring") version "1.6.0"
	kotlin("jvm")
}

group = "pl.gddkia"
version = "0.1"
var jwtVersion = "0.11.5"

java {
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation(kotlin("stdlib-jdk8"))


	implementation("io.micrometer:micrometer-registry-prometheus")

	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	implementation("org.apache.poi:poi:5.2.3")
	implementation("org.apache.poi:poi-ooxml:5.2.3")
	implementation("org.apache.commons:commons-lang3:3.12.0")

	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test")

	testImplementation("org.testcontainers:testcontainers")
	testImplementation("org.testcontainers:postgresql:1.19.7")
	testImplementation("org.testcontainers:junit-jupiter")
}

tasks.withType<Test> {
	useJUnitPlatform()
}