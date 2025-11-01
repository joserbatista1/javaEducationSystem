plugins {
	java
	id("org.springframework.boot") version "3.5.6"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
//	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
//    implementation("mysql:mysql-connector-java:8.0.33")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
//    implementation("org.springframework.security:spring-security-crypto:6.4.2")
    implementation("org.springframework:spring-jcl:6.1.13")
    // Jakarta Validation API
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")
// Use the latest stable version
// Hibernate Validator (Reference Implementation)
    implementation("org.hibernate.validator:hibernate-validator:8.0.1.Final")
// Use the latest stable version
// Jakarta Expression Language (required for Java SE environments)
    implementation("org.glassfish:jakarta.el:4.0.2")
// Use the latest stable version
    runtimeOnly("com.mysql:mysql-connector-j")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//    implementation ("org.springframework.boot:spring-boot-starter-security")
    implementation("org.apache.commons:commons-lang3:3.14.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
