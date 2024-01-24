plugins {
    id("java-library")
    id("groovy")
    id("com.gradle.plugin-publish") version "1.2.1"
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0-rc-1"
    signing
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
    testImplementation("net.bytebuddy:byte-buddy:1.14.11")
    testImplementation(platform("org.junit:junit-bom:5.10.1"))
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
            displayName = providers.gradleProperty("POM_NAME").orNull
            description = providers.gradleProperty("POM_DESCRIPTION").orNull
            tags = listOf("legacy", "osgi")
        }
    }
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl = uri("https://s01.oss.sonatype.org/service/local/")
            snapshotRepositoryUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            username = providers.gradleProperty("mavenCentralUsername")
            password = providers.gradleProperty("mavenCentralPassword")
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = providers.gradleProperty("POM_ARTIFACT_ID").get()
            from(components["java"])

            pom {
                name = providers.gradleProperty("POM_NAME")
                description = providers.gradleProperty("POM_DESCRIPTION")
                url = providers.gradleProperty("POM_URL")
                licenses {
                    license {
                        name = providers.gradleProperty("POM_LICENSE_NAME")
                        url = providers.gradleProperty("POM_LICENSE_URL")
                    }
                }
                developers {
                    developer {
                        id = providers.gradleProperty("POM_DEVELOPER_ID")
                        name = providers.gradleProperty("POM_DEVELOPER_NAME")
                        url = providers.gradleProperty("POM_DEVELOPER_URL")
                    }
                }
                scm {
                    connection = providers.gradleProperty("POM_SCM_CONNECTION")
                    developerConnection = providers.gradleProperty("POM_SCM_DEV_CONNECTION")
                    url = providers.gradleProperty("POM_SCM_URL")
                }
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
    isRequired = !version.toString().endsWith("-SNAPSHOT")
}

// TODO: workaround for https://github.com/gradle-nexus/publish-plugin/issues/208.
tasks.withType<PublishToMavenRepository>().configureEach {
    dependsOn(tasks.withType<Sign>())
}

tasks.matching {
    it.name.matches(Regex("^publish.*ToMavenLocal\$"))
}.configureEach {
    dependsOn(tasks.withType<Sign>())
}

tasks.publishPlugins {
    notCompatibleWithConfigurationCache("https://github.com/gradle/gradle/issues/21283")

    dependsOn(tasks.check)
}

tasks.test {
    useJUnitPlatform()
}
