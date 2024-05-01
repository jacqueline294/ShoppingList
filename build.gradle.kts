buildscript {
    extra.apply {
        set("compose_version", "1.1.0-beta01")
    }
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    id("com.android.application") version "8.3.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
