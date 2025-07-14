plugins {
    id("java-library")
    id("groovy")
    id("com.gradle.plugin-publish") version "1.3.1"
    id("com.vanniktech.maven.publish") version "0.34.0"
}

group = providers.gradleProperty("GROUP").get()
version = providers.gradleProperty("VERSION_NAME").get()

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation("biz.aQute.bnd:biz.aQute.bndlib:6.4.1")

    testImplementation("org.spockframework:spock-core:2.3-groovy-3.0")
    testImplementation("net.bytebuddy:byte-buddy:1.17.5")
    testImplementation(platform("org.junit:junit-bom:5.12.2"))
    testImplementation("org.junit.vintage:junit-vintage-engine")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

gradlePlugin {
    website = providers.gradleProperty("POM_URL")
    vcsUrl = providers.gradleProperty("POM_URL")

    plugins {
        create("osgiPlugin") {
            id = group.toString()
            implementationClass = "com.github.blindpirate.osgi.plugins.osgi.OsgiPlugin"
            displayName = providers.gradleProperty("POM_NAME").get()
            description = providers.gradleProperty("POM_DESCRIPTION").get()
            tags = listOf("legacy", "osgi")
        }
    }
}

tasks.test {
    useJUnitPlatform()
}
