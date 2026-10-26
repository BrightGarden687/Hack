subprojects {
	apply(plugin = "java")

	repositories {
		maven {
			name = "Fabric"
			url = uri("https://maven.fabricmc.net/")
		}
		mavenCentral()
	}

	tasks.withType<JavaCompile>().configureEach {
		options.release = 25
	}

	extensions.configure<JavaPluginExtension> {
		sourceCompatibility = JavaVersion.VERSION_25
		targetCompatibility = JavaVersion.VERSION_25
	}
}
