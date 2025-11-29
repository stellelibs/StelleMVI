rootProject.name = "StelleMVI"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }

}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}


include(":composeApp")
include(":app:subfeatures:landing:data")
include(":app:subfeatures:landing:domain")
include(":app:subfeatures:landing:lib")
include(":app:subfeatures:landing:presentation")

include(":app:subfeatures:detail:lib")
include(":app:subfeatures:detail:presentation")

include(":app:subfeatures:common:lib")
include(":app:subfeatures:common:domain")
include(":app:subfeatures:common:presentation")
include(":library:mvi")
include(":library:mvi-compose")
include(":library:mvi-compose-koin")
include(":library:mvi-koin")
include(":library:usecase")
include(":library:dispatchers")



includeBuild("build-logic")
includeBuild("build-logic-dsl")

