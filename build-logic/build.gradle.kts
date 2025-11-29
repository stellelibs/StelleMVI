plugins {
    id("java-gradle-plugin")
    alias(libs.plugins.kotlin.jvm)
}

private val packagePlugin = "com.stelle.plugins."
private val pluginPublish = "Publish"
gradlePlugin {
    plugins {

        create(packagePlugin + pluginPublish) {
            id = packagePlugin + pluginPublish
            implementationClass = packagePlugin + pluginPublish
        }
    }
}


dependencies {

    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.vanniktech.publish)
}
repositories {
    google()
    mavenCentral()
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(libs.versions.jdk.toolchain.get()))
}

