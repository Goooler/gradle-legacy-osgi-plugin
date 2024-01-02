import com.vanniktech.maven.publish.SonatypeHost

plugins {
    id("java-library")
    id("groovy")
    id("com.gradle.plugin-publish") version "1.2.1"
    id("com.vanniktech.maven.publish") version "0.26.0"
}

group = "io.github.goooler.osgi"
version = "0.8.2-SNAPSHOT"

java {
    toolchain.languageVersion = JavaLanguageVersion.of(8)
}

dependencies {
    api(gradleApi())
    api(localGroovy())
    api("biz.aQute.bnd:biz.aQute.bndlib:6.4.1")

    testImplementation("org.spockframework:spock-core:2.3-groovy-3.0")
    testImplementation("net.bytebuddy:byte-buddy:1.14.11")
    testImplementation(platform("org.junit:junit-bom:5.10.1"))
    testImplementation("org.junit.vintage:junit-vintage-engine")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

gradlePlugin {
    website = "https://github.com/Goooler/gradle-legacy-osgi-plugin"
    vcsUrl = "https://github.com/Goooler/gradle-legacy-osgi-plugin"

    plugins {
        create("osgiPlugin") {
            id = group.toString()
            implementationClass = "com.github.blindpirate.osgi.plugins.osgi.OsgiPlugin"
            displayName = "A legacy osgi plugin in Gradle 5"
            description = "A fork of https://github.com/blindpirate/gradle-legacy-osgi-plugin"
            tags = listOf("legacy", "osgi")
        }
    }
}

mavenPublishing {
    coordinates(group.toString(), "gradle-legacy-osgi-plugin", version.toString())
    pom {
        name = "A legacy osgi plugin in Gradle 5"
        description = "A fork of https://github.com/blindpirate/gradle-legacy-osgi-plugin"
        inceptionYear = "2023"
        url = "https://github.com/Goooler/gradle-legacy-osgi-plugin/"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "https://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                id = "Goooler"
                name = "Zongle Wang"
                url = "https://github.com/Goooler"
            }
        }
        scm {
            url = "https://github.com/Goooler/gradle-legacy-osgi-plugin/"
            connection = "scm:git:git://github.com/Goooler/gradle-legacy-osgi-plugin.git"
            developerConnection = "scm:git:ssh://git@github.com/Goooler/gradle-legacy-osgi-plugin.git"
        }
    }
    publishToMavenCentral(SonatypeHost.S01)
    signAllPublications()
}

tasks.publishPlugins {
    notCompatibleWithConfigurationCache("https://github.com/gradle/gradle/issues/21283")

    dependsOn(tasks.check)
}

tasks.test {
    useJUnitPlatform()
}
