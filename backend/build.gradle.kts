import org.jetbrains.kotlin.gradle.dsl.KotlinCompile

plugins {
	java
	id("org.springframework.boot") version "3.1.5"
	id("io.spring.dependency-management") version "1.1.3"
	kotlin("plugin.spring") version "1.6.0"
	kotlin("jvm")
}

group = "pl.gddkia"
version = "0.0.1-SNAPSHOT"
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
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-parent")
	implementation("org.springframework.boot:spring-boot-starter-security")

	implementation("io.jsonwebtoken:jjwt-api:$jwtVersion")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:$jwtVersion")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jwtVersion")


	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	implementation("org.apache.poi:poi:5.2.3")
	implementation("org.apache.poi:poi-ooxml:5.2.3")
	implementation("org.apache.commons:commons-lang3:3.12.0")

	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation(kotlin("stdlib-jdk8"))
}

tasks.withType<Test> {
	useJUnitPlatform()
}


kotlin {
	jvmToolchain(21)
}