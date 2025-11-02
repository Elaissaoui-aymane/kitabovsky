
plugins {
    alias(libs.plugins.android.application)

}

android {
    namespace = "com.example.kitabovski"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.kitabovski"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding= true // Enable View Binding for easier UI access
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.room.common.jvm)
    implementation(libs.room.runtime.android)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.messaging)
    implementation(libs.adapters)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    // AndroidX & UI Components
    implementation (libs.swiperefreshlayout)
    // Lifecycle, ViewModel, LiveData (for Controllers)
    implementation (libs.lifecycle.viewmodel)
    implementation (libs.lifecycle.livedata)
    implementation (libs.lifecycle.common.java8)
    annotationProcessor ("androidx.room:room-compiler:")
    // Image Loading - Glide
    implementation (libs.glide)
    annotationProcessor (libs.compiler)
    // Circle ImageView (for professional profile pictures)
    implementation (libs.circleimageview)
    // WorkManager for background tasks (BookDownloader)
    implementation (libs.work.runtime)
    // Testing
    testImplementation (libs.junit)
    androidTestImplementation (libs.junit.v115)
    androidTestImplementation (libs.espresso.core.v351)

}

infix fun Any.platform(s: String) {

}
