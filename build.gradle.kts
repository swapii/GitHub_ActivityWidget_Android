buildscript {
    repositories {
        jcenter()
        google()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("com.android.tools.build:gradle:4.1.0")
    }
}

allprojects {
    repositories {
        jcenter()
        google()
    }
}
