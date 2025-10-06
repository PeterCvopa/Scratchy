
buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    alias(libs.plugins.spotless) version "6.25.0"
}

subprojects {
    apply(plugin = "com.diffplug.spotless")
    spotless {
        kotlin {
            target("**/*.kt")
            targetExclude("${layout.buildDirectory}/**/*.kt")
            targetExclude("bin/**/*.kt")
            ktfmt(libs.versions.ktfmt.version.get()).kotlinlangStyle()
        }
        kotlinGradle {
            target("*.gradle.kts")
            ktlint()
        }
    }
    afterEvaluate {
        tasks.named("build") {
            dependsOn("spotlessApply")
        }
    }
}