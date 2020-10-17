plugins {
	id("com.android.library")
}

android {
	compileSdkVersion(Versions.Android.compileSdkVersion)
}

dependencies {
	api("de.greenrobot:greendao:${Versions.greenDao}")
}
