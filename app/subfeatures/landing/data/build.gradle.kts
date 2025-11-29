

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kmpTargetsPlugin)
    alias(libs.plugins.kotlinSerializable)
}


kotlin {


    sourceSets {
        commonMain {
            kotlin.srcDir("build/generated/ksp/metadata")

            dependencies {
                api(libs.ktor.client.core)
                implementation(libs.kotlin.serialization)
                implementation(projects.app.subfeatures.landing.domain)
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(libs.junit)
            }
        }
    }
}

android {
    namespace = "com.stelle.apps.mvi.sample.subfeatures.landing.data"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
