plugins {
	id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {

    compileSdkVersion(Versions.Android.compileSdkVersion)

    defaultConfig {

        minSdkVersion(Versions.Android.minSdkVersion)
        targetSdkVersion(Versions.Android.targetSdkVersion)

    }

}

dependencies {

    api(project(":feature-userActivity"))

    api(Dependencies.Dagger.dagger)
    kapt(Dependencies.Dagger.compiler)

    implementation(project(":feature-common"))
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.ApacheCommons.lang3)
    implementation(Dependencies.Slf4j.api)

}
