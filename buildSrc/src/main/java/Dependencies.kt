object Dependencies {

    const val rxJava = "io.reactivex.rxjava3:rxjava:${Versions.rxJava}"
    const val rxJavaBridge = "com.github.akarnokd:rxjava3-bridge:3.0.0"
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"

    object Slf4j {
        const val api = "org.slf4j:slf4j-api:${Versions.slf4j}"
        const val simple = "org.slf4j:slf4j-simple:${Versions.slf4j}"
    }

    object Dagger {
        const val android = "com.google.dagger:dagger-android:${Versions.dagger}"
        const val compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    }

    object Kotlin {
        object Coroutines {
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5"
            const val rx2 = "org.jetbrains.kotlinx:kotlinx-coroutines-rx2:1.3.5"
        }
    }

}
