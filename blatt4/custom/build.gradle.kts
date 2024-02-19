import de.hhu.cs.dbs.dbwk.gradle.environments.envfile


plugins {
    id("de.hhu.cs.dbs.dbwk.project")
    id("org.springframework.boot") version "3.1.3"
    id("io.spring.dependency-management") version "1.1.3"
}


tasks.bootRun { environment(envfile(".env")) }

dependencies {
    specification(libs.blatt4)
    implementation("org.xerial:sqlite-jdbc:3.43.0.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
}