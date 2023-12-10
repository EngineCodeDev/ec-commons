import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    java
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
    id("maven-publish")
    id("groovy")
}

group = "dev.enginecode"
version = "0.0.3-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}
tasks.withType<JavaCompile> {
    options.compilerArgs.add("--enable-preview")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.1.5")
    implementation("org.springframework.boot:spring-boot-starter-web:3.1.5")
    implementation("org.postgresql:postgresql:42.6.0")

    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    implementation("com.querydsl:querydsl-sql:5.0.0")
    implementation("com.querydsl:querydsl-apt:5.0.0:jakarta")
    implementation("com.querydsl:querydsl-core:5.0.0")

    annotationProcessor("com.querydsl:querydsl-apt:5.0.0:jakarta")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api:3.1.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.5")
    testImplementation("org.spockframework:spock-core:2.4-M1-groovy-4.0")
    testImplementation("org.spockframework:spock-spring:2.4-M1-groovy-4.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
tasks.named<BootJar>("bootJar") {
    enabled = false
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }

            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
        }
    }

    repositories {
        mavenLocal()

        maven {
            name = "EngineCodeGitHubPackages"
            url = uri("https://maven.pkg.github.com/EngineCodeDev/packages")
            credentials {
                username = project.findProperty("gpr.user")?.toString() ?: System.getenv("GITHUB_ACTOR")
                password = project.findProperty("gpr.key")?.toString() ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}