import com.android.build.gradle.internal.api.BaseVariantOutputImpl

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {

    compileSdkVersion(Versions.Android.compileSdkVersion)
    buildToolsVersion(Versions.Android.buildToolsVersion)

    defaultConfig {

        applicationId = "github.activity"

        versionCode = project.properties["versionCode"].toString().toInt()
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

    applicationVariants.forEach { variant ->
        variant.outputs
                .map { it as BaseVariantOutputImpl }
                .forEach { output ->
                    output.outputFileName = output.outputFileName
                            .replace("app-", "GithubActivityWidget-")
                            .replace(".apk", "-${variant.versionName}.${variant.versionCode}.apk")
                            .replace("-release", "")
                }
    }

}

kapt {
    arguments {
        arg("androidManifestFile", project.android.applicationVariants.first().outputs.first().processResources.manifestFile)
        arg("resourcePackageName", project.android.defaultConfig.applicationId!!)
    }
}

dependencies {

    implementation(project(":dao"))
    implementation(project(":github"))

    kapt("org.androidannotations:androidannotations:3.1")
    implementation("org.androidannotations:androidannotations-api:3.1")

    implementation("com.android.support:appcompat-v7:25.4.0")
    implementation("com.android.support:gridlayout-v7:25.4.0")

    implementation("com.noveogroup.android:android-logger:1.3.5")

    implementation("org.apache.commons:commons-lang3:3.6")

}
