package com.stelle.plugins
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

class KmpTargetsPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.extensions.configure(KotlinMultiplatformExtension::class.java) {

            val isSampleModule = !project.hasProperty("POM_ARTIFACT_ID")
            val extendedTarget = project.hasProperty("EXT_TARGET")

            val ideaActive = System.getProperty("idea.active") == "true"
            jvm()

            js {
                browser()
                if (isSampleModule) binaries.executable()
            }

            @OptIn(ExperimentalWasmDsl::class) wasmJs {
                browser()
                if (isSampleModule) binaries.executable()
            }

            androidTarget {
                @OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class) compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_11)
                }
            }

            listOf(
                iosArm64(),
                iosSimulatorArm64()
            ).process(isSampleModule)
            //Optimize sync
            if (!ideaActive) {
                listOf(
                    iosX64(),
                    macosX64(),
                    macosArm64(),
                ).process(isSampleModule)
            }
            //Not Supported by coil and others libs koin etc future support
            if (!isSampleModule && extendedTarget)
                listOf(
                    mingwX64(),
                    linuxX64(),
                    linuxArm64(),
                    watchosArm32(),
                    watchosArm64(),
                    watchosDeviceArm64(),
                    watchosSimulatorArm64(),
                    watchosX64(),
                    tvosArm64(),
                    tvosSimulatorArm64(),
                    tvosX64(),
                ).process()

        }
    }

    private fun List<KotlinNativeTarget>.process(isSampleModule: Boolean = false) {
        forEach { iosTarget: KotlinNativeTarget ->
            if (isSampleModule) {
                iosTarget.binaries.framework {
                    baseName = "ComposeApp"
                    isStatic = true
                }
            }
        }
    }
}
