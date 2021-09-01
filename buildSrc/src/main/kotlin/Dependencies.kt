object Dependencies {
    const val buildGradle =
        "com.android.tools.build:gradle:${Versions.buildGradle}"
    const val ktLint =
        "com.pinterest:ktlint:${Versions.ktLint}"

    object Kotlin {
        const val gradlePlugin =
            "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val serialization =
            "org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}"
    }

    object KotlinX {
        const val serializationJson =
            "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.KotlinX.serializationJson}"
    }

    object AndroidX {
        const val coreKtx =
            "androidx.core:core-ktx:${Versions.AndroidX.coreKtx}"
        const val appCompat =
            "com.google.android.material:material:${Versions.AndroidX.appCompat}"
        const val lifecycleRuntimeKtx =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.AndroidX.lifecycle}"
        const val lifecycleViewModelCompose =
            "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.AndroidX.lifecycleViewModelCompose}"
        const val activityCompose =
            "androidx.activity:activity-compose:${Versions.AndroidX.activityCompose}"
        const val navigationFragmentKtx =
            "androidx.navigation:navigation-fragment-ktx:${Versions.AndroidX.navigation}"
        const val navigationUiKtx =
            "androidx.navigation:navigation-ui-ktx:${Versions.AndroidX.navigation}"
        const val navigationCompose =
            "androidx.navigation:navigation-compose:${Versions.AndroidX.navigationCompose}"

        object Compose {
            const val version = Versions.AndroidX.compose
            const val compiler =
                "androidx.compose.compiler:compiler:${Versions.AndroidX.compose}"
            const val ui =
                "androidx.compose.ui:ui:${Versions.AndroidX.compose}"
            const val uiTooling =
                "androidx.compose.ui:ui-tooling:${Versions.AndroidX.compose}"
            const val material =
                "androidx.compose.material:material:${Versions.AndroidX.compose}"
            const val materialIconsExtended =
                "androidx.compose.material:material-icons-extended:${Versions.AndroidX.compose}"
            const val uiTestJUnit4 =
                "androidx.compose.ui:ui-test-junit4:${Versions.AndroidX.compose}"
        }

        object Test {
            const val extJUnit =
                "androidx.test.ext:junit:${Versions.AndroidX.Test.extJUnit}"
            const val espressoCore =
                "androidx.test.espresso:espresso-core:${Versions.AndroidX.Test.espressoCore}"
        }
    }

    object DaggerHilt {
        const val androidGradlePlugin =
            "com.google.dagger:hilt-android-gradle-plugin:${Versions.daggerHilt}"
        const val android =
            "com.google.dagger:hilt-android:${Versions.daggerHilt}"
        const val androidCompiler =
            "com.google.dagger:hilt-android-compiler:${Versions.daggerHilt}"
    }

    const val androidMaterial =
        "com.google.android.material:material:${Versions.androidMaterial}"
    const val swipeRefresh =
        "com.google.accompanist:accompanist-swiperefresh:${Versions.swipeRefresh}"
    const val viewPager =
        "com.google.accompanist:accompanist-pager:${Versions.viewPager}"

    // network
    const val retrofit2 =
        "com.squareup.retrofit2:retrofit:${Versions.retrofit2}"
    const val retrofit2KotlinXSerializationConverter =
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.retrofit2KotlinXSerializationConverter}"
    const val okhttp3LoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3}"

    // test
    const val jUnit4 =
        "junit:junit:${Versions.jUnit4}"
}

private object Versions {
    const val buildGradle = "7.0.1"
    const val kotlin = "1.5.21"
    const val ktLint = "0.41.0"

    object KotlinX {
        const val serializationJson = "1.2.1"
    }

    object AndroidX {
        const val coreKtx = "1.6.0"
        const val appCompat = "1.4.0"
        const val lifecycle = "2.3.1"
        const val lifecycleViewModelCompose = "1.0.0-alpha07"
        const val activityCompose = "1.3.1"
        const val navigation = "2.3.5"
        const val navigationCompose = "2.4.0-alpha06"
        const val compose = "1.0.1"

        object Test {
            const val extJUnit = "1.1.3"
            const val espressoCore = "3.4.0"
        }
    }

    const val androidMaterial = "1.4.0"
    const val swipeRefresh = "0.11.1"
    const val viewPager = "0.17.0"
    const val daggerHilt = "2.37"

    // network
    const val retrofit2 = "2.9.0"
    const val retrofit2KotlinXSerializationConverter = "0.8.0"
    const val okhttp3 = "4.9.1"

    // test
    const val jUnit4 = "4.13.2"
}
