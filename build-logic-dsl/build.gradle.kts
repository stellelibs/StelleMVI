plugins {
    `kotlin-dsl`
}
private val packagePlugin = "com.stelle.plugins."
private val pluginTargetName = "KmpTargetsPlugin"
gradlePlugin {
    plugins {
        create(pluginTargetName) {
            id = packagePlugin + pluginTargetName
            implementationClass = packagePlugin + pluginTargetName
        }
    }
}

repositories {
    google()
    mavenCentral()
}
dependencies {
    implementation(libs.kotlin.gradle.plugin)
}