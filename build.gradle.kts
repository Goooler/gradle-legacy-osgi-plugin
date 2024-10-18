plugins {
    id("java-library")
    id("groovy")
    id("com.gradle.plugin-publish") version "1.3.0"
    id("com.vanniktech.maven.publish") version "0.29.0"
}

group = providers.gradleProperty("GROUP").get()
version = providers.gradleProperty("VERSION_NAME").get()

java {
    toolchain.languageVersion = JavaLanguageVersion.of(8)
}

dependencies {
    api(gradleApi())
    api(localGroovy())
    api("biz.aQute.bnd:biz.aQute.bndlib:6.4.1")

    testImplementation("org.spockframework:spock-core:2.3-groovy-3.0")
    testImplementation("net.bytebuddy:byte-buddy:1.15.5")
    testImplementation(platform("org.junit:junit-bom:5.11.2"))
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

tasks.publishPlugins {
    notCompatibleWithConfigurationCache("https://github.com/gradle/gradle/issues/21283")

    dependsOn(tasks.check)
}

tasks.test {
    useJUnitPlatform()
}
