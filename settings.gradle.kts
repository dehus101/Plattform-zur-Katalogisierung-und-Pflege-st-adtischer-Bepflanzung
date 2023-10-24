pluginManagement {
    repositories {
        gradlePluginPortal()
        maven {
            name = "GitHhuGroup8698"
            setUrl("https://git.hhu.de/api/v4/groups/8698/-/packages/maven")
            credentials(HttpHeaderCredentials::class) {
                name = "Deploy-Token"
                value = "FU6y94JWmErw9EyR9Wxh"
            }
            authentication { register("header", HttpHeaderAuthentication::class) }
        }
    }
}

plugins { id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0" }

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven {
            name = "GitHhuGroup8698"
            setUrl("https://git.hhu.de/api/v4/groups/8698/-/packages/maven")
            credentials(HttpHeaderCredentials::class) {
                name = "Deploy-Token"
                value = "FU6y94JWmErw9EyR9Wxh"
            }
            authentication { register("header", HttpHeaderAuthentication::class) }
        }
    }

    versionCatalogs {
        register("libs") { from("de.hhu.cs.dbs.dbwk.project:versioncatalog:latest.integration") }
    }

    include("blatt1")
    include("blatt2")
    include("blatt3")
    include("blatt4")
}

rootProject.name = "template"
