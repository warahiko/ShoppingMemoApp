import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.FileInputStream
import java.util.Properties

val ktlint by configurations.creating

val notionProperties = Properties()
notionProperties.load(FileInputStream(rootProject.file("notion.properties")))

fun String.asJavaStringLiteral(): String =
    "\"${replace("\\", "\\\\").replace("\"", "\\\"")}\""

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 30
    buildToolsVersion = "30.0.3"

    // signing configs
    apply(from = "signingConfigs.gradle", to = android)

    defaultConfig {
        applicationId = "io.github.warahiko.shoppingmemoapp"
        minSdk = 23
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField(
            "String",
            "NOTION_BASE_URL",
            "https://api.notion.com/v1/".asJavaStringLiteral(),
        )
        buildConfigField(
            "String",
            "NOTION_TOKEN",
            notionProperties.getProperty("notionToken").asJavaStringLiteral(),
        )
        buildConfigField(
            "String",
            "NOTION_VERSION",
            notionProperties.getProperty("notionVersion").asJavaStringLiteral(),
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
            buildConfigField(
                "String",
                "DATABASE_ID",
                notionProperties.getProperty("databaseIdRelease").asJavaStringLiteral(),
            )
            buildConfigField(
                "String",
                "TAG_DATABASE_ID",
                notionProperties.getProperty("tagDatabaseIdRelease").asJavaStringLiteral(),
            )
        }
        debug {
            applicationIdSuffix = ".debug"
            buildConfigField(
                "String",
                "DATABASE_ID",
                notionProperties.getProperty("databaseIdDebug").asJavaStringLiteral(),
            )
            buildConfigField(
                "String",
                "TAG_DATABASE_ID",
                notionProperties.getProperty("tagDatabaseIdDebug").asJavaStringLiteral(),
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.AndroidX.Compose.version
    }
}

dependencies {
    implementation(Dependencies.androidMaterial)
    implementation(Dependencies.swipeRefresh)
    implementation(Dependencies.viewPager)
    implementation(Dependencies.viewPagerIndicators)

    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.lifecycleRuntimeKtx)
    implementation(Dependencies.AndroidX.lifecycleViewModelCompose)
    implementation(Dependencies.AndroidX.activityCompose)
    implementation(Dependencies.AndroidX.navigationFragmentKtx)
    implementation(Dependencies.AndroidX.navigationUiKtx)
    implementation(Dependencies.AndroidX.navigationCompose)
    implementation(Dependencies.AndroidX.hiltNavigationCompose)
    implementation(Dependencies.AndroidX.Compose.compiler)
    implementation(Dependencies.AndroidX.Compose.ui)
    implementation(Dependencies.AndroidX.Compose.material)
    implementation(Dependencies.AndroidX.Compose.materialIconsExtended)
    implementation(Dependencies.AndroidX.Compose.uiTooling)

    // dagger hilt
    implementation(Dependencies.DaggerHilt.android)
    kapt(Dependencies.DaggerHilt.androidCompiler)

    // network
    implementation(Dependencies.KotlinX.serializationJson)
    implementation(Dependencies.retrofit2)
    implementation(Dependencies.okhttp3LoggingInterceptor)
    implementation(Dependencies.retrofit2KotlinXSerializationConverter)

    // test
    testImplementation(Dependencies.jUnit4)
    androidTestImplementation(Dependencies.AndroidX.Test.extJUnit)
    androidTestImplementation(Dependencies.AndroidX.Test.espressoCore)
    androidTestImplementation(Dependencies.AndroidX.Compose.uiTestJUnit4)

    // debug
    implementation(Dependencies.timber)

    ktlint(Dependencies.ktLint)
}

val outputDir = "${project.buildDir}/reports/ktlint/"
val inputFiles = project.fileTree(mapOf("dir" to "src", "include" to "**/*.kt"))

val ktlintCheck by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)

    description = "Check Kotlin code style."
    classpath = ktlint
    main = "com.pinterest.ktlint.Main"
    args = listOf("--android", "--color", "src/**/*.kt")
    isIgnoreExitValue = true
}

val ktlintFormat by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)

    description = "Fix Kotlin code style deviations."
    classpath = ktlint
    main = "com.pinterest.ktlint.Main"
    args = listOf("-F", "src/**/*.kt")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.freeCompilerArgs += listOf(
        "-Xuse-experimental=kotlinx.serialization.ExperimentalSerializationApi",
        "-Xuse-experimental=com.google.accompanist.pager.ExperimentalPagerApi",
        "-Xuse-experimental=androidx.compose.foundation.ExperimentalFoundationApi",
    )
}
