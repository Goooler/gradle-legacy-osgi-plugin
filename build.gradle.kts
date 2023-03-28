plugins {
    `java-library`
    groovy
    id("com.gradle.plugin-publish") version "1.1.0"
}

group = "io.github.goooler"
version = "0.8.1-SNAPSHOT"

dependencies {
    api(gradleApi())
    api(localGroovy())
    api("biz.aQute.bnd:biz.aQute.bndlib:6.4.0")

    testImplementation("org.spockframework:spock-core:2.3-groovy-3.0")
    testImplementation("net.bytebuddy:byte-buddy:1.14.3")
    testImplementation(platform("org.junit:junit-bom:5.9.2"))
    testImplementation("org.junit.vintage:junit-vintage-engine")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(8))
}

gradlePlugin {
    website.set("https://github.com/Goooler/gradle-legacy-osgi-plugin")
    vcsUrl.set("https://github.com/Goooler/gradle-legacy-osgi-plugin")

    plugins {
        create("osgiPlugin") {
            id = "io.github.goooler.osgi"
            implementationClass = "com.github.blindpirate.osgi.plugins.osgi.OsgiPlugin"
            displayName = "A legacy osgi plugin in Gradle 5"
            description = "A fork of https://github.com/blindpirate/gradle-legacy-osgi-plugin"
            tags.set(listOf("legacy", "osgi"))
        }
    }
}

tasks.publishPlugins.configure {
    notCompatibleWithConfigurationCache("https://github.com/gradle/gradle/issues/21283")
    dependsOn(tasks.test)
}

tasks.test {
    useJUnitPlatform()
}
