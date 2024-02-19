import de.hhu.cs.dbs.dbwk.gradle.environments.envfile

plugins {
    id("de.hhu.cs.dbs.dbwk.project")
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
}

tasks.bootRun { environment(envfile(".env")) }

tasks.composeBuild { context.setFrom(tasks.bootJar) }

dependencies {
    specification(libs.blatt4)
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    runtimeOnly("org.springframework.boot:spring-boot-properties-migrator")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.xerial:sqlite-jdbc:3.44.1.0")
}
