plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.idpassportreader"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.idpassportreader"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation ("androidx.navigation:navigation-compose:2.6.0")

    implementation("androidx.compose.material:material:1.5.1")

    val cameraxVersion = "1.3.0-rc01"
    implementation("androidx.camera:camera-core:$cameraxVersion")
    implementation("androidx.camera:camera-camera2:$cameraxVersion")
    implementation("androidx.camera:camera-lifecycle:$cameraxVersion")
    implementation("androidx.camera:camera-video:$cameraxVersion")
    implementation("androidx.camera:camera-view:$cameraxVersion")
    implementation("androidx.camera:camera-extensions:$cameraxVersion")
    implementation ("com.google.mlkit:text-recognition:16.0.0")
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.28.0")
    implementation("androidx.compose.material3:material3:1.1.0")
    implementation( "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.x.x")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.x.x")
    implementation ("androidx.compose.runtime:runtime:1.x.x")
    implementation ("com.google.accompanist:accompanist-permissions:0.31.5-beta")

    implementation ("com.google.mlkit:face-detection:16.1.5")


    implementation ("org.jmrtd:jmrtd:0.7.18")
    implementation ("net.sf.scuba:scuba-sc-android:0.0.20")
    implementation ("com.madgag.spongycastle:prov:1.58.0.0")
    implementation ("edu.ucar:jj2000:5.2")
    implementation ("com.github.mhshams:jnbis:1.1.0")


    implementation("io.coil-kt:coil-compose:2.5.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.okio:okio:2.10.0")
    implementation("androidx.compose.foundation:foundation:1.4.0")


    implementation("androidx.activity:activity-ktx:1.6.1")
    implementation("androidx.compose.runtime:runtime-livedata:1.5.4")
    implementation("com.airbnb.android:lottie-compose:6.0.0")


    implementation("com.airbnb.android:lottie-compose:6.0.0")

    implementation("com.google.accompanist:accompanist-flowlayout:0.30.1")



}