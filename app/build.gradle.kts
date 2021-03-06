import com.android.build.gradle.internal.api.BaseVariantOutputImpl

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {

    compileSdkVersion(Versions.Android.compileSdkVersion)

    defaultConfig {

        applicationId = "github.activity"

        versionCode = project.properties["GitHub_ActivityWidget_Android_versionCode"].toString().toInt()
        versionName = "1.0.0"

        minSdkVersion(Versions.Android.minSdkVersion)
        targetSdkVersion(Versions.Android.targetSdkVersion)

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    applicationVariants.all {
        outputs
                .map { it as BaseVariantOutputImpl }
                .forEach { output ->
                    output.outputFileName = output.outputFileName
                            .replace("app-", "GitHub-ActivityWidget-Android-")
                            .replace(".apk", "-$versionName.$versionCode.apk")
                            .replace("-release", "")
                }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

}

kapt {
    arguments {
        arg("androidManifestFile", project.android.applicationVariants.first().outputs.first().processResources.manifestFile)
        arg("resourcePackageName", project.android.defaultConfig.applicationId!!)
    }
}

dependencies {

    implementation(project(":feature-widget"))
    implementation(project(":feature-dao"))
    implementation(project(":feature-github"))

    implementation(kotlin("stdlib-jdk8"))

    implementation(Dependencies.Dagger.android)
    kapt(Dependencies.Dagger.compiler)

    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.Work.runtimeKtx)

    implementation("com.noveogroup.android:android-logger:1.3.5")

}
