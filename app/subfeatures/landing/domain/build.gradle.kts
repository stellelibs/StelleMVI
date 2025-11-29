

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kmpTargetsPlugin)
}


kotlin {


    sourceSets {
        commonMain {
            kotlin.srcDir("build/generated/ksp/metadata")

            dependencies {
                api(projects.library.usecase)

                api(projects.app.subfeatures.common.domain)
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
    namespace = "com.stelle.apps.mvi.sample.subfeatures.landing.domain"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
