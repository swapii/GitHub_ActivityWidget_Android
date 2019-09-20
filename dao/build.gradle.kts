plugins {
	id("com.android.library")
}

android {
	compileSdkVersion(Versions.Android.compileSdkVersion)
	buildToolsVersion(Versions.Android.buildToolsVersion)
}

dependencies {
	api("de.greenrobot:greendao:${Versions.greenDao}")
}
