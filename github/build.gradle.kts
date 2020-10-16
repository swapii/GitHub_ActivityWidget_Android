plugins {
	id("java-library")
	id("kotlin")
}

dependencies {

	api(Dependencies.rxJava)
	api(Dependencies.Slf4j.api)

	implementation(Dependencies.okHttp)
	implementation(Dependencies.Kotlin.Coroutines.core)
	implementation(Dependencies.Kotlin.Coroutines.rx2)
	implementation(Dependencies.rxJavaBridge)

	testImplementation("junit:junit:${Versions.junit}")
	testImplementation("commons-io:commons-io:${Versions.ApacheCommons.io}")
	testImplementation(Dependencies.Slf4j.simple)

}
