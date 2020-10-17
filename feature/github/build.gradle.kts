plugins {
	id("java-library")
	id("kotlin")
    kotlin("kapt")
}

dependencies {

	api(Dependencies.rxJava)
	api(Dependencies.Slf4j.api)
	api(Dependencies.okHttp)

    api(Dependencies.Dagger.dagger)
    kapt(Dependencies.Dagger.compiler)

	testImplementation("junit:junit:${Versions.junit}")
	testImplementation("commons-io:commons-io:${Versions.ApacheCommons.io}")
	testImplementation(Dependencies.Slf4j.simple)

}
