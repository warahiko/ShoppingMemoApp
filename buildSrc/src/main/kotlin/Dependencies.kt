object Dependencies {
    object Kotlin {
        const val gradlePlugin =
            "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Kotlin.version}"
        const val serialization =
            "org.jetbrains.kotlin:kotlin-serialization:${Versions.Kotlin.version}"
    }
}

private object Versions {
    object Kotlin {
        const val version = "1.5.21"
    }
}
