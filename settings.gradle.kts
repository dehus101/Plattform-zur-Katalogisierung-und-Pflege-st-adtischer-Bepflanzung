pluginManagement {
    repositories {
        gradlePluginPortal()
        maven {
            val groupId = providers.gradleProperty("de.hhu.git.group.id").get()
            val deployToken = providers.gradleProperty("de.hhu.git.group.deploy-token").get()
            name = "GitHhuGroup$groupId"
            setUrl("https://git.hhu.de/api/v4/groups/$groupId/-/packages/maven")
            credentials(HttpHeaderCredentials::class) {
                name = "Deploy-Token"
                value = deployToken
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
            val groupId = providers.gradleProperty("de.hhu.git.group.id").get()
            val deployToken = providers.gradleProperty("de.hhu.git.group.deploy-token").get()
            name = "GitHhuGroup$groupId"
            setUrl("https://git.hhu.de/api/v4/groups/$groupId/-/packages/maven")
            credentials(HttpHeaderCredentials::class) {
                name = "Deploy-Token"
                value = deployToken
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
    include("blatt4:custom")
    include("blatt4:jersey")
    include("blatt4:spring")
}

rootProject.name = "submission"
