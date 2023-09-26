plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android") // Needed to resolve an error, I assume that's because Fragula uses kotlin
}

android {
    namespace = "com.pandacorp.weatherui"

    defaultConfig {
        applicationId = "com.pandacorp.weatherui"
        minSdk = 21
        compileSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    viewBinding.enable = true
}

dependencies {

    // Android
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.7.2")
    implementation("androidx.navigation:navigation-ui:2.7.2")
    implementation("androidx.preference:preference-ktx:1.2.1")
    implementation("com.google.android.gms:play-services-location:21.0.1")

    // Tests
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:5.4.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Custom Libraries
    implementation("com.fragula2:fragula-core:2.9") // Swipe to dismiss
    implementation("com.github.BirjuVachhani:locus-android:4.1.2") // Get the current location
    implementation("androidx.core:core-splashscreen:1.0.1") // Show the Splash screen

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.squareup.retrofit2:adapter-rxjava3:2.9.0") // Support for rxjava

    // RxJava
    implementation("io.reactivex.rxjava3:rxjava:3.1.6")
    implementation("io.reactivex.rxjava3:rxandroid:3.0.2")

    // Dagger
    val daggerVersion = "2.48"
    implementation("com.google.dagger:dagger:$daggerVersion")
    implementation("com.google.dagger:dagger-android-support:$daggerVersion")
    annotationProcessor("com.google.dagger:dagger-compiler:$daggerVersion")
    annotationProcessor("com.google.dagger:dagger-android-processor:$daggerVersion")

}