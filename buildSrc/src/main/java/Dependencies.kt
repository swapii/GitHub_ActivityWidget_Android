object Dependencies {

    const val rxJava = "io.reactivex.rxjava3:rxjava:${Versions.rxJava}"
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"

    object Slf4j {
        const val api = "org.slf4j:slf4j-api:${Versions.slf4j}"
        const val simple = "org.slf4j:slf4j-simple:${Versions.slf4j}"
    }

    object Dagger {
        private const val version = "2.29.1"
        const val dagger = "com.google.dagger:dagger:$version"
        const val android = "com.google.dagger:dagger-android:$version"
        const val compiler = "com.google.dagger:dagger-compiler:$version"
    }

    object AndroidX {

        const val appCompat = "androidx.appcompat:appcompat:1.2.0"

        object Work {
            private const val version = "2.4.0"
            const val runtimeKtx = "androidx.work:work-runtime-ktx:$version"
        }

    }

    object ApacheCommons {
        const val lang3 = "org.apache.commons:commons-lang3:3.7"
    }

}
