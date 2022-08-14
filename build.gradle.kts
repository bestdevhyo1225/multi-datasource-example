import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.2"
	id("io.spring.dependency-management") version "1.0.12.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("plugin.jpa") version "1.6.21"
	kotlin("plugin.allopen") version "1.6.21"
	kotlin("kapt") version "1.6.21"
}

allOpen {
	annotation("javax.persistence.Embeddable")
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.MappedSuperclass")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.querydsl:querydsl-jpa:5.0.0")

	kapt("com.querydsl:querydsl-apt:5.0.0:jpa")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor(group = "com.querydsl", name = "querydsl-apt", classifier = "jpa")

	runtimeOnly("mysql:mysql-connector-java")
	runtimeOnly("com.h2database:h2")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")
	testImplementation("io.kotest:kotest-assertions-core:5.4.1")
	testImplementation("io.kotest:kotest-assertions-core-jvm:5.4.1")
	testImplementation("io.kotest:kotest-extensions-spring:4.4.3")
	testImplementation("io.kotest:kotest-runner-junit5-jvm:5.4.1")
	testImplementation("io.mockk:mockk:1.12.5")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

kotlin.sourceSets.main {
	setBuildDir("$buildDir")
}
