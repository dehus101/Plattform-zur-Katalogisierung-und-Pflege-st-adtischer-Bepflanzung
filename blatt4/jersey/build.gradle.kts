import de.hhu.cs.dbs.dbwk.gradle.environments.envfile

plugins { id("de.hhu.cs.dbs.dbwk.project") }

tasks.named<JavaExec>("run") { environment(envfile(".env")) }

tasks.composeBuild { context.setFrom(tasks.uberJar) }

dependencies {
    specification(libs.blatt4)
    implementation("org.xerial:sqlite-jdbc:3.44.1.0")
    implementation("jakarta.ws.rs:jakarta.ws.rs-api:3.1.0")
    implementation("org.glassfish.jersey.containers:jersey-container-grizzly2-http:3.1.3")
    implementation("org.glassfish.jersey.media:jersey-media-multipart:3.1.3")
    implementation("org.glassfish.jersey.media:jersey-media-json-jackson:3.1.3")
    implementation("com.fasterxml.jackson.jakarta.rs:jackson-jakarta-rs-json-provider:2.15.2")
    implementation("org.glassfish.jersey.inject:jersey-hk2:3.1.3")
    implementation("jakarta.annotation:jakarta.annotation-api:2.1.1")
    implementation("jakarta.inject:jakarta.inject-api:2.0.1")
}
