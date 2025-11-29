package com.stelle.plugins

import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.SonatypeHost
import org.gradle.api.Plugin
import org.gradle.api.Project

class Publish : Plugin<Project> {
    override fun apply(target: Project) {

        // Asegúrate group/version antes de aplicar el plugin de publicación
        target.group = target.property("GROUP") as String
        target.version = target.property("VERSION_NAME") as String

        // Usa el plugin completo (no .base)
        target.plugins.apply("com.vanniktech.maven.publish")

        // Configuración segura y declarativa a través de la extensión
        target.extensions.configure(MavenPublishBaseExtension::class.java) { ext ->
            ext.publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)
            ext.signAllPublications()
            //  ext.pomFromGradleProperties() // lee el POM de gradle.properties
        }

        // NOTA: no usamos afterEvaluate ni tocamos PublishingExtension.publications aquí.
        // Si necesitas validar SNAPSHOT, hazlo con tareas independientes:
        target.tasks.register("checkVersionIsNotSnapshot") { task ->
            task.group = "publishing"
            task.description = "ensures that the project version does not have a -SNAPSHOT suffix"
            task.doLast {
                val vs = target.version.toString()
                require(!vs.endsWith("-SNAPSHOT")) { "The project's version name cannot have a -SNAPSHOT suffix, but it was $vs." }
            }
        }
    }
}