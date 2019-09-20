plugins {
	id("java-library")
}

dependencies {

	api("org.slf4j:slf4j-api:${Versions.slf4j}")

	testImplementation("junit:junit:${Versions.junit}")
	testImplementation("commons-io:commons-io:${Versions.ApacheCommons.io}")
	testImplementation("org.slf4j:slf4j-simple:${Versions.slf4j}")

}
