import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlinAndroidKsp)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.room)
    alias(libs.plugins.serialization)
}

val appConfigPropertiesFile = rootProject.file("appConfig.properties")
val appConfig = Properties()
appConfig.load(FileInputStream(appConfigPropertiesFile))

android {
    namespace = "com.example.film"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.film"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    room {
        schemaDirectory("$projectDir/schemas")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildFeatures {
        buildConfig = true
    }

    flavorDimensions += "app"

    productFlavors {
        create("dev") {
            dimension = "app"
            manifestPlaceholders["appLabel"] = "@string/app_name_dev"
            buildConfigField("String", "BASE_URL", "\"${appConfig["development_url"]}\"")
            applicationIdSuffix = ".dev"
        }
        create("production") {
            dimension = "app"
            manifestPlaceholders["appLabel"] = "@string/app_name"
            buildConfigField("String", "BASE_URL", "\"${appConfig["production_url"]}\"")
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.dagger.hilt.android)

    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    ksp(libs.hilt.compiler)

    implementation(libs.compose.lifecyle)
    implementation(libs.compose.viewmodel)
    implementation(libs.compose.livedate)

    implementation(libs.retrofit)
    implementation(libs.retrofit.conventer)
    implementation(libs.okhttp.interceptor)

    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    implementation(libs.room.paging)
    implementation(libs.room.testing)

    implementation(libs.coli)

    implementation(libs.serialization)

    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}