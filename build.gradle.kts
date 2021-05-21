buildscript {
    val compose_version by extra("1.0.0-beta07")
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.0-beta02")
        @Suppress("GradleDependency") // compose と 1.5.0 が合わない
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.4.32")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.31-alpha")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
