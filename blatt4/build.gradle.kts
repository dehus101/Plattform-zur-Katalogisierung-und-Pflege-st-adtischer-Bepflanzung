import de.hhu.cs.dbs.dbwk.gradle.environments.envfile

plugins {
    id("de.hhu.cs.dbs.dbwk.project") version "latest.release"
    id("org.springframework.boot") version "3.1.3"
    id("io.spring.dependency-management") version "1.1.3"
}

tasks.bootJar {
    archiveBaseName.set("api")
    mainClass.set("de.hhu.cs.dbs.dbwk.project.api.Application")
}

tasks.bootRun {
    mainClass.set("de.hhu.cs.dbs.dbwk.project.api.Application")
    environment(envfile(".env"))
}

tasks.composeBuild { context.setFrom(tasks.bootJar) }

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.xerial:sqlite-jdbc:3.43.0.0")
}
